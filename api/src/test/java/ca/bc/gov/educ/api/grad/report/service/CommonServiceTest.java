package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CommonServiceTest {

    @Autowired EducGradReportApiConstants constants;
    @Autowired CommonService commonService;
    @MockBean GradStudentCertificatesRepository gradStudentCertificatesRepository;
    @MockBean GradStudentReportsRepository gradStudentReportsRepository;
    @MockBean GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    @MockBean GradCertificateTypesRepository gradCertificateTypesRepository;
	@MockBean GradReportTypesRepository gradReportTypesRepository;
	@MockBean DocumentStatusCodeRepository documentStatusCodeRepository;
    @MockBean TranscriptTypesRepository transcriptTypesRepository;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetStudentCertificate() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final List<GradStudentCertificatesEntity> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentCertificatesEntity studentCertificate = new GradStudentCertificatesEntity();
        studentCertificate.setId(UUID.randomUUID());
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate);

        when(gradStudentCertificatesRepository.existsByCertificateTypeCode(gradCertificateType.getCode())).thenReturn(gradStudentCertificatesList);
        var result = commonService.getStudentCertificate(gradCertificateType.getCode());
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveGradStudentCertificates_thenReturnCreateSuccess() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");
                
        final DocumentStatusCode documentStatus = new DocumentStatusCode();
        gradCertificateType.setCode("COMP");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("Test Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        // Student Certificate Types Entity
        final GradStudentCertificatesEntity studentCertificateEntity = new GradStudentCertificatesEntity();
        studentCertificateEntity.setPen(pen);
        studentCertificateEntity.setStudentID(studentID);
        studentCertificateEntity.setCertificate("Test Certificate Body");
        studentCertificateEntity.setGradCertificateTypeCode(gradCertificateType.getCode());

        final Optional<GradStudentCertificatesEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID, gradCertificateType.getCode(),documentStatus.getCode())).thenReturn(optionalEmpty);
        when(this.gradStudentCertificatesRepository.save(studentCertificateEntity)).thenReturn(studentCertificateEntity);

        var result = commonService.saveGradCertificates(studentCertificate);

        assertThat(result).isNotNull();
        assertThat(result.getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
    }

    @Test
    public void testSaveGradStudentCertificatesWithExistingOne_thenReturnUpdateSuccess() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        final DocumentStatusCode documentStatus = new DocumentStatusCode();
        documentStatus.setCode("COMPL");
        documentStatus.setDescription("Test Code Name");
        
        // Student Certificate Types
        final GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("Test Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());
        studentCertificate.setDocumentStatusCode("COMPL");

        // Student Certificate Types Entity
        final GradStudentCertificatesEntity studentCertificateEntity = new GradStudentCertificatesEntity();
        studentCertificateEntity.setPen(pen);
        studentCertificateEntity.setStudentID(studentID);
        studentCertificateEntity.setGradCertificateTypeCode(gradCertificateType.getCode());
        studentCertificateEntity.setDocumentStatusCode("COMPL");

        final Optional<GradStudentCertificatesEntity> optional = Optional.of(studentCertificateEntity);

        when(this.gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID, gradCertificateType.getCode(),documentStatus.getCode())).thenReturn(optional);
        when(this.gradStudentCertificatesRepository.save(studentCertificateEntity)).thenReturn(studentCertificateEntity);

        var result = commonService.saveGradCertificates(studentCertificate);

        assertThat(result).isNotNull();
        assertThat(result.getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
    }

    @Test
    public void testGetStudentReport() {
        final List<GradStudentReportsEntity> reportList = new ArrayList<>();
        final GradStudentReportsEntity report = new GradStudentReportsEntity();
        report.setId(UUID.randomUUID());
        report.setGradReportTypeCode("TEST");
        report.setPen("123456789");
        report.setStudentID(UUID.randomUUID());
        report.setReport("TEST Report Body");
        reportList.add(report);

        when(this.gradStudentReportsRepository.existsByReportTypeCode("TEST")).thenReturn(reportList);
        var result = commonService.getStudentReport("TEST");
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveGradReports_thenReturnCreateSuccess() {
        // ID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode="ARCH";
        final GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        final GradStudentReportsEntity gradStudentReportEntity = new GradStudentReportsEntity();
        gradStudentReportEntity.setGradReportTypeCode(reportTypeCode);
        gradStudentReportEntity.setPen(pen);
        gradStudentReportEntity.setStudentID(studentID);
        gradStudentReportEntity.setReport("TEST Report Body");

        final Optional<GradStudentReportsEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optionalEmpty);
        when(this.gradStudentReportsRepository.save(gradStudentReportEntity)).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradReports(gradStudentReport,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getGradReportTypeCode()).isEqualTo(gradStudentReport.getGradReportTypeCode());
    }

    @Test
    public void testSaveGradReportsWithExistingOne_thenReturnUpdateSuccess() {
        // ID
        final UUID reportID = UUID.randomUUID();
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode = "COMPL";
        final GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setId(reportID);
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        final GradStudentReportsEntity gradStudentReportEntity = new GradStudentReportsEntity();
        gradStudentReportEntity.setId(reportID);
        gradStudentReportEntity.setGradReportTypeCode(reportTypeCode);
        gradStudentReportEntity.setPen(pen);
        gradStudentReportEntity.setStudentID(studentID);
        gradStudentReportEntity.setReport("TEST Report Body");

        final Optional<GradStudentReportsEntity> optional = Optional.of(gradStudentReportEntity);

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optional);
        when(this.gradStudentReportsRepository.save(gradStudentReportEntity)).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradReports(gradStudentReport,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getGradReportTypeCode()).isEqualTo(gradStudentReport.getGradReportTypeCode());
    }

    @Test
    public void testGetStudentReportByType() {
        // ID
        final UUID reportID = UUID.randomUUID();
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        final String documentStatusCode = "IP";

        final GradStudentReportsEntity gradStudentReport = new GradStudentReportsEntity();
        gradStudentReport.setId(reportID);
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        when(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(studentID, reportTypeCode,documentStatusCode)).thenReturn(Optional.of(gradStudentReport));
        var result = commonService.getStudentReportByType(studentID, reportTypeCode,documentStatusCode);
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).isEqualTo("[inline; filename=student_TEST_report.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentCertificateByType() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        final GradStudentCertificatesEntity studentCertificate = new GradStudentCertificatesEntity();
        studentCertificate.setId(UUID.randomUUID());
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("TEST Certificate Body");
        studentCertificate.setDocumentStatusCode("COMPL");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());
       
        final DocumentStatusCode documentStatus = new DocumentStatusCode();
        documentStatus.setCode("COMP");
        documentStatus.setDescription("Test Code Name");
        
        when(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID, gradCertificateType.getCode(),documentStatus.getCode())).thenReturn(Optional.of(studentCertificate));
        var result = commonService.getStudentCertificateByType(studentID, gradCertificateType.getCode(),documentStatus.getCode());
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).isEqualTo("[inline; filename=student_TEST_certificate.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetAllStudentCertificateList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("SC");
        gradCertificateType.setDescription("School Completion Certificate");

        final DocumentStatusCodeEntity documentStatusCodeEntity = new DocumentStatusCodeEntity();
        documentStatusCodeEntity.setCode("COMPL");
        documentStatusCodeEntity.setDescription("School Completion Certificate");
        
        final DocumentStatusCode documentStatusCode = new DocumentStatusCode();
        documentStatusCode.setCode("COMPL");
        documentStatusCode.setDescription("School Completion Certificate");
        
        // Student Certificate Types
        final List<GradStudentCertificatesEntity> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentCertificatesEntity studentCertificate1 = new GradStudentCertificatesEntity();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setPen(pen);
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate1);

        final GradStudentCertificatesEntity studentCertificate2 = new GradStudentCertificatesEntity();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setPen(pen);
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate2);
        final GradCertificateTypesEntity gradCertificateTypesEntity = new GradCertificateTypesEntity();
        gradCertificateTypesEntity.setCode("SC");
        gradCertificateTypesEntity.setDescription("School Completion Certificate");
        
        when(gradStudentCertificatesRepository.findByStudentID(studentID)).thenReturn(gradStudentCertificatesList);
        when(gradCertificateTypesRepository.findById(gradCertificateType.getCode())).thenReturn(Optional.of(gradCertificateTypesEntity));
        when(documentStatusCodeRepository.findById(documentStatusCode.getCode())).thenReturn(Optional.of(documentStatusCodeEntity));
        var result = commonService.getAllStudentCertificateList(studentID);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
    }
    
    @Test
    public void testGetAllStudentReportList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final GradReportTypes gradReportTypes = new GradReportTypes();
        gradReportTypes.setCode("SC");
        gradReportTypes.setDescription("School Completion Certificate");
        
        final DocumentStatusCode documentStatusCode = new DocumentStatusCode();
        documentStatusCode.setCode("COMPL");
        documentStatusCode.setDescription("School Completion Certificate");

        // Student Certificate Types
        final List<GradStudentReportsEntity> gradStudentReportsList = new ArrayList<>();
        final GradStudentReportsEntity studentReport1 = new GradStudentReportsEntity();
        studentReport1.setId(UUID.randomUUID());
        studentReport1.setPen(pen);
        studentReport1.setStudentID(studentID);
        studentReport1.setGradReportTypeCode(gradReportTypes.getCode());
        studentReport1.setDocumentStatusCode("IP");
        gradStudentReportsList.add(studentReport1);

        final GradStudentReportsEntity studentReport2 = new GradStudentReportsEntity();
        studentReport2.setId(UUID.randomUUID());
        studentReport2.setPen(pen);
        studentReport2.setStudentID(studentID);
        studentReport2.setGradReportTypeCode(gradReportTypes.getCode());
        studentReport2.setDocumentStatusCode("IP");
        gradStudentReportsList.add(studentReport2);
        
        final GradReportTypesEntity gradReportTypesEntity = new GradReportTypesEntity();
        gradReportTypesEntity.setCode("SC");
        gradReportTypesEntity.setDescription("School Completion Certificate");
        
        final DocumentStatusCodeEntity documentStatusCodeEntity = new DocumentStatusCodeEntity();
        documentStatusCodeEntity.setCode("COMPL");
        documentStatusCodeEntity.setDescription("School Completion Certificate");
        
        when(gradStudentReportsRepository.findByStudentID(studentID)).thenReturn(gradStudentReportsList);
        when(gradReportTypesRepository.findById(gradReportTypes.getCode())).thenReturn(Optional.of(gradReportTypesEntity));
        when(documentStatusCodeRepository.findById(documentStatusCode.getCode())).thenReturn(Optional.of(documentStatusCodeEntity));

        var result = commonService.getAllStudentReportList(studentID);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getGradReportTypeCode()).isEqualTo(gradReportTypes.getCode());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getGradReportTypeCode()).isEqualTo(gradReportTypes.getCode());
    }
    
    @Test
    public void testGetAllStudentAchievement() {
    	UUID studentID = new UUID(1, 1);
    	
    	final GradReportTypes gradReportTypes = new GradReportTypes();
        gradReportTypes.setCode("SC");
        gradReportTypes.setDescription("School Completion Certificate");
        
        final DocumentStatusCode documentStatusCode = new DocumentStatusCode();
        documentStatusCode.setCode("COMPL");
        documentStatusCode.setDescription("School Completion Certificate");
        
        final GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("SC");
        gradCertificateType.setDescription("School Completion Certificate");
        
    	final List<GradStudentReportsEntity> gradStudentReportsList = new ArrayList<>();
        final GradStudentReportsEntity studentReport1 = new GradStudentReportsEntity();
        studentReport1.setId(UUID.randomUUID());
        studentReport1.setStudentID(studentID);
        studentReport1.setGradReportTypeCode(gradReportTypes.getCode());
        studentReport1.setDocumentStatusCode("COMP");
        gradStudentReportsList.add(studentReport1);
        
        final List<GradStudentCertificatesEntity> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentCertificatesEntity studentCertificate1 = new GradStudentCertificatesEntity();
        studentCertificate1.setId(UUID.randomUUID());        
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setGradCertificateTypeCode(gradCertificateType.getCode());
        studentCertificate1.setDocumentStatusCode("COMP");
        gradStudentCertificatesList.add(studentCertificate1);  
       
        
    	Mockito.when(gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH")).thenReturn(gradStudentReportsList);
    	Mockito.when(gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH")).thenReturn(gradStudentCertificatesList);
    	commonService.getAllStudentAchievement(studentID);
    }

    @Test
    public void testSaveGradTranscripts_thenReturnCreateSuccess() {
        // ID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode="ARCH";
        final GradStudentTranscripts gradStudentReport = new GradStudentTranscripts();
        gradStudentReport.setTranscriptTypeCode(reportTypeCode);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setTranscript("TEST Report Body");

        final GradStudentTranscriptsEntity gradStudentReportEntity = new GradStudentTranscriptsEntity();
        gradStudentReportEntity.setTranscriptTypeCode(reportTypeCode);

        gradStudentReportEntity.setStudentID(studentID);
        gradStudentReportEntity.setTranscript("TEST Report Body");

        final Optional<GradStudentTranscriptsEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optionalEmpty);
        when(this.gradStudentTranscriptsRepository.save(gradStudentReportEntity)).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradTranscripts(gradStudentReport,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getTranscriptTypeCode()).isEqualTo(gradStudentReport.getTranscriptTypeCode());
    }

    @Test
    public void testSaveGradTranscriptWithExistingOne_thenReturnUpdateSuccess() {
        // ID
        final UUID reportID = UUID.randomUUID();
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode = "COMPL";
        final GradStudentTranscripts gradStudentTranscripts = new GradStudentTranscripts();
        gradStudentTranscripts.setId(reportID);
        gradStudentTranscripts.setTranscriptTypeCode(reportTypeCode);
        gradStudentTranscripts.setStudentID(studentID);
        gradStudentTranscripts.setTranscript("TEST Report Body");

        final GradStudentTranscriptsEntity gradStudentTranscriptsEntity = new GradStudentTranscriptsEntity();
        gradStudentTranscriptsEntity.setId(reportID);
        gradStudentTranscriptsEntity.setTranscriptTypeCode(reportTypeCode);
        gradStudentTranscriptsEntity.setStudentID(studentID);
        gradStudentTranscriptsEntity.setTranscript("TEST Report Body");

        final Optional<GradStudentTranscriptsEntity> optional = Optional.of(gradStudentTranscriptsEntity);

        when(this.gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optional);
        when(this.gradStudentTranscriptsRepository.save(gradStudentTranscriptsEntity)).thenReturn(gradStudentTranscriptsEntity);

        var result = commonService.saveGradTranscripts(gradStudentTranscripts,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getTranscriptTypeCode()).isEqualTo(gradStudentTranscripts.getTranscriptTypeCode());
    }

    @Test
    public void testGetAllStudentTranscriptList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String pen = "123456789";
        // Certificate Type
        final TranscriptTypes gradCertificateType = new TranscriptTypes();
        gradCertificateType.setCode("SC");
        gradCertificateType.setDescription("School Completion Certificate");

        final DocumentStatusCodeEntity documentStatusCodeEntity = new DocumentStatusCodeEntity();
        documentStatusCodeEntity.setCode("COMPL");
        documentStatusCodeEntity.setDescription("School Completion Certificate");

        final DocumentStatusCode documentStatusCode = new DocumentStatusCode();
        documentStatusCode.setCode("COMPL");
        documentStatusCode.setDescription("School Completion Certificate");

        // Student Certificate Types
        final List<GradStudentTranscriptsEntity> gradStudentCertificatesList = new ArrayList<>();
        final GradStudentTranscriptsEntity studentCertificate1 = new GradStudentTranscriptsEntity();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setTranscriptTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate1);

        final GradStudentTranscriptsEntity studentCertificate2 = new GradStudentTranscriptsEntity();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setTranscriptTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate2);
        final TranscriptTypesEntity gradCertificateTypesEntity = new TranscriptTypesEntity();
        gradCertificateTypesEntity.setCode("SC");
        gradCertificateTypesEntity.setDescription("School Completion Certificate");

        when(gradStudentTranscriptsRepository.findByStudentID(studentID)).thenReturn(gradStudentCertificatesList);
        when(transcriptTypesRepository.findById(gradCertificateType.getCode())).thenReturn(Optional.of(gradCertificateTypesEntity));
        when(documentStatusCodeRepository.findById(documentStatusCode.getCode())).thenReturn(Optional.of(documentStatusCodeEntity));
        var result = commonService.getAllStudentTranscriptList(studentID);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getTranscriptTypeCode()).isEqualTo(gradCertificateType.getCode());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getTranscriptTypeCode()).isEqualTo(gradCertificateType.getCode());
    }

    @Test
    public void testGetStudentTranscriptByType() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        // Certificate Type
        final TranscriptTypes transcriptTypes = new TranscriptTypes();
        transcriptTypes.setCode("TEST");
        transcriptTypes.setDescription("Test Code Name");

        // Student Certificate Types
        final GradStudentTranscriptsEntity studentTranscript = new GradStudentTranscriptsEntity();
        studentTranscript.setId(UUID.randomUUID());
        studentTranscript.setStudentID(studentID);
        studentTranscript.setTranscript("TEST Certificate Body");
        studentTranscript.setDocumentStatusCode("COMPL");
        studentTranscript.setTranscriptTypeCode(transcriptTypes.getCode());

        final DocumentStatusCode documentStatus = new DocumentStatusCode();
        documentStatus.setCode("COMPL");
        documentStatus.setDescription("Test Code Name");

        when(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID, transcriptTypes.getCode(),documentStatus.getCode())).thenReturn(Optional.of(studentTranscript));
        var result = commonService.getStudentTranscriptByType(studentID, transcriptTypes.getCode(),documentStatus.getCode());
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).isEqualTo("[inline; filename=student_TEST_transcript.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetAllStudentTranscriptDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution credentialDistribution = new StudentCredentialDistribution(UUID.randomUUID(),"BC1996-IND",studentID,"YED4");
        list.add(credentialDistribution);


        when(gradStudentTranscriptsRepository.findByDocumentStatusCodeAndDistributionDate("COMPL")).thenReturn(list);
        var result = commonService.getAllStudentTranscriptDistributionList();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);

    }

    @Test
    public void testGetAllStudentCertificateDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution credentialDistribution = new StudentCredentialDistribution(UUID.randomUUID(),"E",studentID,"YED2");
        list.add(credentialDistribution);


        when(gradStudentCertificatesRepository.findByDocumentStatusCodeAndDistributionDate("COMPL")).thenReturn(list);
        var result = commonService.getAllStudentCertificateDistributionList();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);

    }
}
