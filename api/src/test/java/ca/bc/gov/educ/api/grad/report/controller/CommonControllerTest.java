package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.service.CommonService;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import ca.bc.gov.educ.api.grad.report.util.ResponseHelper;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommonControllerTest {

    @Mock
    private CommonService commonService;

    @Mock
    ResponseHelper responseHelper;

    @Mock
    GradValidation validation;

    @InjectMocks
    private CommonController commonController;


    @Test
    public void testGetAllStudentUngradReasonsList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        final GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons Data
        final List<GradStudentUngradReasons> gradStudentUngradReasonsList = new ArrayList<>();
        final GradStudentUngradReasons studentUngradReason1 = new GradStudentUngradReasons();
        studentUngradReason1.setId(UUID.randomUUID());
        studentUngradReason1.setPen("123456789");
        studentUngradReason1.setStudentID(studentID);
        studentUngradReason1.setUngradReasonCode(gradUngradReason.getCode());
        gradStudentUngradReasonsList.add(studentUngradReason1);

        final GradStudentUngradReasons studentUngradReason2 = new GradStudentUngradReasons();
        studentUngradReason2.setId(UUID.randomUUID());
        studentUngradReason2.setPen("123456789");
        studentUngradReason2.setStudentID(studentID);
        studentUngradReason2.setUngradReasonCode(gradUngradReason.getCode());
        gradStudentUngradReasonsList.add(studentUngradReason2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllStudentUngradReasonsList(studentID, null)).thenReturn(gradStudentUngradReasonsList);
        commonController.getAllStudentUngradReasonsList(studentID.toString());
        Mockito.verify(commonService).getAllStudentUngradReasonsList(studentID, null);

    }

    @Test
    public void testCreateGradStudentUngradReason() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        final GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons
        final GradStudentUngradReasons studentUngradReason = new GradStudentUngradReasons();
        studentUngradReason.setPen("123456789");
        studentUngradReason.setStudentID(studentID);
        studentUngradReason.setUngradReasonCode(gradUngradReason.getCode());

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.createGradStudentUngradReasons(studentUngradReason, null)).thenReturn(studentUngradReason);
        commonController.createGradStudentUngradReason(studentID.toString(), studentUngradReason);
        Mockito.verify(commonService).createGradStudentUngradReasons(studentUngradReason, null);
    }

    @Test
    public void testGetStudentUngradReasons() {
        final String reasonCode = "TEST";

        Mockito.when(commonService.getStudentUngradReasons(reasonCode)).thenReturn(true);
        commonController.getStudentUngradReasons(reasonCode);
        Mockito.verify(commonService).getStudentUngradReasons(reasonCode);
    }

    @Test
    public void testGetStudentCareerProgram() {
        final String programCode = "2018-EN";

       Mockito.when(commonService.getStudentCareerProgram(programCode)).thenReturn(true);
       commonController.getStudentCareerProgram(programCode);
       Mockito.verify(commonService).getStudentCareerProgram(programCode);
    }

    @Test
    public void testGetAllStudentCareerProgramsList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Career Program
        final GradCareerProgram gradCareerProgram = new GradCareerProgram();
        gradCareerProgram.setCode("TEST");
        gradCareerProgram.setDescription("Test Code Name");

        // Student Career Program Data
        final List<GradStudentCareerProgram> gradStudentCareerProgramList = new ArrayList<>();
        final GradStudentCareerProgram studentCareerProgram1 = new GradStudentCareerProgram();
        studentCareerProgram1.setId(UUID.randomUUID());
        studentCareerProgram1.setPen(pen);
        studentCareerProgram1.setStudentID(studentID);
        studentCareerProgram1.setCareerProgramCode(gradCareerProgram.getCode());
        gradStudentCareerProgramList.add(studentCareerProgram1);

        final GradStudentCareerProgram studentCareerProgram2 = new GradStudentCareerProgram();
        studentCareerProgram2.setId(UUID.randomUUID());
        studentCareerProgram2.setPen(pen);
        studentCareerProgram2.setStudentID(studentID);
        studentCareerProgram2.setCareerProgramCode(gradCareerProgram.getCode());
        gradStudentCareerProgramList.add(studentCareerProgram2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllGradStudentCareerProgramList(pen, null)).thenReturn(gradStudentCareerProgramList);
        commonController.getAllStudentCareerProgramsList(pen);
        Mockito.verify(commonService).getAllGradStudentCareerProgramList(pen, null);
    }

    @Test
    public void testGetStudentCertificate() {
        final String certificateTypeCode = "TEST";

        Mockito.when(commonService.getStudentCertificate(certificateTypeCode)).thenReturn(true);
        commonController.getStudentCertificate(certificateTypeCode);
        Mockito.verify(commonService).getStudentCertificate(certificateTypeCode);
    }

    @Test
    public void testSaveStudentCertificate() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final  GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("Test Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        Mockito.when(commonService.saveGradCertificates(studentCertificate)).thenReturn(studentCertificate);
        commonController.saveStudentCertificate(studentCertificate);
        Mockito.verify(commonService).saveGradCertificates(studentCertificate);
    }

    @Test
    public void testGetStudentReport() {
        final String reportTypeCode = "TEST";
        Mockito.when(commonService.getStudentReport(reportTypeCode)).thenReturn(Boolean.TRUE);
        commonController.getStudentReport(reportTypeCode);
        Mockito.verify(commonService).getStudentReport(reportTypeCode);
    }

    @Test
    public void testSaveStudentReport() {
        // ID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";

        final GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        Mockito.when(commonService.saveGradReports(gradStudentReport)).thenReturn(gradStudentReport);
        commonController.saveStudentReport(gradStudentReport);
        Mockito.verify(commonService).saveGradReports(gradStudentReport);
    }

    @Test
    public void testGetStudentReportByType() {
        // ID
        final UUID studentID = new UUID(1, 1);
        final String reportTypeCode = "TEST";
        final String reportBody = "Test Report Body";

        byte[] certificateByte = Base64.decodeBase64(reportBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+reportTypeCode+"_report.pdf");

        Mockito.when(commonService.getStudentReportByType(studentID, reportTypeCode)).thenReturn(
                ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis)));
        commonController.getStudentReportByType(studentID.toString(), reportTypeCode);
        Mockito.verify(commonService).getStudentReportByType(studentID, reportTypeCode);

    }

    @Test
    public void testGetStudentCertificateByType() {
        final UUID studentID = new UUID(1, 1);
        final String certificateTypeCode = "TEST";
        final String certificateBody = "Test Certificate Body";

        byte[] certificateByte = Base64.decodeBase64(certificateBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+certificateTypeCode+"_certificate.pdf");

        Mockito.when(commonService.getStudentCertificateByType(studentID, certificateTypeCode)).thenReturn(
                ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis)));
        commonController.getStudentCertificateByType(studentID.toString(), certificateTypeCode);
        Mockito.verify(commonService).getStudentCertificateByType(studentID, certificateTypeCode);
    }

    @Test
    public void testGetAllStudentCertificateList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final List<GradStudentCertificates> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentCertificates studentCertificate1 = new GradStudentCertificates();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setPen(pen);
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate1);

        final GradStudentCertificates studentCertificate2 = new GradStudentCertificates();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setPen(pen);
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllStudentCertificateList(studentID, null)).thenReturn(gradStudentCertificatesList);
        commonController.getAllStudentCertificateList(studentID.toString());
        Mockito.verify(commonService).getAllStudentCertificateList(studentID, null);
    }

    @Test
    public void testGetAlgorithmRulesList() {
        // UUID
        final UUID ID = UUID.randomUUID();
        final String programCode = "2018-EN";

        // Student Certificate Types
        final  List<GradAlgorithmRules> algorithmsRulesList = new ArrayList<>();

        final GradAlgorithmRules gradAlgorithmRule1 = new GradAlgorithmRules();
        gradAlgorithmRule1.setId(ID);
        gradAlgorithmRule1.setRuleName("Test1");
        gradAlgorithmRule1.setRuleDescription("Test1 Description");
        gradAlgorithmRule1.setProgramCode(programCode);
        gradAlgorithmRule1.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule1);

        final GradAlgorithmRules gradAlgorithmRule2 = new GradAlgorithmRules();
        gradAlgorithmRule2.setId(ID);
        gradAlgorithmRule2.setRuleName("Test2");
        gradAlgorithmRule2.setRuleDescription("Test2 Description");
        gradAlgorithmRule2.setProgramCode(programCode);
        gradAlgorithmRule2.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule2);

        Mockito.when(commonService.getAlgorithmRulesList(programCode)).thenReturn(algorithmsRulesList);
        commonController.getAlgorithmRulesList(programCode);
        Mockito.verify(commonService).getAlgorithmRulesList(programCode);
    }

    @Test
    public void testGetAllAlgorithmRulesList() {
        // UUID
        final UUID ID = UUID.randomUUID();
        final String programCode = "2018-EN";

        // Student Certificate Types
        final  List<GradAlgorithmRules> algorithmsRulesList = new ArrayList<>();

        final GradAlgorithmRules gradAlgorithmRule1 = new GradAlgorithmRules();
        gradAlgorithmRule1.setId(ID);
        gradAlgorithmRule1.setRuleName("Test1");
        gradAlgorithmRule1.setRuleDescription("Test1 Description");
        gradAlgorithmRule1.setProgramCode(programCode);
        gradAlgorithmRule1.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule1);

        final GradAlgorithmRules gradAlgorithmRule2 = new GradAlgorithmRules();
        gradAlgorithmRule2.setId(ID);
        gradAlgorithmRule2.setRuleName("Test2");
        gradAlgorithmRule2.setRuleDescription("Test2 Description");
        gradAlgorithmRule2.setProgramCode(programCode);
        gradAlgorithmRule2.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule2);

        Mockito.when(commonService.getAllAlgorithmRulesList()).thenReturn(algorithmsRulesList);
        commonController.getAllAlgorithmRulesList();
        Mockito.verify(commonService).getAllAlgorithmRulesList();
    }

    @Test
    public void testGetAllStudentNotes() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";

        final List<StudentNote> allNotesList = new ArrayList<>();

        final StudentNote note1 = new StudentNote();
        note1.setId(UUID.randomUUID());
        note1.setStudentID(studentID.toString());
        note1.setPen(pen);
        note1.setNote("Test1 Comments");
        note1.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
        allNotesList.add(note1);

        final StudentNote note2 = new StudentNote();
        note2.setId(UUID.randomUUID());
        note2.setStudentID(studentID.toString());
        note2.setPen(pen);
        note2.setNote("Test2 Comments");
        note2.setUpdatedTimestamp(new Date(System.currentTimeMillis() + 100000L));
        allNotesList.add(note2);

        Mockito.when(commonService.getAllStudentNotes(pen)).thenReturn(allNotesList);
        commonController.getAllStudentNotes(pen);
        Mockito.verify(commonService).getAllStudentNotes(pen);
    }

    @Test
    public void testSaveStudentNotes() {
        // ID
        final UUID noteID = UUID.randomUUID();
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";

        final StudentNote studentNote = new StudentNote();
        studentNote.setId(noteID);
        studentNote.setStudentID(studentID.toString());
        studentNote.setPen(pen);
        studentNote.setNote("Test Note Body");

        Mockito.when(commonService.saveStudentNote(studentNote)).thenReturn(studentNote);
        commonController.saveStudentNotes(studentNote);
        Mockito.verify(commonService).saveStudentNote(studentNote);
    }

    @Test
    public void testDeleteNotes() {
        // ID
        final UUID noteID = UUID.randomUUID();

        Mockito.when(commonService.deleteNote(noteID)).thenReturn(1);
        commonController.deleteNotes(noteID.toString());
        Mockito.verify(commonService).deleteNote(noteID);
    }


}
