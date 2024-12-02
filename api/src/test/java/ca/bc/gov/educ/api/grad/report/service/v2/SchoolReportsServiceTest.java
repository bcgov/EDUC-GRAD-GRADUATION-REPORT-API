package ca.bc.gov.educ.api.grad.report.service.v2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolReports;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.SchoolReportsTransformer;
import ca.bc.gov.educ.api.grad.report.repository.SchoolReportsRepository;
import ca.bc.gov.educ.api.grad.report.service.SchoolReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class SchoolReportsServiceTest {

  @Mock
  private SchoolReportsRepository schoolReportsRepository;
  @Mock
  private SchoolReportsTransformer schoolReportsTransformer;
  @InjectMocks
  private SchoolReportsService schoolReportsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSearchSchoolReports_givenValidParams_returnsReports() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportType = "type";
    SchoolReportsEntity entity = new SchoolReportsEntity();
    List<SchoolReportsEntity> entities = Collections.singletonList(entity);
    SchoolReports dto = new SchoolReports();
    List<SchoolReports> dtos = Collections.singletonList(dto);

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(schoolReportsTransformer.transformToDTO(entities)).thenReturn(dtos);

    List<SchoolReports> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsRepository, times(1)).findAll(any(Specification.class));
    verify(schoolReportsTransformer, times(1)).transformToDTO(entities);
  }

  @Test
  void testSearchSchoolReports_givenNoMatchingReports_returnsEmptyList() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());
    when(schoolReportsTransformer.transformToDTO(anyList())).thenReturn(Collections.emptyList());

    List<SchoolReports> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportTypeCode);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testGetSchoolReportBySchoolOfRecordIdAndReportType_givenNoMatchingReport_returnsNull() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    ResponseEntity<InputStreamResource> response = schoolReportsService.getSchoolReportBySchoolOfRecordIdAndReportType(schoolOfRecordId, reportTypeCode);

    assertNull(response);
  }

  @Test
  void testUpdateSchoolReports_givenValidParams_updatesReport() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity entity = new SchoolReportsEntity();

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.of(entity));

    ResponseEntity<Void> response = schoolReportsService.updateSchoolReports(schoolOfRecordId, reportTypeCode);

    assertNotNull(response);
    assertEquals(204, response.getStatusCode().value());
    verify(schoolReportsRepository, times(1)).findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    verify(schoolReportsRepository, times(1)).save(entity);
  }

  @Test
  void testUpdateSchoolReports_givenNoMatchingReport_returnsNotFound() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    ResponseEntity<Void> response = schoolReportsService.updateSchoolReports(schoolOfRecordId, reportTypeCode);

    assertEquals(404, response.getStatusCode().value());
    verify(schoolReportsRepository, never()).save(any());
  }

  @Test
  void testDeleteSchoolReport_givenValidParams_deletesReport() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity entity = new SchoolReportsEntity();

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.of(entity));

    ResponseEntity<Void> response = schoolReportsService.deleteSchoolReport(schoolOfRecordId, reportTypeCode);

    assertNotNull(response);
    assertEquals(204, response.getStatusCode().value());
    verify(schoolReportsRepository, times(1)).findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode);
    verify(schoolReportsRepository, times(1)).delete(entity);
  }

  @Test
  void testDeleteSchoolReport_givenNoMatchingReport_returnsNotFound() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findBySchoolOfRecordIdAndReportTypeCode(schoolOfRecordId, reportTypeCode)).thenReturn(Optional.empty());

    ResponseEntity<Void> response = schoolReportsService.deleteSchoolReport(schoolOfRecordId, reportTypeCode);

    assertEquals(404, response.getStatusCode().value());
    verify(schoolReportsRepository, never()).delete((SchoolReportsEntity) any());
  }

  @Test
  void testDeleteAllSchoolReportsByType_givenValidReportType_deletesReports() {
    String reportTypeCode = "type";
    List<SchoolReportsEntity> entities = Collections.singletonList(new SchoolReportsEntity());

    when(schoolReportsRepository.deleteAllByReportTypeCode(reportTypeCode)).thenReturn(entities);

    ResponseEntity<Void> response = schoolReportsService.deleteAllSchoolReportsByType(reportTypeCode);

    assertNotNull(response);
    assertEquals(204, response.getStatusCode().value());
    verify(schoolReportsRepository, times(1)).deleteAllByReportTypeCode(reportTypeCode);
  }
}
