package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.service.CodeService;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import ca.bc.gov.educ.api.grad.report.util.MessageHelper;
import ca.bc.gov.educ.api.grad.report.util.ResponseHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CodeControllerTest {

	@Mock
	private CodeService codeService;
	
	@Mock
	ResponseHelper response;
	
	@InjectMocks
	private CodeController codeController;
	
	@Mock
	GradValidation validation;
	
	@Mock
	MessageHelper messagesHelper;
	
	@Mock
	OAuth2AuthenticationDetails oAuth2AuthenticationDetails;
	
	@Mock
	SecurityContextHolder securityContextHolder;
	
	@Test
	public void testGetAllProgramList() {
		List<GradProgram> gradProgramList = new ArrayList<>();
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		obj.setProgramEndDate(new Date(System.currentTimeMillis()));
		gradProgramList.add(obj);
		obj = new GradProgram();
		obj.setProgramCode("AC");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		obj.setProgramEndDate(new Date(System.currentTimeMillis()));
		gradProgramList.add(obj);
		
		Mockito.when(codeService.getAllProgramList()).thenReturn(gradProgramList);
		codeController.getAllPrograms();
		Mockito.verify(codeService).getAllProgramList();
	}
	
	@Test
	public void testGetSpecificProgramCode() {
		String programCode = "AB";
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		obj.setProgramEndDate(new Date(System.currentTimeMillis()));
		
		Mockito.when(codeService.getSpecificProgramCode(programCode)).thenReturn(obj);
		codeController.getSpecificProgramCode(programCode);
		Mockito.verify(codeService).getSpecificProgramCode(programCode);
	}
	
	@Test
	public void testGetSpecificProgramCode_noContent() {
		String programCode = "AB";		
		Mockito.when(codeService.getSpecificProgramCode(programCode)).thenReturn(null);
		codeController.getSpecificProgramCode(programCode);
		Mockito.verify(codeService).getSpecificProgramCode(programCode);
	}
	
	@Test
	public void testGetAllCountryList() {
		List<GradCountry> gradCountryList = new ArrayList<>();
		GradCountry obj = new GradCountry();
		obj.setCountryCode("CA");
		obj.setCountryName("Canada");
		gradCountryList.add(obj);
		obj = new GradCountry();
		obj.setCountryCode("USA");
		obj.setCountryName("America");
		gradCountryList.add(obj);
		Mockito.when(codeService.getAllCountryCodeList()).thenReturn(gradCountryList);
		codeController.getAllCountryCodeList();
		Mockito.verify(codeService).getAllCountryCodeList();
	}
	
	@Test
	public void testGetSpecificCountryCode() {
		String countryCode = "CA";
		GradCountry obj = new GradCountry();
		obj.setCountryCode("CA");
		obj.setCountryName("Canada");
		Mockito.when(codeService.getSpecificCountryCode(countryCode)).thenReturn(obj);
		codeController.getSpecificCountryCode(countryCode);
		Mockito.verify(codeService).getSpecificCountryCode(countryCode);
	}
	
	@Test
	public void testGetSpecificCountryCode_noContent() {
		String countryCode = "AB";	
		Mockito.when(codeService.getSpecificCountryCode(countryCode)).thenReturn(null);
		codeController.getSpecificCountryCode(countryCode);
		Mockito.verify(codeService).getSpecificCountryCode(countryCode);
	}
	
	@Test
	public void testGetAllProvinceList() {
		List<GradProvince> gradProvinceList = new ArrayList<>();
		GradProvince obj = new GradProvince();
		obj.setProvCode("CA");
		obj.setProvName("Canada");
		gradProvinceList.add(obj);
		obj = new GradProvince();
		obj.setProvCode("USA");
		obj.setProvName("America");
		gradProvinceList.add(obj);
		Mockito.when(codeService.getAllProvinceCodeList()).thenReturn(gradProvinceList);
		codeController.getAllProvinceCodeList();
		Mockito.verify(codeService).getAllProvinceCodeList();
	}
	
	@Test
	public void testGetSpecificProvinceCode() {
		String countryCode = "CA";
		GradProvince obj = new GradProvince();
		obj.setProvCode("CA");
		obj.setProvName("Canada");
		Mockito.when(codeService.getSpecificProvinceCode(countryCode)).thenReturn(obj);
		codeController.getSpecificProvinceCode(countryCode);
		Mockito.verify(codeService).getSpecificProvinceCode(countryCode);
	}
	
	@Test
	public void testGetSpecificProvinceCode_noContent() {
		String countryCode = "AB";	
		Mockito.when(codeService.getSpecificProvinceCode(countryCode)).thenReturn(null);
		codeController.getSpecificProvinceCode(countryCode);
		Mockito.verify(codeService).getSpecificProvinceCode(countryCode);
	}
	
	@Test
	public void testGetAllUngradReasonCodeList() {
		List<GradUngradReasons> gradUngradReasonList = new ArrayList<>();
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradUngradReasonList.add(obj);
		obj = new GradUngradReasons();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradUngradReasonList.add(obj);
		Mockito.when(codeService.getAllUngradReasonCodeList()).thenReturn(gradUngradReasonList);
		codeController.getAllUngradReasonCodeList();
		Mockito.verify(codeService).getAllUngradReasonCodeList();
	}
	
	@Test
	public void testGetSpecificUngradReasonCode() {
		String reasonCode = "DC";
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificUngradReasonCode(reasonCode)).thenReturn(obj);
		codeController.getSpecificUngradReasonCode(reasonCode);
		Mockito.verify(codeService).getSpecificUngradReasonCode(reasonCode);
	}
	
	@Test
	public void testGetSpecificUngradReasonCode_noContent() {
		String reasonCode = "AB";	
		Mockito.when(codeService.getSpecificUngradReasonCode(reasonCode)).thenReturn(null);
		codeController.getSpecificUngradReasonCode(reasonCode);
		Mockito.verify(codeService).getSpecificUngradReasonCode(reasonCode);
	}
	
	@Test
	public void testGetAllCertificateTypesCodeList() {
		List<GradCertificateTypes> gradCertificateTypesList = new ArrayList<>();
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradCertificateTypesList.add(obj);
		obj = new GradCertificateTypes();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradCertificateTypesList.add(obj);
		Mockito.when(codeService.getAllCertificateTypeCodeList()).thenReturn(gradCertificateTypesList);
		codeController.getAllCertificateTypeCodeList();
		Mockito.verify(codeService).getAllCertificateTypeCodeList();
	}
	
	@Test
	public void testGetSpecificCertificateTypesCode() {
		String certificateType = "DC";
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificCertificateTypeCode(certificateType)).thenReturn(obj);
		codeController.getSpecificCertificateTypeCode(certificateType);
		Mockito.verify(codeService).getSpecificCertificateTypeCode(certificateType);
	}
	
	@Test
	public void testGetSpecificCertificateTypesCode_noContent() {
		String certificateType = "AB";	
		Mockito.when(codeService.getSpecificCertificateTypeCode(certificateType)).thenReturn(null);
		codeController.getSpecificCertificateTypeCode(certificateType);
		Mockito.verify(codeService).getSpecificCertificateTypeCode(certificateType);
	}
	
	@Test
	public void testGetAllMessagingCodeList() {
		List<GradMessaging> gradMessagingList = new ArrayList<>();
		GradMessaging obj = new GradMessaging();
		obj.setProgramCode("2018-EN");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradMessagingList.add(obj);
		obj = new GradMessaging();
		obj.setProgramCode("2018-PF");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradMessagingList.add(obj);
		Mockito.when(codeService.getAllGradMessagingList()).thenReturn(gradMessagingList);
		codeController.getAllGradMessagingList();
		Mockito.verify(codeService).getAllGradMessagingList();
	}
	
	@Test
	public void testGetSpecificMessagingCode() {
		String programCode = "2018-EN";
		String msgType = "GRADUATION";
		GradMessaging obj = new GradMessaging();
		obj.setProgramCode("2018-EN");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificGradMessagingCode(programCode,msgType)).thenReturn(obj);
		codeController.getSpecificGradMessagingCode(programCode,msgType);
		Mockito.verify(codeService).getSpecificGradMessagingCode(programCode,msgType);
	}
	
	@Test
	public void testGetSpecificMessagingCode_noContent() {
		String programCode = "2018-ENF";
		String msgType = "GRADUATION";	
		Mockito.when(codeService.getSpecificGradMessagingCode(programCode,msgType)).thenReturn(null);
		codeController.getSpecificGradMessagingCode(programCode,msgType);
		Mockito.verify(codeService).getSpecificGradMessagingCode(programCode,msgType);
	}
	
	@Test
	public void testGetAllReportTypesCodeList() {
		List<GradReportTypes> gradReportTypesList = new ArrayList<>();
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradReportTypesList.add(obj);
		obj = new GradReportTypes();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradReportTypesList.add(obj);
		Mockito.when(codeService.getAllReportTypeCodeList()).thenReturn(gradReportTypesList);
		codeController.getAllReportTypeCodeList();
		Mockito.verify(codeService).getAllReportTypeCodeList();
	}
	
	@Test
	public void testGetSpecificReportTypesCode() {
		String reportType = "DC";
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificReportTypeCode(reportType)).thenReturn(obj);
		codeController.getSpecificReportTypeCode(reportType);
		Mockito.verify(codeService).getSpecificReportTypeCode(reportType);
	}
	
	@Test
	public void testGetSpecificReportTypesCode_noContent() {
		String reportType = "AB";	
		Mockito.when(codeService.getSpecificReportTypeCode(reportType)).thenReturn(null);
		codeController.getSpecificReportTypeCode(reportType);
		Mockito.verify(codeService).getSpecificReportTypeCode(reportType);
	}
	
	@Test
	public void testGetAllRequirementTypesCodeList() {
		List<GradRequirementTypes> gradRequirementTypesList = new ArrayList<>();
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradRequirementTypesList.add(obj);
		obj = new GradRequirementTypes();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradRequirementTypesList.add(obj);
		Mockito.when(codeService.getAllRequirementTypeCodeList()).thenReturn(gradRequirementTypesList);
		codeController.getAllRequirementTypeCodeList();
		Mockito.verify(codeService).getAllRequirementTypeCodeList();
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode() {
		String requirementType = "DC";
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificRequirementTypeCode(requirementType)).thenReturn(obj);
		codeController.getSpecificRequirementTypeCode(requirementType);
		Mockito.verify(codeService).getSpecificRequirementTypeCode(requirementType);
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode_noContent() {
		String requirementType = "AB";	
		Mockito.when(codeService.getSpecificRequirementTypeCode(requirementType)).thenReturn(null);
		codeController.getSpecificRequirementTypeCode(requirementType);
		Mockito.verify(codeService).getSpecificRequirementTypeCode(requirementType);
	}
	
	@Test
	public void testGetAllCareerProgramCodeList() {
		List<GradCareerProgram> gradCareerProgramList = new ArrayList<>();
		GradCareerProgram obj = new GradCareerProgram();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		gradCareerProgramList.add(obj);
		obj = new GradCareerProgram();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		gradCareerProgramList.add(obj);
		Mockito.when(codeService.getAllCareerProgramCodeList()).thenReturn(gradCareerProgramList);
		codeController.getAllCareerPrograms();
		Mockito.verify(codeService).getAllCareerProgramCodeList();
	}
	
	@Test
	public void testGetSpecificCareerProgramCode() {
		String requirementType = "DC";
		GradCareerProgram obj = new GradCareerProgram();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		Mockito.when(codeService.getSpecificCareerProgramCode(requirementType)).thenReturn(obj);
		codeController.getSpecificCareerProgramCode(requirementType);
		Mockito.verify(codeService).getSpecificCareerProgramCode(requirementType);
	}
	
	@Test
	public void testGetSpecificCareerProgramCode_noContent() {
		String requirementType = "AB";	
		Mockito.when(codeService.getSpecificCareerProgramCode(requirementType)).thenReturn(null);
		codeController.getSpecificCareerProgramCode(requirementType);
		Mockito.verify(codeService).getSpecificCareerProgramCode(requirementType);
	}
	
	@Test
	public void testGetAllStudentStatusCodeList() {
		List<StudentStatus> studentStatusList = new ArrayList<>();
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		studentStatusList.add(obj);
		obj = new StudentStatus();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		studentStatusList.add(obj);
		Mockito.when(codeService.getAllStudentStatusCodeList()).thenReturn(studentStatusList);
		codeController.getAllStudentStatusCodeList();
		Mockito.verify(codeService).getAllStudentStatusCodeList();
	}
	
	@Test
	public void testGetSpecificStudentStatusCode() {
		String requirementType = "DC";
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificStudentStatusCode(requirementType)).thenReturn(obj);
		codeController.getSpecificStudentStatusCode(requirementType);
		Mockito.verify(codeService).getSpecificStudentStatusCode(requirementType);
	}
	
	@Test
	public void testGetSpecificStudentStatusCode_noContent() {
		String requirementType = "AB";	
		Mockito.when(codeService.getSpecificStudentStatusCode(requirementType)).thenReturn(null);
		codeController.getSpecificStudentStatusCode(requirementType);
		Mockito.verify(codeService).getSpecificStudentStatusCode(requirementType);
	}
	
	@Test
	public void testCreateStudentStatus() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.createStudentStatus(obj)).thenReturn(obj);
		codeController.createStudentStatus(obj);
		Mockito.verify(codeService).createStudentStatus(obj);
	}
	
	@Test
	public void testUpdateStudentStatus() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.updateStudentStatus(obj)).thenReturn(obj);
		codeController.updateStudentStatusCode(obj);
		Mockito.verify(codeService).updateStudentStatus(obj);
	}
	
	@Test
	public void testDeleteStudentStatus() {
		String statusCode = "DC";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(codeService.deleteStudentStatus(statusCode,null)).thenReturn(1);
		codeController.deleteStudentStatusCodes(statusCode);
		Mockito.verify(codeService).deleteStudentStatus(statusCode,null);
	}
	
	@Test
	public void testCreateGradUngradReasons() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.createGradUngradReasons(obj)).thenReturn(obj);
		codeController.createGradUngradReasons(obj);
		Mockito.verify(codeService).createGradUngradReasons(obj);
	}
	
	@Test
	public void testUpdateGradUngradReasons() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.updateGradUngradReasons(obj)).thenReturn(obj);
		codeController.updateGradUngradReasons(obj);
		Mockito.verify(codeService).updateGradUngradReasons(obj);
	}
	
	@Test
	public void testDeleteGradUngradReasons() {
		String statusCode = "DC";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(codeService.deleteGradUngradReasons(statusCode,null)).thenReturn(1);
		codeController.deleteGradUngradReasons(statusCode);
		Mockito.verify(codeService).deleteGradUngradReasons(statusCode,null);
	}
	
	@Test
	public void testCreateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.createGradCertificateTypes(obj)).thenReturn(obj);
		codeController.createGradCertificateTypes(obj);
		Mockito.verify(codeService).createGradCertificateTypes(obj);
	}
	
	@Test
	public void testUpdateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.updateGradCertificateTypes(obj)).thenReturn(obj);
		codeController.updateGradCertificateTypes(obj);
		Mockito.verify(codeService).updateGradCertificateTypes(obj);
	}
	
	@Test
	public void testDeleteGradCertificateTypes() {
		String statusCode = "DC";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(codeService.deleteGradCertificateTypes(statusCode,null)).thenReturn(1);
		codeController.deleteGradCertificateTypes(statusCode);
		Mockito.verify(codeService).deleteGradCertificateTypes(statusCode,null);
	}
	
	@Test
	public void testCreateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.createGradReportTypes(obj)).thenReturn(obj);
		codeController.createGradReportTypes(obj);
		Mockito.verify(codeService).createGradReportTypes(obj);
	}
	
	@Test
	public void testUpdateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.updateGradReportTypes(obj)).thenReturn(obj);
		codeController.updateGradReportTypes(obj);
		Mockito.verify(codeService).updateGradReportTypes(obj);
	}
	
	@Test
	public void testDeleteGradReportTypes() {
		String statusCode = "DC";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(codeService.deleteGradReportTypes(statusCode,null)).thenReturn(1);
		codeController.deleteGradReportTypes(statusCode);
		Mockito.verify(codeService).deleteGradReportTypes(statusCode,null);
	}
	
	@Test
	public void testCreateGradRequirementTypes() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.createGradRequirementTypes(obj)).thenReturn(obj);
		codeController.createGradRequirementTypes(obj);
		Mockito.verify(codeService).createGradRequirementTypes(obj);
	}
	
	@Test
	public void testUpdateGradRequirementTypes() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.updateGradRequirementTypes(obj)).thenReturn(obj);
		codeController.updateGradRequirementTypes(obj);
		Mockito.verify(codeService).updateGradRequirementTypes(obj);
	}
	
	@Test
	public void testDeleteGradRequirementTypes() {
		String statusCode = "DC";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(codeService.deleteGradRequirementTypes(statusCode,null)).thenReturn(1);
		codeController.deleteGradRequirementTypes(statusCode);
		Mockito.verify(codeService).deleteGradRequirementTypes(statusCode,null);
	}
}
