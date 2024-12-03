package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.filter.SchoolReportsSpecifications;
import ca.bc.gov.educ.api.grad.report.model.dto.SchoolReports;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.SchoolReportsTransformer;
import ca.bc.gov.educ.api.grad.report.repository.SchoolReportsLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.SchoolReportsRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchoolReportsService extends BaseService {
  SchoolReportsRepository schoolReportsRepository;
  SchoolReportsLightRepository schoolReportsLightRepository;
  SchoolReportsTransformer schoolReportsTransformer;
  CommonService commonService;

  private static final Logger logger = LoggerFactory.getLogger(SchoolReportsService.class);

  @Autowired
  public SchoolReportsService(SchoolReportsRepository schoolReportsRepository, SchoolReportsTransformer schoolReportsTransformer, CommonService commonService, SchoolReportsLightRepository schoolReportsLightRepository) {
    this.schoolReportsRepository = schoolReportsRepository;
    this.schoolReportsTransformer = schoolReportsTransformer;
    this.commonService = commonService;
    this.schoolReportsLightRepository = schoolReportsLightRepository;
  }

  private <T> Specification<T> buildSearchSpecification(UUID schoolOfRecordId, String reportTypeCode) {
    Specification<T> spec = Specification.where(null);

    if (schoolOfRecordId != null) {
      spec = spec.and(SchoolReportsSpecifications.hasSchoolOfRecordId(schoolOfRecordId));
    }
    if (StringUtils.isNotBlank(reportTypeCode)) {
      spec = spec.and(SchoolReportsSpecifications.hasReportTypeCode(reportTypeCode));
    }
    return spec;
  }

  public List<SchoolReports> searchSchoolReports(UUID schoolOfRecordId, String reportTypeCode, boolean isLight) {
    if(isLight) {
      Specification<SchoolReportsLightEntity> spec = buildSearchSpecification(schoolOfRecordId, reportTypeCode);
      List<SchoolReportsLightEntity> entities = schoolReportsLightRepository.findAll(spec);
      return schoolReportsTransformer.transformToLightDTO(entities);
    } else {
      Specification<SchoolReportsEntity> spec = buildSearchSpecification(schoolOfRecordId, reportTypeCode);
      List<SchoolReportsEntity> entities = schoolReportsRepository.findAll(spec);
      return schoolReportsTransformer.transformToDTO(entities);
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<InputStreamResource> getSchoolReportBySchoolOfRecordIdAndReportType(UUID schoolOfRecordId, String reportTypeCode) {
    SchoolReports schoolReport = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode));
    if (schoolReport != null && schoolReport.getReport() != null) {
      byte[] reportByte = Base64.decodeBase64(schoolReport.getReport().getBytes(StandardCharsets.US_ASCII));
      ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
      return ResponseEntity
              .ok()
              .contentType(MediaType.APPLICATION_PDF)
              .body(new InputStreamResource(bis));
    }
    return null;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Void> updateSchoolReports(UUID schoolOfRecordId, String reportTypeCode) {
    Optional<SchoolReportsEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    if (optEntity.isPresent()) {
      SchoolReportsEntity ent = optEntity.get();
      ent.setUpdateDate(null);
      ent.setUpdateUser(null);
      schoolReportsRepository.save(ent);
      return ResponseEntity.noContent().build();
    }
    logger.warn("No school report found to update for schoolOfRecordId {} and reportTypeCode {}", schoolOfRecordId, reportTypeCode);
    return ResponseEntity.notFound().build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public SchoolReports saveSchoolReports(SchoolReports schoolReports) {
    SchoolReportsEntity toBeSaved = schoolReportsTransformer.transformToEntity(schoolReports);
    Optional<SchoolReportsEntity> existingEntity = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolReports.getSchoolOfRecordId(), schoolReports.getReportTypeCode());
    if (existingEntity.isPresent()) {
      SchoolReportsEntity gradEntity = existingEntity.get();
      gradEntity.setUpdateDate(null);
      gradEntity.setUpdateUser(null);
      if (schoolReports.getReport() != null) {
        gradEntity.setReport(schoolReports.getReport());
      }
      return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(gradEntity));
    } else {
      return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(toBeSaved));
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Void> deleteSchoolReport(UUID schoolOfRecordId, String reportTypeCode) {
    Optional<SchoolReportsEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    if (optEntity.isPresent()) {
      schoolReportsRepository.delete(optEntity.get());
      return ResponseEntity.noContent().build();
    }
    logger.warn("No school report found to delete for schoolOfRecordId {} and reportTypeCode {}", schoolOfRecordId, reportTypeCode);
    return ResponseEntity.notFound().build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Void> deleteAllSchoolReportsByType(String reportTypeCode) {
    List<SchoolReportsEntity> deletedReports = schoolReportsRepository.deleteAllByReportTypeCode(reportTypeCode);
    logger.debug("Deleted {} school reports with reportTypeCode {}", deletedReports.size(), reportTypeCode);
    return ResponseEntity.noContent().build();
  }
}
