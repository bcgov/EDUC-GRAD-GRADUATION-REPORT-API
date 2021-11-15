package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.TranscriptTypes;
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
	public void testGetAllTranscriptTypesCodeList() {
		List<TranscriptTypes> tTypesList = new ArrayList<>();
		TranscriptTypes obj = new TranscriptTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		tTypesList.add(obj);
		obj = new TranscriptTypes();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		tTypesList.add(obj);
		Mockito.when(codeService.getAllTranscriptTypeCodeList()).thenReturn(tTypesList);
		codeController.getAllTranscriptTypeCodeList();
		Mockito.verify(codeService).getAllTranscriptTypeCodeList();
	}

	@Test
	public void testGetSpecificTranscriptTypesCode() {
		String tranTypeType = "DC";
		TranscriptTypes obj = new TranscriptTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(codeService.getSpecificTranscriptTypeCode(tranTypeType)).thenReturn(obj);
		codeController.getSpecificTranscriptTypeCode(tranTypeType);
		Mockito.verify(codeService).getSpecificTranscriptTypeCode(tranTypeType);
	}

	@Test
	public void testGetSpecificTranscriptTypesCode_noContent() {
		String tranTypeType = "AB";
		Mockito.when(codeService.getSpecificTranscriptTypeCode(tranTypeType)).thenReturn(null);
		codeController.getSpecificTranscriptTypeCode(tranTypeType);
		Mockito.verify(codeService).getSpecificTranscriptTypeCode(tranTypeType);
	}
}
