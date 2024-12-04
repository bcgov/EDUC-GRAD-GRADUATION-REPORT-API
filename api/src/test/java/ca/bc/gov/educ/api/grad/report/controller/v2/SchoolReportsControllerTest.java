package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.EducGradReportApiApplication;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.dto.School;
import ca.bc.gov.educ.api.grad.report.repository.SchoolReportsRepository;
import ca.bc.gov.educ.api.grad.report.service.CommonService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EducGradReportApiApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SchoolReportsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SchoolReportsRepository schoolReportsRepository;

  @MockBean
  private CommonService commonService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    schoolReportsRepository.deleteAll();
  }

  @Test
  void testSearchSchoolReports_givenNoReports_shouldReturnNoContent() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING).with(mockAuthority)
                    .param("schoolOfRecordId", UUID.randomUUID().toString())
                    .param("reportType", "type")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  void testSearchSchoolReports_givenParams_shouldReturnAllContent() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING).contentType(MediaType.APPLICATION_JSON).with(mockAuthority))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchSchoolReports_givenSchoolOfRecordIdParams_shouldReturnCorrectRecord() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity schoolReportsEntity1 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build();
    SchoolReportsEntity schoolReportsEntity2 = SchoolReportsEntity.builder().schoolOfRecordId(UUID.randomUUID()).reportTypeCode(reportTypeCode).schoolOfRecord("123455").build();
    schoolReportsRepository.saveAll(List.of(schoolReportsEntity1, schoolReportsEntity2));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("schoolOfRecordId", schoolOfRecordId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchSchoolReports_givenReportTypeParams_shouldReturnCorrectRecord() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity schoolReportsEntity1 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build();
    SchoolReportsEntity schoolReportsEntity2 = SchoolReportsEntity.builder().schoolOfRecordId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").schoolOfRecord("123455").build();
    schoolReportsRepository.saveAll(List.of(schoolReportsEntity1, schoolReportsEntity2));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("reportTypeCode", reportTypeCode))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchSchoolReports_givenAllParams_shouldReturnCorrectRecord() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity schoolReportsEntity1 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build();
    SchoolReportsEntity schoolReportsEntity2 = SchoolReportsEntity.builder().schoolOfRecordId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").schoolOfRecord("123455").build();
    SchoolReportsEntity schoolReportsEntity3 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode("TEST_TYPE").schoolOfRecord("123455").build();
    schoolReportsRepository.saveAll(List.of(schoolReportsEntity1, schoolReportsEntity2, schoolReportsEntity3));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("reportTypeCode", reportTypeCode)
                    .param("schoolOfRecordId", schoolOfRecordId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testGetSchoolReportBySchoolOfRecordIdAndReportType() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    String expectedReportContent = "Some fake report";
    String base64EncodedReport = Base64.encodeBase64String(expectedReportContent.getBytes(StandardCharsets.US_ASCII));

    SchoolReportsEntity schoolReportsEntity1 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).report(base64EncodedReport).schoolOfRecord("123456").build();
    SchoolReportsEntity schoolReportsEntity2 = SchoolReportsEntity.builder().schoolOfRecordId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").schoolOfRecord("123455").build();
    SchoolReportsEntity schoolReportsEntity3 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode("TEST_TYPE").schoolOfRecord("123455").build();
    schoolReportsRepository.saveAll(List.of(schoolReportsEntity1, schoolReportsEntity2, schoolReportsEntity3));

    when(commonService.getSchool(anyString())).thenReturn(School.builder().minCode("123456").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .param("schoolOfRecordId", schoolOfRecordId.toString())
                    .param("reportTypeCode", "type")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
              byte[] actualResponse = result.getResponse().getContentAsByteArray();
              String actualBase64Content = Base64.encodeBase64String(actualResponse);
              assertEquals(base64EncodedReport, actualBase64Content, "The decoded report content should match the expected content.");
            });
  }

  @Test
  void testUpdateSchoolReport_givenValidPayload_ReturnOkAndUpdateDetailsUpdated() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    SchoolReportsEntity schoolReportsEntity1 = SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build();
    schoolReportsRepository.save(schoolReportsEntity1);

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(post(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + "/" + schoolOfRecordId + "/" + reportTypeCode + "/reset-update-user")
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    var schoolReports = schoolReportsRepository.findAll();
    assertEquals(1, schoolReports.size());
    assertNotEquals(schoolReports.get(0).getUpdateDate(), schoolReports.get(0).getCreateDate());
  }

  @Test
  void testUpdateSchoolReport_givenNoSchoolRecord_ReturnNotFound() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(post(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + "/" + UUID.randomUUID() + "/type/reset-update-user")
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void testUpdateSchoolReport_givenBadUUID_ReturnNotFound() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(post(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + "/123456/type/reset-update-user")
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void testDeleteSchoolReport_givenValidPayload_SchoolReportIsDeleted() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build());
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode("TEST_TYPE").schoolOfRecord("123456").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + "/" + schoolOfRecordId + "/" + reportTypeCode)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    var schoolReports = schoolReportsRepository.findAll();
    assertEquals(1, schoolReports.size());
  }

  @Test
  void testDeleteSchoolReport_givenNoRecordToDelete_ReturnBadRequest() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING + "/" + UUID.randomUUID() + "/type")
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteSchoolReportsByType_givenValidPayload_SchoolReportIsDeleted() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    String reportTypeCode = "type";
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode(reportTypeCode).schoolOfRecord("123456").build());
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode("TEST_TYPE").schoolOfRecord("123456").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("reportTypeCode", "type"))
            .andExpect(status().isNoContent());
    var schoolReports = schoolReportsRepository.findAll();
    assertEquals(1, schoolReports.size());
  }

  @Test
  void testDeleteSchoolReportsByType_givenMissingQueryParam_ReturnBadRequest() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("WRONG-PARAM", "type"))
            .andExpect(status().isBadRequest());
  }

  @Test
  void testDeleteSchoolReportsByType_givenNoneToDelete_ReturnNoContent() throws Exception {
    UUID schoolOfRecordId = UUID.randomUUID();
    schoolReportsRepository.save(SchoolReportsEntity.builder().schoolOfRecordId(schoolOfRecordId).reportTypeCode("TEST_TYPE").schoolOfRecord("123456").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("reportTypeCode", "type"))
            .andExpect(status().isNoContent());
    var schoolReports = schoolReportsRepository.findAll();
    assertEquals(1, schoolReports.size());
  }
}
