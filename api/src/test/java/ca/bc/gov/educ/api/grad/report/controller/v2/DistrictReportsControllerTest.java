package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.EducGradReportApiApplication;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.District;
import ca.bc.gov.educ.api.grad.report.repository.v2.DistrictReportRepository;
import ca.bc.gov.educ.api.grad.report.service.v2.InstituteService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EducGradReportApiApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class DistrictReportsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DistrictReportRepository districtReportsRepository;
  @MockBean
  private InstituteService instituteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    districtReportsRepository.deleteAll();
  }

  @Test
  void testSearchDistrictReports_givenNoReports_shouldReturnNoContent() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING).with(mockAuthority)
                    .param("districtId", UUID.randomUUID().toString())
                    .param("reportType", "type")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  void testSearchDistrictReports_givenParams_shouldReturnAllContent() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING).contentType(MediaType.APPLICATION_JSON).with(mockAuthority))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchDistrictReports_givenDistrictIdParams_shouldReturnCorrectRecord() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    DistrictReportEntity districtReportsEntity1 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build();
    DistrictReportEntity districtReportsEntity2 = DistrictReportEntity.builder().districtId(UUID.randomUUID()).reportTypeCode(reportTypeCode).build();
    districtReportsRepository.saveAll(List.of(districtReportsEntity1, districtReportsEntity2));

    when(instituteService.getDistrict(districtId)).thenReturn(District.builder().districtId(String.valueOf(districtId)).displayName("District1").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("districtId", districtId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchDistrictReports_givenReportTypeParams_shouldReturnCorrectRecord() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    DistrictReportEntity districtReportsEntity1 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build();
    DistrictReportEntity districtReportsEntity2 = DistrictReportEntity.builder().districtId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").build();
    districtReportsRepository.saveAll(List.of(districtReportsEntity1, districtReportsEntity2));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("reportTypeCode", reportTypeCode))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testSearchDistrictReports_givenAllParams_shouldReturnCorrectRecord() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    DistrictReportEntity districtReportsEntity1 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build();
    DistrictReportEntity districtReportsEntity2 = DistrictReportEntity.builder().districtId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").build();
    DistrictReportEntity districtReportsEntity3 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode("TEST_TYPE").build();
    districtReportsRepository.saveAll(List.of(districtReportsEntity1, districtReportsEntity2, districtReportsEntity3));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + EducGradReportApiConstants.SEARCH_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(mockAuthority)
                    .param("reportTypeCode", reportTypeCode)
                    .param("districtId", districtId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testGetDistrictReportByDistrictIdAndReportType() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    String expectedReportContent = "Some fake report";
    String base64EncodedReport = Base64.encodeBase64String(expectedReportContent.getBytes(StandardCharsets.US_ASCII));

    DistrictReportEntity districtReportsEntity1 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).report(base64EncodedReport).build();
    DistrictReportEntity districtReportsEntity2 = DistrictReportEntity.builder().districtId(UUID.randomUUID()).reportTypeCode("TEST_TYPE").build();
    DistrictReportEntity districtReportsEntity3 = DistrictReportEntity.builder().districtId(districtId).reportTypeCode("TEST_TYPE").build();
    districtReportsRepository.saveAll(List.of(districtReportsEntity1, districtReportsEntity2, districtReportsEntity3));

    final GrantedAuthority grantedAuthority = () -> "SCOPE_READ_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(get(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .param("districtId", districtId.toString())
                    .param("reportTypeCode", reportTypeCode)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
              byte[] actualResponse = result.getResponse().getContentAsByteArray();
              String actualBase64Content = Base64.encodeBase64String(actualResponse);
              assertEquals(base64EncodedReport, actualBase64Content, "The decoded report content should match the expected content.");
            });
  }

  @Test
  void testDeleteDistrictReport_givenValidPayload_DistrictReportIsDeleted() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build());
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode("TEST_TYPE").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING + "/" + districtId + "/" + reportTypeCode)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    var districtReports = districtReportsRepository.findAll();
    assertEquals(1, districtReports.size());
  }

  @Test
  void testDeleteDistrictReportsByType_givenValidPayload_DistrictReportIsDeleted() throws Exception {
    UUID districtId = UUID.randomUUID();
    String reportTypeCode = "type";
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode(reportTypeCode).build());
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode("TEST_TYPE").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("reportTypeCode", "type"))
            .andExpect(status().isNoContent());
    var districtReports = districtReportsRepository.findAll();
    assertEquals(1, districtReports.size());
  }

  @Test
  void testDeleteDistrictReportsByType_givenMissingQueryParam_ReturnBadRequest() throws Exception {
    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("WRONG-PARAM", "type"))
            .andExpect(status().isBadRequest());
  }

  @Test
  void testDeleteDistrictReportsByType_givenNoneToDelete_ReturnNoContent() throws Exception {
    UUID districtId = UUID.randomUUID();
    districtReportsRepository.save(DistrictReportEntity.builder().districtId(districtId).reportTypeCode("TEST_TYPE").build());

    final GrantedAuthority grantedAuthority = () -> "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA";
    final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);
    mockMvc.perform(delete(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING)
                    .with(mockAuthority)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("reportTypeCode", "type"))
            .andExpect(status().isNoContent());
    var districtReports = districtReportsRepository.findAll();
    assertEquals(1, districtReports.size());
  }
}
