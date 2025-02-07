package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.GradReportTypesEnum;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.District;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.DistrictReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.DistrictReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistrictReportsServiceTest {

  @Mock
  private DistrictReportRepository districtReportsRepository;
  @Mock
  private DistrictReportLightRepository districtReportsLightRepository;
  @Mock
  private InstituteService instituteService;
  private DistrictReportService districtReportsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    DistrictReportTransformer districtReportsTransformer = new DistrictReportTransformer(new ModelMapper());
    districtReportsService = new DistrictReportService(
        districtReportsRepository,
        districtReportsLightRepository,
        instituteService,
        districtReportsTransformer
    );
  }

  private DistrictReportEntity createDistrictReportEntity(String reportTypeCode) {
    return DistrictReportEntity.builder()
        .districtId(UUID.randomUUID())
        .reportTypeCode(reportTypeCode)
        .report("data")
        .build();
  }

  private DistrictReportLightEntity createDistrictReportLightEntity(String reportTypeCode) {
    return DistrictReportLightEntity.builder()
        .districtId(UUID.randomUUID())
        .reportTypeCode(reportTypeCode)
        .build();
  }

  private District createDistrict() {
    return District.builder()
        .districtId(UUID.randomUUID().toString())
        .districtNumber("123")
        .displayName("Test District")
        .build();
  }

  @Test
  void testSearchDistrictReports_givenValidParams_returnsReports() {
    String reportType = GradReportTypesEnum.DISTREP_SD.getCode();
    DistrictReportEntity entity = createDistrictReportEntity(reportType);
    UUID districtId = entity.getDistrictId();
    List<DistrictReportEntity> entities = Collections.singletonList(entity);
    District district = createDistrict();
    district.setDistrictId(String.valueOf(entity.getDistrictId()));

    when(districtReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getDistrict(any())).thenReturn(district);

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(districtReportsRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.DISTREP_SD.getLabel());
    assertEquals("Test District", result.get(0).getDistrictName());
  }

  @Test
  void testSearchDistrictReports_givenValidParams_returnsLightReports() {
    String reportType = GradReportTypesEnum.DISTREP_SD.getCode();
    DistrictReportLightEntity entity = createDistrictReportLightEntity(reportType);
    UUID districtId = entity.getDistrictId();
    List<DistrictReportLightEntity> entities = Collections.singletonList(entity);
    District district = createDistrict();
    district.setDistrictId(String.valueOf(entity.getDistrictId()));

    when(districtReportsLightRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getDistrict(any())).thenReturn(district);

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportType, true);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(districtReportsLightRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.DISTREP_SD.getLabel());
    assertEquals("Test District", result.get(0).getDistrictName());
  }

  @Test
  void testSearchDistrictReports_givenLabelReport_returnsReports() {
    String reportType = GradReportTypesEnum.ADDRESS_LABEL_YE.getCode();
    DistrictReportEntity entity = createDistrictReportEntity(reportType);
    UUID districtId = entity.getDistrictId();
    List<DistrictReportEntity> entities = Collections.singletonList(entity);
    District district = createDistrict();
    district.setDistrictId(String.valueOf(entity.getDistrictId()));

    when(districtReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getDistrict(any())).thenReturn(district);

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(districtReportsRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.ADDRESS_LABEL_YE.getLabel());
    assertNull(result.get(0).getDistrictName());
  }

  @Test
  void testSearchDistrictReports_givenNoMatchingReports_returnsEmptyList() {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(districtReportsRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportTypeCode, false);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testGetDistrictReportByDistrictIdAndReportType_givenNoMatchingReport_returnsNull() {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(districtReportsRepository.findByDistrictIdAndReportTypeCode(districtId, reportTypeCode)).thenReturn(Optional.empty());

    InputStreamResource response = districtReportsService.getDistrictReportByDistrictIdAndReportType(districtId, reportTypeCode);

    assertNull(response);
  }

  @Test
  void testDeleteDistrictReport_givenValidParams_deletesReport() {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    DistrictReportEntity entity = new DistrictReportEntity();

    when(districtReportsRepository.findByDistrictIdAndReportTypeCode(districtId, reportTypeCode)).thenReturn(Optional.of(entity));

    districtReportsService.deleteDistrictReport(districtId, reportTypeCode);

    verify(districtReportsRepository, times(1)).findByDistrictIdAndReportTypeCode(districtId, reportTypeCode);
    verify(districtReportsRepository, times(1)).delete(entity);
  }

  @Test
  void testDeleteDistrictReport_givenNoMatchingReport_doesNothing() {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(districtReportsRepository.findByDistrictIdAndReportTypeCode(districtId, reportTypeCode)).thenReturn(Optional.empty());

    districtReportsService.deleteDistrictReport(districtId, reportTypeCode);

    verify(districtReportsRepository, never()).delete((DistrictReportEntity) any());
  }

  @Test
  void testDeleteAllDistrictReportsByType_givenValidReportType_deletesReports() {
    String reportTypeCode = "type";
    List<DistrictReportEntity> entities = Collections.singletonList(new DistrictReportEntity());

    when(districtReportsRepository.deleteAllByReportTypeCode(reportTypeCode)).thenReturn(entities);

    districtReportsService.deleteAllDistrictReportsByType(reportTypeCode);

    verify(districtReportsRepository, times(1)).deleteAllByReportTypeCode(reportTypeCode);
  }
}
