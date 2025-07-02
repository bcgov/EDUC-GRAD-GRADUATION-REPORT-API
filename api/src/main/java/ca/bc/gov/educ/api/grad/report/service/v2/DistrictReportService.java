package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.GradReportTypesEnum;
import ca.bc.gov.educ.api.grad.report.filter.DistrictReportSpecifications;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.District;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.DistrictReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.DistrictReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportRepository;
import ca.bc.gov.educ.api.grad.report.service.BaseService;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class DistrictReportService extends BaseService {
  DistrictReportRepository districtReportsRepository;
  DistrictReportLightRepository districtReportLightRepository;
  DistrictReportTransformer districtReportTransformer;
  InstituteService instituteService;

  @Autowired
  protected DistrictReportService(EducGradReportApiConstants constants, RESTService restService,
                                  @Qualifier("graduationReportApiClient") WebClient graduationServiceWebClient, DistrictReportRepository districtReportsRepository,
                                  DistrictReportLightRepository districtReportLightRepository,
                                  InstituteService instituteService, DistrictReportTransformer districtReportTransformer) {
    super(constants, restService, graduationServiceWebClient);
    this.districtReportsRepository = districtReportsRepository;
    this.districtReportLightRepository = districtReportLightRepository;
    this.instituteService = instituteService;
    this.districtReportTransformer = districtReportTransformer;
  }

  private <T> Specification<T> buildSearchSpecification(UUID districtId, String reportTypeCode) {
    Specification<T> spec = Specification.where(null);

    if (districtId != null) {
      spec = spec.and(DistrictReportSpecifications.hasDistrictId(districtId));
    }
    if (StringUtils.isNotBlank(reportTypeCode)) {
      spec = spec.and(DistrictReportSpecifications.hasReportTypeCode(reportTypeCode));
    }
    return spec;
  }

  public List<DistrictReport> searchDistrictReports(UUID districtId, String reportTypeCode, boolean isLight) {
    if(isLight) {
      Specification<DistrictReportLightEntity> spec = buildSearchSpecification(districtId, reportTypeCode);
      List<DistrictReportLightEntity> entities = districtReportLightRepository.findAll(spec);
      return populateDistrictReports(districtReportTransformer.transformToLightDTO(entities));
    } else {
      Specification<DistrictReportEntity> spec = buildSearchSpecification(districtId, reportTypeCode);
      List<DistrictReportEntity> entities = districtReportsRepository.findAll(spec);
      return populateDistrictReports(districtReportTransformer.transformToDTO(entities));
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public InputStreamResource getDistrictReportByDistrictIdAndReportType(UUID districtId, String reportTypeCode) {
    DistrictReport districtReport = districtReportTransformer.transformToDTO(districtReportsRepository.findByDistrictIdAndReportTypeCode(districtId, reportTypeCode));
    if (districtReport != null && districtReport.getReport() != null) {
      byte[] reportByte = Base64.decodeBase64(districtReport.getReport().getBytes(StandardCharsets.US_ASCII));
      ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
      return new InputStreamResource(bis);
    }
    return null;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public DistrictReport saveDistrictReports(DistrictReport districtReport) {
    DistrictReportEntity toBeSaved = districtReportTransformer.transformToEntity(districtReport);
    Optional<DistrictReportEntity> existingEntity = districtReportsRepository.findByDistrictIdAndReportTypeCode(districtReport.getDistrictId(), districtReport.getReportTypeCode());
    if (existingEntity.isPresent()) {
      DistrictReportEntity gradEntity = existingEntity.get();
      gradEntity.setUpdateDate(null);
      gradEntity.setUpdateUser(null);
      if (districtReport.getReport() != null) {
        gradEntity.setReport(districtReport.getReport());
      }
      return districtReportTransformer.transformToDTO(districtReportsRepository.save(gradEntity));
    } else {
      return districtReportTransformer.transformToDTO(districtReportsRepository.save(toBeSaved));
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteDistrictReport(UUID districtId, String reportTypeCode) {
    Optional<DistrictReportEntity> optEntity = districtReportsRepository.findByDistrictIdAndReportTypeCode(districtId, reportTypeCode);
    optEntity.ifPresentOrElse(districtReportsEntity -> districtReportsRepository.delete(districtReportsEntity),
            () -> log.warn("No district report found to delete for districtId {} and reportTypeCode {}", districtId, reportTypeCode));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteAllDistrictReportsByType(String reportTypeCode) {
    List<DistrictReportEntity> deletedReports = districtReportsRepository.deleteAllByReportTypeCode(reportTypeCode);
    log.debug("Deleted {} district reports with reportTypeCode {}", deletedReports.size(), reportTypeCode);
  }

  private List<DistrictReport> populateDistrictReports(List<DistrictReport> reportList) {
    Map<UUID, District> districtCache = new HashMap<>();

    reportList.forEach(report -> {
      GradReportTypesEnum.findByValue(report.getReportTypeCode()).ifPresent(types -> report.setReportTypeLabel(types.getLabel()));
      if (report.getDistrictId() != null && ! GradReportTypesEnum.isDistrictAddressLabelReport(report.getReportTypeCode())) {
        District district = districtCache.computeIfAbsent(report.getDistrictId(), id -> instituteService.getDistrict(id));
        if (district != null) {
          report.setDistrictName(district.getDisplayName());
        }
      } else {
        log.warn("populateDistrictReports: Could not set districtName; districtId is null for reportId {}", report.getId() );
      }
    });
    return reportList;
  }
}
