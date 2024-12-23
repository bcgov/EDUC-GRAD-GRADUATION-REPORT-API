package ca.bc.gov.educ.api.grad.report.service.v2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.bc.gov.educ.api.grad.report.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SchoolReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.SchoolReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
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

class SchoolReportsServiceTest {

  @Mock
  private SchoolReportRepository schoolReportsRepository;
  @Mock
  private SchoolReportLightRepository schoolReportsLightRepository;
  @Mock
  private SchoolReportTransformer schoolReportsTransformer;
  @InjectMocks
  private SchoolReportService schoolReportsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSearchSchoolReports_givenValidParams_returnsReports() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportType = "type";
    SchoolReportEntity entity = new SchoolReportEntity();
    List<SchoolReportEntity> entities = Collections.singletonList(entity);
    SchoolReport dto = new SchoolReport();
    List<SchoolReport> dtos = Collections.singletonList(dto);

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(schoolReportsTransformer.transformToDTO(entities)).thenReturn(dtos);

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsRepository, times(1)).findAll(any(Specification.class));
    verify(schoolReportsTransformer, times(1)).transformToDTO(entities);
  }

  @Test
  void testSearchSchoolReports_givenValidParams_returnsLightReports() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportType = "type";
    SchoolReportLightEntity entity = new SchoolReportLightEntity();
    List<SchoolReportLightEntity> entities = Collections.singletonList(entity);
    SchoolReport dto = new SchoolReport();
    List<SchoolReport> dtos = Collections.singletonList(dto);

    when(schoolReportsLightRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(schoolReportsTransformer.transformToLightDTO(entities)).thenReturn(dtos);

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType, true);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsLightRepository, times(1)).findAll(any(Specification.class));
    verify(schoolReportsTransformer, times(1)).transformToLightDTO(entities);
  }

  @Test
  void testSearchSchoolReports_givenNoMatchingReports_returnsEmptyList() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());
    when(schoolReportsTransformer.transformToDTO(anyList())).thenReturn(Collections.emptyList());

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportTypeCode, false);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testGetSchoolReportBySchoolOfRecordIdAndReportType_givenNoMatchingReport_returnsNull() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    InputStreamResource response = schoolReportsService.getSchoolReportBySchoolOfRecordIdAndReportType(schoolOfRecordId, reportTypeCode);

    assertNull(response);
  }

  @Test
  void testUpdateSchoolReports_givenValidParams_updatesReport() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportEntity entity = new SchoolReportEntity();

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.of(entity));

    schoolReportsService.updateSchoolReports(schoolOfRecordId, reportTypeCode);

    verify(schoolReportsRepository, times(1)).findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    verify(schoolReportsRepository, times(1)).save(entity);
  }

  @Test
  void testUpdateSchoolReports_givenNoMatchingReport_throwsException() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      schoolReportsService.updateSchoolReports(schoolOfRecordId, reportTypeCode);
    });

    verify(schoolReportsRepository, times(1)).findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    verify(schoolReportsRepository, never()).save(any());
  }

  @Test
  void testDeleteSchoolReport_givenValidParams_deletesReport() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportEntity entity = new SchoolReportEntity();

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.of(entity));

    schoolReportsService.deleteSchoolReport(schoolOfRecordId, reportTypeCode);

    verify(schoolReportsRepository, times(1)).findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    verify(schoolReportsRepository, times(1)).delete(entity);
  }

  @Test
  void testDeleteSchoolReport_givenNoMatchingReport_doesNothing() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    schoolReportsService.deleteSchoolReport(schoolOfRecordId, reportTypeCode);

    verify(schoolReportsRepository, never()).delete((SchoolReportEntity) any());
  }

  @Test
  void testDeleteAllSchoolReportsByType_givenValidReportType_deletesReports() {
    String reportTypeCode = "type";
    List<SchoolReportEntity> entities = Collections.singletonList(new SchoolReportEntity());

    when(schoolReportsRepository.deleteAllByReportTypeCode(reportTypeCode)).thenReturn(entities);

    schoolReportsService.deleteAllSchoolReportsByType(reportTypeCode);

    verify(schoolReportsRepository, times(1)).deleteAllByReportTypeCode(reportTypeCode);
  }
}
