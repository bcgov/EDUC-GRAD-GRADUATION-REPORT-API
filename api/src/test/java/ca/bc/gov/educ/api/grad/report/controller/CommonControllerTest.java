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

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

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
        boolean isGraduated= false;
        final GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");
        gradStudentReport.setDocumentStatusCode("IP");

        Mockito.when(commonService.saveGradReports(gradStudentReport,isGraduated)).thenReturn(gradStudentReport);
        commonController.saveStudentReport(gradStudentReport,isGraduated);
        Mockito.verify(commonService).saveGradReports(gradStudentReport,isGraduated);
    }

    @Test
    public void testSaveStudentTranscript() {
        // ID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        boolean isGraduated= false;
        final GradStudentTranscripts gradStudentReport = new GradStudentTranscripts();
        gradStudentReport.setTranscriptTypeCode(reportTypeCode);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setTranscript("TEST Report Body");
        gradStudentReport.setDocumentStatusCode("IP");

        Mockito.when(commonService.saveGradTranscripts(gradStudentReport,isGraduated)).thenReturn(gradStudentReport);
        commonController.saveStudentTranscript(gradStudentReport,isGraduated);
        Mockito.verify(commonService).saveGradTranscripts(gradStudentReport,isGraduated);
    }

    @Test
    public void testGetStudentReportByType() {
        // ID
        final UUID studentID = new UUID(1, 1);
        final String reportTypeCode = "TEST";
        final String reportBody = "Test Report Body";
        final String documentStatusCode = "IP";
        byte[] certificateByte = Base64.decodeBase64(reportBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+reportTypeCode+"_report.pdf");

        Mockito.when(commonService.getStudentReportByType(studentID, reportTypeCode,documentStatusCode)).thenReturn(
                ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis)));
        commonController.getStudentReportByType(studentID.toString(), reportTypeCode,documentStatusCode);
        Mockito.verify(commonService).getStudentReportByType(studentID, reportTypeCode,documentStatusCode);

    }

    @Test
    public void testGetStudentCertificateByType() {
        final UUID studentID = new UUID(1, 1);
        final String certificateTypeCode = "TEST";
        final String certificateBody = "Test Certificate Body";
        final String documentStatusCode = "COMPL";
        byte[] certificateByte = Base64.decodeBase64(certificateBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+certificateTypeCode+"_certificate.pdf");

        Mockito.when(commonService.getStudentCertificateByType(studentID, certificateTypeCode,documentStatusCode)).thenReturn(
                ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis)));
        commonController.getStudentCertificateByType(studentID.toString(), certificateTypeCode,documentStatusCode);
        Mockito.verify(commonService).getStudentCertificateByType(studentID, certificateTypeCode,documentStatusCode);
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

        Mockito.when(commonService.getAllStudentCertificateList(studentID)).thenReturn(gradStudentCertificatesList);
        commonController.getAllStudentCertificateList(studentID.toString());
        Mockito.verify(commonService).getAllStudentCertificateList(studentID);
    }

    @Test
    public void testGetAllStudentTranscriptList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final TranscriptTypes gradCertificateType = new TranscriptTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final List<GradStudentTranscripts> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentTranscripts studentCertificate1 = new GradStudentTranscripts();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setTranscriptTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate1);

        final GradStudentTranscripts studentCertificate2 = new GradStudentTranscripts();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setTranscriptTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate2);

        Mockito.when(commonService.getAllStudentTranscriptList(studentID)).thenReturn(gradStudentCertificatesList);
        commonController.getAllStudentTranscriptList(studentID.toString());
        Mockito.verify(commonService).getAllStudentTranscriptList(studentID);
    }
    
    @Test
    public void testGetAllStudentReportList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradReportTypes gradCertificateType = new GradReportTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final List<GradStudentReports> gradStudentReportList = new ArrayList<>();
        final GradStudentReports studentCertificate1 = new GradStudentReports();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setPen(pen);
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setGradReportTypeCode(gradCertificateType.getCode());
        gradStudentReportList.add(studentCertificate1);

        final GradStudentReports studentCertificate2 = new GradStudentReports();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setPen(pen);
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setGradReportTypeCode(gradCertificateType.getCode());
        gradStudentReportList.add(studentCertificate2);

        Mockito.when(commonService.getAllStudentReportList(studentID)).thenReturn(gradStudentReportList);
        commonController.getAllStudentReportsList(studentID.toString());
        Mockito.verify(commonService).getAllStudentReportList(studentID);
    }
    
    @Test
    public void testDeleteAllStudentAchievements() {
    	UUID studentID = new UUID(1, 1);
        int returnValue = 1;
    	Mockito.when(commonService.getAllStudentAchievement(studentID)).thenReturn(returnValue);
    	commonController.deleteAllStudentAchievements(studentID.toString());
        assertEquals(1,returnValue);
    }

    @Test
    public void testGetStudentTranscriptByType() {
        final UUID studentID = new UUID(1, 1);
        final String transcriptTypeCode = "TEST";
        final String transcriptBody = "Test Certificate Body";
        final String documentStatusCode = "COMPL";
        byte[] certificateByte = Base64.decodeBase64(transcriptBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+transcriptTypeCode+"_transcript.pdf");

        Mockito.when(commonService.getStudentTranscriptByType(studentID, transcriptTypeCode,documentStatusCode)).thenReturn(
                ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis)));
        commonController.getStudentTranscriptByType(studentID.toString(), transcriptTypeCode,documentStatusCode);
        Mockito.verify(commonService).getStudentTranscriptByType(studentID, transcriptTypeCode,documentStatusCode);
    }

    @Test
    public void testGetAllStudentTranscriptDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL");
        list.add(cred);

        Mockito.when(commonService.getAllStudentTranscriptDistributionList()).thenReturn(list);
        commonController.getAllStudentTranscriptDistribution();
        Mockito.verify(commonService).getAllStudentTranscriptDistributionList();
    }

    @Test
    public void testGetAllStudentCertificateDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL");
        list.add(cred);

        Mockito.when(commonService.getAllStudentCertificateDistributionList()).thenReturn(list);
        commonController.getAllStudentCertificateDistribution();
        Mockito.verify(commonService).getAllStudentCertificateDistributionList();
    }

    @Test
    public void testArchiveAllStudentAchievements() {
        UUID studentID = new UUID(1, 1);
        Mockito.when(commonService.archiveAllStudentAchievements(studentID)).thenReturn(1);
        commonController.archiveAllStudentAchievements(studentID.toString());
        Mockito.verify(commonService).archiveAllStudentAchievements(studentID);
    }

    @Test
    public void testSaveSchoolReport() {

        final String schoolOfRecord = "123456789";
        final String reportTypeCode = "TEST";
        final SchoolReports schoolReports = new SchoolReports();
        schoolReports.setReportTypeCode(reportTypeCode);
        schoolReports.setSchoolOfRecord(schoolOfRecord);
        schoolReports.setReport("TEST Report Body");

        Mockito.when(commonService.saveSchoolReports(schoolReports)).thenReturn(schoolReports);
        commonController.saveSchoolReport(schoolReports);
        Mockito.verify(commonService).saveSchoolReports(schoolReports);
    }

    @Test
    public void testGetSchoolReportByType() {
        // ID
        final String mincode = "123213211";
        final String reportTypeCode = "TEST";
        final String reportBody = "Test Report Body";
        byte[] certificateByte = Base64.decodeBase64(reportBody.getBytes(StandardCharsets.US_ASCII));
        ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_"+reportTypeCode+"_report.pdf");

        Mockito.when(commonService.getSchoolReportByType(mincode, reportTypeCode)).thenReturn(
                ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis)));
        commonController.getSchoolReportByType(mincode, reportTypeCode);
        Mockito.verify(commonService).getSchoolReportByType(mincode, reportTypeCode);

    }

    @Test
    public void testGetAllSchoolReportList() {
        // UUID
        final String mincode = "123123123";
        final String pen = "123456789";// Certificate Type
        final GradReportTypes gradCertificateType = new GradReportTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final List<SchoolReports> gradStudentReportList = new ArrayList<>();
        final SchoolReports studentCertificate1 = new SchoolReports();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setSchoolOfRecord(mincode);
        studentCertificate1.setReportTypeCode(gradCertificateType.getCode());
        gradStudentReportList.add(studentCertificate1);

        final SchoolReports studentCertificate2 = new SchoolReports();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setSchoolOfRecord(mincode);
        studentCertificate2.setReportTypeCode(gradCertificateType.getCode());
        gradStudentReportList.add(studentCertificate2);

        Mockito.when(commonService.getAllSchoolReportList(mincode,"accessToken")).thenReturn(gradStudentReportList);
        commonController.getAllSchoolReportsList(mincode,"accessToken");
        Mockito.verify(commonService).getAllSchoolReportList(mincode,"accessToken");
    }

    @Test
    public void testUpdateSchoolReport() {
        final String schoolOfRecord = "123456789";
        final String reportTypeCode = "TEST";
        Mockito.when(commonService.updateSchoolReports(schoolOfRecord,reportTypeCode)).thenReturn(true);
        commonController.updateSchoolReport(schoolOfRecord,reportTypeCode);
        Mockito.verify(commonService).updateSchoolReports(schoolOfRecord,reportTypeCode);
    }

    @Test
    public void testUpdateStudentCredential() {
        final UUID studentID = new UUID(1,1);
        final String schoolOfRecord = "123456789";
        final String credentialTypeCode = "TEST";
        final String paperType = "YED4";
        final String documentStatusCode = "TEST";
        Mockito.when(commonService.updateStudentCredential(studentID,credentialTypeCode,paperType,documentStatusCode)).thenReturn(true);
        commonController.updateStudentCredential(studentID.toString(),credentialTypeCode,paperType,documentStatusCode);
        Mockito.verify(commonService).updateStudentCredential(studentID,credentialTypeCode,paperType,documentStatusCode);
    }

    @Test
    public void testUpdateStudentCredential_YED2() {
        final UUID studentID = new UUID(1,1);
        final String schoolOfRecord = "123456789";
        final String credentialTypeCode = "TEST";
        final String paperType = "YED2";
        final String documentStatusCode = "TEST";
        Mockito.when(commonService.updateStudentCredential(studentID,credentialTypeCode,paperType,documentStatusCode)).thenReturn(true);
        commonController.updateStudentCredential(studentID.toString(),credentialTypeCode,paperType,documentStatusCode);
        Mockito.verify(commonService).updateStudentCredential(studentID,credentialTypeCode,paperType,documentStatusCode);
    }

    @Test
    public void testGetAllStudentCredentialDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String credentialType = "E";
        final StudentSearchRequest req = new StudentSearchRequest();
        req.setPens(List.of("2131231213"));

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL");
        list.add(cred);

        Mockito.when(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,"accessToken")).thenReturn(list);
        commonController.getStudentCredentialsForUserRequestDisRun(credentialType,req,"accessToken");
        Mockito.verify(commonService).getStudentCredentialsForUserRequestDisRun(credentialType,req,"accessToken");
    }

    @Test
    public void testGetAllStudentTranscriptDistributionYearlyList() {
        // Student Certificate Types
        final UUID studentID = new UUID(1,1);
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL");
        list.add(cred);

        Mockito.when(commonService.getAllStudentTranscriptYearlyDistributionList("accessToken")).thenReturn(list);
        commonController.getAllStudentTranscriptYearlyDistribution("accessToken");
        Mockito.verify(commonService).getAllStudentTranscriptYearlyDistributionList("accessToken");
    }

    @Test
    public void testGetAllSchoolReportDistributionList() {

        final String mincode = "123131123";

        // Student Certificate Types
        final List<SchoolReportDistribution> list = new ArrayList<>();
        final SchoolReportDistribution cred = new SchoolReportDistribution(UUID.randomUUID(),"GRAD",mincode);
        list.add(cred);

        Mockito.when(commonService.getAllSchoolReportDistributionList()).thenReturn(list);
        commonController.getAllSchoolReportDistribution();
        Mockito.verify(commonService).getAllSchoolReportDistributionList();
    }
}
