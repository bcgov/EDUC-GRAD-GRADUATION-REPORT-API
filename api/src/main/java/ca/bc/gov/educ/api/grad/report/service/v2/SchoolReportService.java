package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.GradReportTypesEnum;
import ca.bc.gov.educ.api.grad.report.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.grad.report.filter.SchoolReportsSpecifications;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SchoolReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.SchoolReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
import ca.bc.gov.educ.api.grad.report.service.BaseService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SchoolReportService extends BaseService {
  SchoolReportRepository schoolReportsRepository;
  SchoolReportLightRepository schoolReportsLightRepository;
  SchoolReportTransformer schoolReportsTransformer;
  InstituteService instituteService;

  private static final Logger logger = LoggerFactory.getLogger(SchoolReportService.class);

  @Autowired
  public SchoolReportService(SchoolReportRepository schoolReportsRepository, SchoolReportTransformer schoolReportsTransformer, SchoolReportLightRepository schoolReportsLightRepository, InstituteService instituteService) {
    this.schoolReportsRepository = schoolReportsRepository;
    this.schoolReportsTransformer = schoolReportsTransformer;
    this.schoolReportsLightRepository = schoolReportsLightRepository;
    this.instituteService = instituteService;
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

  public List<SchoolReport> searchSchoolReports(UUID schoolOfRecordId, String reportTypeCode, boolean isLight) {
    if(isLight) {
      Specification<SchoolReportLightEntity> spec = buildSearchSpecification(schoolOfRecordId, reportTypeCode);
      List<SchoolReportLightEntity> entities = schoolReportsLightRepository.findAll(spec);
      return populateSchoolReports(schoolReportsTransformer.transformToLightDTO(entities));
    } else {
      Specification<SchoolReportEntity> spec = buildSearchSpecification(schoolOfRecordId, reportTypeCode);
      List<SchoolReportEntity> entities = schoolReportsRepository.findAll(spec);
      return populateSchoolReports(schoolReportsTransformer.transformToDTO(entities));
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public InputStreamResource getSchoolReportBySchoolOfRecordIdAndReportType(UUID schoolOfRecordId, String reportTypeCode) {
    SchoolReport schoolReport = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode));
    if (schoolReport != null && schoolReport.getReport() != null) {
      byte[] reportByte = Base64.decodeBase64(schoolReport.getReport().getBytes(StandardCharsets.US_ASCII));
      ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
      return new InputStreamResource(bis);
    }
    return null;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSchoolReports(UUID schoolOfRecordId, String reportTypeCode) {
    SchoolReportEntity schoolReport = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)
            .orElseThrow(() -> new EntityNotFoundException(SchoolReportEntity.class, "schoolOfRecordId", schoolOfRecordId.toString(), "reportTypeCode", reportTypeCode));
    schoolReport.setUpdateDate(null);
    schoolReport.setUpdateUser(null);
    schoolReportsRepository.save(schoolReport);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public SchoolReport saveSchoolReports(SchoolReport schoolReports) {
    SchoolReportEntity toBeSaved = schoolReportsTransformer.transformToEntity(schoolReports);
    Optional<SchoolReportEntity> existingEntity = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolReports.getSchoolOfRecordId(), schoolReports.getReportTypeCode());
    if (existingEntity.isPresent()) {
      SchoolReportEntity gradEntity = existingEntity.get();
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
  public void deleteSchoolReport(UUID schoolOfRecordId, String reportTypeCode) {
    Optional<SchoolReportEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    optEntity.ifPresentOrElse(schoolReportsEntity -> schoolReportsRepository.delete(schoolReportsEntity),
            () -> logger.warn("No school report found to delete for schoolOfRecordId {} and reportTypeCode {}", schoolOfRecordId, reportTypeCode));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteAllSchoolReportsByType(String reportTypeCode) {
    List<SchoolReportEntity> deletedReports = schoolReportsRepository.deleteAllByReportTypeCode(reportTypeCode);
    logger.debug("Deleted {} school reports with reportTypeCode {}", deletedReports.size(), reportTypeCode);
  }

  private List<SchoolReport> populateSchoolReports(List<SchoolReport> reportList) {
    Map<UUID, School> schoolCache = new HashMap<>();

    reportList.forEach(report -> {
      GradReportTypesEnum.findByValue(report.getReportTypeCode()).ifPresent(types -> report.setReportTypeLabel(types.getLabel()));
      if (report.getSchoolOfRecordId() != null && !GradReportTypesEnum.isSchoolAddressLabelReport(report.getReportTypeCode())) {
        School schObj = schoolCache.computeIfAbsent(report.getSchoolOfRecordId(), id -> instituteService.getSchool(id));
        if (schObj != null) {
          report.setSchoolName(schObj.getDisplayName());
          report.setSchoolCategory(schObj.getSchoolCategoryCode());
        }
      } else {
        logger.warn("populateSchoolReports: Could not set schoolOfRecordName, schoolOfRecordId is null for reportId {}", report.getId() );
      }
    });
    return reportList;
  }
}
