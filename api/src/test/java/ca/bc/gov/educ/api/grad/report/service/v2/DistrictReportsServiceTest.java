package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.DistrictReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.DistrictReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
  private DistrictReportTransformer districtReportsTransformer;
  @InjectMocks
  private DistrictReportService districtReportsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSearchDistrictReports_givenValidParams_returnsReports() {
    UUID districtId = UUID.randomUUID();
    String reportType = "type";
    DistrictReportEntity entity = new DistrictReportEntity();
    List<DistrictReportEntity> entities = Collections.singletonList(entity);
    DistrictReport dto = new DistrictReport();
    List<DistrictReport> dtos = Collections.singletonList(dto);

    when(districtReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(districtReportsTransformer.transformToDTO(entities)).thenReturn(dtos);

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(districtReportsRepository, times(1)).findAll(any(Specification.class));
    verify(districtReportsTransformer, times(1)).transformToDTO(entities);
  }

  @Test
  void testSearchDistrictReports_givenValidParams_returnsLightReports() {
    UUID districtId = UUID.randomUUID();
    String reportType = "type";
    DistrictReportLightEntity entity = new DistrictReportLightEntity();
    List<DistrictReportLightEntity> entities = Collections.singletonList(entity);
    DistrictReport dto = new DistrictReport();
    List<DistrictReport> dtos = Collections.singletonList(dto);

    when(districtReportsLightRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(districtReportsTransformer.transformToLightDTO(entities)).thenReturn(dtos);

    List<DistrictReport> result = districtReportsService.searchDistrictReports(districtId, reportType, true);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(districtReportsLightRepository, times(1)).findAll(any(Specification.class));
    verify(districtReportsTransformer, times(1)).transformToLightDTO(entities);
  }

  @Test
  void testSearchDistrictReports_givenNoMatchingReports_returnsEmptyList() {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(districtReportsRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());
    when(districtReportsTransformer.transformToDTO(anyList())).thenReturn(Collections.emptyList());

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
