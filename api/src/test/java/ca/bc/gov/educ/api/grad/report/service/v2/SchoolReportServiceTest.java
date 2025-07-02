package ca.bc.gov.educ.api.grad.report.service.v2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.bc.gov.educ.api.grad.report.constants.GradReportTypesEnum;
import ca.bc.gov.educ.api.grad.report.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SchoolReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.v2.SchoolReportTransformer;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class SchoolReportServiceTest {

  @Mock
  protected EducGradReportApiConstants constants;
  @Mock
  protected RESTService restService;
  @Mock
  WebClient graduationServiceWebClient;
  @Mock
  private SchoolReportRepository schoolReportsRepository;
  @Mock
  private SchoolReportLightRepository schoolReportsLightRepository;
  @Mock
  private InstituteService instituteService;
  private SchoolReportService schoolReportsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    SchoolReportTransformer schoolReportsTransformer = new SchoolReportTransformer( new ModelMapper());
    schoolReportsService = new SchoolReportService(constants, restService,graduationServiceWebClient,
        schoolReportsRepository, schoolReportsTransformer, schoolReportsLightRepository, instituteService
    );
  }

  private SchoolReportEntity createSchoolReportEntity(String reportTypeCode) {
    return SchoolReportEntity.builder()
        .id(UUID.randomUUID())
        .schoolOfRecordId(UUID.randomUUID())
        .reportTypeCode(reportTypeCode)
        .build();
  }

  private SchoolReportLightEntity createSchoolReportLightEntity(String reportTypeCode) {
    return SchoolReportLightEntity.builder()
        .id(UUID.randomUUID())
        .schoolOfRecordId(UUID.randomUUID())
        .reportTypeCode(reportTypeCode)
        .build();
  }

  private School createSchool() {
    return School.builder()
        .schoolId(UUID.randomUUID().toString())
        .displayName("Test School")
        .schoolCategoryCode("PUBLIC")
        .build();
  }

  @Test
  void testSearchSchoolReports_givenValidParams_returnsReports() {
    String reportType = GradReportTypesEnum.DISTREP_SC.getCode();
    SchoolReportEntity entity = createSchoolReportEntity(reportType);
    UUID schoolOfRecordId = entity.getSchoolOfRecordId();
    List<SchoolReportEntity> entities = Collections.singletonList(entity);
    School school = createSchool();
    school.setSchoolId(String.valueOf(entity.getSchoolOfRecordId()));

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getSchool(any())).thenReturn(school);

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.DISTREP_SC.getLabel());
    assertEquals("Test School", result.get(0).getSchoolName());
  }

  @Test
  void testSearchSchoolReports_givenValidParams_returnsLightReports() {
    String reportType = GradReportTypesEnum.DISTREP_SC.getCode();
    SchoolReportLightEntity entity = createSchoolReportLightEntity(reportType);
    UUID schoolOfRecordId = entity.getSchoolOfRecordId();
    List<SchoolReportLightEntity> entities = Collections.singletonList(entity);
    School school = createSchool();
    school.setSchoolId(entity.getSchoolOfRecordId().toString());

    when(schoolReportsLightRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getSchool(any())).thenReturn(school);

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType, true);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsLightRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.DISTREP_SC.getLabel());
    assertEquals("Test School", result.get(0).getSchoolName());
  }

  @Test
  void testSearchSchoolReports_givenLabelReport_returnsReports() {
    String reportType = GradReportTypesEnum.ADDRESS_LABEL_SCHL.getCode();
    SchoolReportEntity entity = createSchoolReportEntity(reportType);
    UUID schoolOfRecordId = entity.getSchoolOfRecordId();
    List<SchoolReportEntity> entities = Collections.singletonList(entity);
    School school = createSchool();
    school.setSchoolId(entity.getSchoolOfRecordId().toString());

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(entities);
    when(instituteService.getSchool(any())).thenReturn(school);

    List<SchoolReport> result = schoolReportsService.searchSchoolReports(schoolOfRecordId, reportType, false);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(schoolReportsRepository, times(1)).findAll(any(Specification.class));
    assertEquals(result.get(0).getReportTypeLabel(), GradReportTypesEnum.ADDRESS_LABEL_SCHL.getLabel());
    assertNull(result.get(0).getSchoolCategory());
    assertNull(result.get(0).getSchoolName());
  }

  @Test
  void testSearchSchoolReports_givenNoMatchingReports_returnsEmptyList() {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";

    when(schoolReportsRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

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

    assertThrows(EntityNotFoundException.class, () -> schoolReportsService.updateSchoolReports(schoolOfRecordId, reportTypeCode));

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
