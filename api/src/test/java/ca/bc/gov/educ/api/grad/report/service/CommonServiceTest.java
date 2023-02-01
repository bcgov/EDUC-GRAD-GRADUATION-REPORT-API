package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    @MockBean SchoolReportsRepository schoolReportsRepository;
    @MockBean WebClient webClient;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock WebClient.ResponseSpec responseMock;
    @Mock WebClient.RequestBodySpec requestBodyMock;
    @Mock WebClient.RequestBodyUriSpec requestBodyUriMock;

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
        gradStudentReportEntity.setReportUpdateDate(new Date());

        final Optional<GradStudentReportsEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optionalEmpty);
        when(this.gradStudentReportsRepository.save(any(GradStudentReportsEntity.class))).thenReturn(gradStudentReportEntity);

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
        gradStudentReportEntity.setReportUpdateDate(new Date());

        final Optional<GradStudentReportsEntity> optional = Optional.of(gradStudentReportEntity);

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,"ARCH")).thenReturn(optional);
        when(this.gradStudentReportsRepository.save(any(GradStudentReportsEntity.class))).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradReports(gradStudentReport,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getGradReportTypeCode()).isEqualTo(gradStudentReport.getGradReportTypeCode());
    }

    @Test
    public void testSaveGradReportsWithExistingOne_whenReportClobIsChanged_thenReturnUpdateSuccess() {
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
        gradStudentReportEntity.setReport("TEST Report Body 123");
        gradStudentReportEntity.setReportUpdateDate(new Date());

        final Optional<GradStudentReportsEntity> optional = Optional.of(gradStudentReportEntity);

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,"ARCH")).thenReturn(optional);
        when(this.gradStudentReportsRepository.save(any(GradStudentReportsEntity.class))).thenReturn(gradStudentReportEntity);

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
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_TEST_report.pdf]");
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
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_TEST_certificate.pdf]");
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
    	int res = commonService.getAllStudentAchievement(studentID);
        assertThat(res).isEqualTo(1);
    }

    @Test
    public void testSaveGradTranscripts_thenReturnCreateSuccess() {
        // ID
        final UUID studentID = UUID.randomUUID();
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode="ARCH";
        final GradStudentTranscripts gradStudentTranscripts = new GradStudentTranscripts();
        gradStudentTranscripts.setTranscriptTypeCode(reportTypeCode);
        gradStudentTranscripts.setStudentID(studentID);
        gradStudentTranscripts.setTranscript("TEST Report Body");

        final GradStudentTranscriptsEntity gradStudentTranscriptsEntity = new GradStudentTranscriptsEntity();
        gradStudentTranscriptsEntity.setTranscriptTypeCode(reportTypeCode);

        gradStudentTranscriptsEntity.setStudentID(studentID);
        gradStudentTranscriptsEntity.setTranscript("TEST Report Body");
        gradStudentTranscriptsEntity.setTranscriptUpdateDate(new Date());

        final Optional<GradStudentTranscriptsEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,documentStatusCode)).thenReturn(optionalEmpty);
        when(this.gradStudentTranscriptsRepository.save(any(GradStudentTranscriptsEntity.class))).thenReturn(gradStudentTranscriptsEntity);

        var result = commonService.saveGradTranscripts(gradStudentTranscripts,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getTranscriptTypeCode()).isEqualTo(gradStudentTranscripts.getTranscriptTypeCode());
    }

    @Test
    public void testSaveGradTranscriptWithExistingOne_thenReturnUpdateSuccess() {
        // ID
        final UUID reportID = UUID.randomUUID();
        final UUID studentID = UUID.randomUUID();
        final String reportTypeCode = "TEST";
        boolean isGraduated = false;
        final String documentStatusCode = "COMPL";
        final GradStudentTranscripts gradStudentTranscripts = new GradStudentTranscripts();
        gradStudentTranscripts.setId(reportID);
        gradStudentTranscripts.setTranscriptTypeCode(reportTypeCode);
        gradStudentTranscripts.setStudentID(studentID);
        gradStudentTranscripts.setTranscript("TEST Report Body 123");

        final GradStudentTranscriptsEntity gradStudentTranscriptsEntity = new GradStudentTranscriptsEntity();
        gradStudentTranscriptsEntity.setId(reportID);
        gradStudentTranscriptsEntity.setTranscriptTypeCode(reportTypeCode);
        gradStudentTranscriptsEntity.setStudentID(studentID);
        gradStudentTranscriptsEntity.setTranscript("TEST Report Body 456");
        gradStudentTranscriptsEntity.setTranscriptUpdateDate(new Date());

        final Optional<GradStudentTranscriptsEntity> optional = Optional.of(gradStudentTranscriptsEntity);

        when(this.gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(studentID, reportTypeCode,"ARCH")).thenReturn(optional);
        when(this.gradStudentTranscriptsRepository.save(any(GradStudentTranscriptsEntity.class))).thenReturn(gradStudentTranscriptsEntity);

        var result = commonService.saveGradTranscripts(gradStudentTranscripts,isGraduated);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getTranscriptTypeCode()).isEqualTo(gradStudentTranscripts.getTranscriptTypeCode());
    }

    @Test
    public void testGetAllStudentTranscriptList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
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
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_TEST_transcript.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentTranscriptByStudentID() {
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

        when(gradStudentTranscriptsRepository.findByStudentID(studentID)).thenReturn(List.of(studentTranscript));
        var result = commonService.getStudentTranscriptByStudentID(studentID);
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_TRAN_transcript.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetAllStudentTranscriptDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<StudentCredentialDistribution> certificates = new ArrayList<>();
        final StudentCredentialDistribution certificateCredentialDistribution = new StudentCredentialDistribution(UUID.randomUUID(),"E",studentID,"YED2","COMPL", new Date());
        certificates.add(certificateCredentialDistribution);

        when(gradStudentCertificatesRepository.findByDocumentStatusCode("COMPL")).thenReturn(certificates);

        // Student Certificate Types
        final List<StudentCredentialDistribution> transcripts = new ArrayList<>();
        final StudentCredentialDistribution transcriptCredentialDistribution = new StudentCredentialDistribution(UUID.randomUUID(),"BC1996-IND",studentID,"YED4","COMPL", new Date());
        transcripts.add(transcriptCredentialDistribution);

        when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(List.of(studentID))).thenReturn(transcripts);
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
        final StudentCredentialDistribution credentialDistribution = new StudentCredentialDistribution(UUID.randomUUID(),"E",studentID,"YED2","COMPL", new Date());
        list.add(credentialDistribution);


        when(gradStudentCertificatesRepository.findByDocumentStatusCodeAndNullDistributionDate("COMPL")).thenReturn(list);
        var result = commonService.getAllStudentCertificateDistributionList();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);

    }

    @Test
    public void testGetAllStudentTranscriptYearlyDistributionList() {
        List<StudentCredentialDistribution> scdList = new ArrayList<>();
        StudentCredentialDistribution scd = new StudentCredentialDistribution(new UUID(2,2),"E",new UUID(1,1),"YED4","COMPL", new Date());
        scdList.add(scd);

        List<StudentCredentialDistribution> scdSubList = new ArrayList<>();
        StudentCredentialDistribution scdSub = new StudentCredentialDistribution(new UUID(4,4),"E",new UUID(5,5),"YED4","COMPL", new Date());
        scdSubList.add(scdSub);

        ParameterizedTypeReference<List<UUID>> studentidres = new ParameterizedTypeReference<>() {
        };

        List<UUID> studentList = new ArrayList<>();
        studentList.add(new UUID(3,3));

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(constants.getStudentsForYearlyDistribution())).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(studentidres)).thenReturn(Mono.just(studentList));


        when(gradStudentTranscriptsRepository.findByDocumentStatusCodeAndDistributionDateYearly("COMPL")).thenReturn(scdList);
        when(gradStudentTranscriptsRepository.findByReportsForYearly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> res = commonService.getAllStudentTranscriptYearlyDistributionList(null);
        assertThat(res.size()).isEqualTo(2);



    }

    @Test
    public void testUpdateStudentCredential() {
        UUID studentId = new UUID(1,1);
        String credentialTypeCode = "E";
        String activityCode="USERDISTOC";
        String paperType="YED4";
        GradStudentTranscriptsEntity ent = new GradStudentTranscriptsEntity();
        ent.setStudentID(studentId);
        ent.setTranscript("dfd");
        ent.setId(new UUID(1,2));
        ent.setTranscriptTypeCode("E");
        ent.setDocumentStatusCode("COMPL");

        when(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentId,credentialTypeCode,"COMPL")).thenReturn(Optional.of(ent));
        boolean res = commonService.updateStudentCredential(studentId,credentialTypeCode,paperType,"COMPL", activityCode);
        assertThat(res).isTrue();
    }

    @Test
    public void testUpdateStudentCredential_CERT() {
        UUID studentId = new UUID(1,1);
        String credentialTypeCode = "E";
        String activityCode="USERDISTOC";
        String paperType="YED2";
        GradStudentCertificatesEntity ent = new GradStudentCertificatesEntity();
        ent.setStudentID(studentId);
        ent.setCertificate("dfd");
        ent.setId(new UUID(1,2));
        ent.setGradCertificateTypeCode("E");
        ent.setDocumentStatusCode("COMPL");

        when(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentId,credentialTypeCode,"COMPL")).thenReturn(Optional.of(ent));
        boolean res = commonService.updateStudentCredential(studentId,credentialTypeCode,paperType,"COMPL", activityCode);
        assertThat(res).isTrue();
    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OC() {

        String credentialType = "OC";
        GraduationStudentRecordSearchResult res = new GraduationStudentRecordSearchResult();

        List<UUID> studList= new ArrayList<>();
        GraduationStudentRecord rec = new GraduationStudentRecord();
        rec.setLegalFirstName("asda");
        rec.setStudentID(new UUID(1,1));
        studList.add(rec.getStudentID());
        res.setStudentIDs(studList);

        List<StudentCredentialDistribution> scdSubList = new ArrayList<>();
        StudentCredentialDistribution scdSub = new StudentCredentialDistribution(new UUID(4,4),"E",new UUID(5,5),"YED4","COMPL", new Date());
        scdSubList.add(scdSub);

        List<UUID> studentList = new ArrayList<>();
        studentList.add(new UUID(1,1));

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,new StudentSearchRequest(),null);
        assertThat(result).isNotEmpty();

    }

    @Test
    public void testGetStudentCredentialsForSpecialGradRun() {

        List<UUID> studentList = new ArrayList<>();

        GraduationStudentRecordSearchResult res = new GraduationStudentRecordSearchResult();

        StudentSearchRequest req = new StudentSearchRequest();
        List<String> penList = new ArrayList<>();
        penList.add("13123111");
        req.setPens(penList);

        GraduationStudentRecord rec = new GraduationStudentRecord();
        rec.setLegalFirstName("asda");
        rec.setStudentID(new UUID(1,1));
        studentList.add(rec.getStudentID());
        res.setStudentIDs(studentList);

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        List<UUID> result = commonService.getStudentsForSpecialGradRun(req,"accessToken");
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OT() {

        String credentialType = "OT";
        GraduationStudentRecordSearchResult res = new GraduationStudentRecordSearchResult();

        List<UUID> studList= new ArrayList<>();
        GraduationStudentRecord rec = new GraduationStudentRecord();
        rec.setLegalFirstName("asda");
        rec.setStudentID(new UUID(1,1));
        studList.add(rec.getStudentID());
        res.setStudentIDs(studList);

        List<StudentCredentialDistribution> scdSubList = new ArrayList<>();
        StudentCredentialDistribution scdSub = new StudentCredentialDistribution(new UUID(4,4),"E",new UUID(5,5),"YED4","COMPL", new Date());
        scdSubList.add(scdSub);

        List<UUID> studentList = new ArrayList<>();
        studentList.add(new UUID(1,1));

        StudentSearchRequest req = new StudentSearchRequest();
        List<String> penList = new ArrayList<>();
        penList.add("13123111");
        req.setPens(penList);

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,null);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OT_Prgm() {

        String credentialType = "OT";
        GraduationStudentRecordSearchResult res = new GraduationStudentRecordSearchResult();

        List<UUID> studList= new ArrayList<>();
        GraduationStudentRecord rec = new GraduationStudentRecord();
        rec.setLegalFirstName("asda");
        rec.setStudentID(new UUID(1,1));
        studList.add(rec.getStudentID());
        res.setStudentIDs(studList);

        List<StudentCredentialDistribution> scdSubList = new ArrayList<>();
        StudentCredentialDistribution scdSub = new StudentCredentialDistribution(new UUID(4,4),"E",new UUID(5,5),"YED4","COMPL", new Date());
        scdSubList.add(scdSub);

        List<UUID> studentList = new ArrayList<>();
        studentList.add(new UUID(1,1));

        StudentSearchRequest req = new StudentSearchRequest();
        List<String> pgList = new ArrayList<>();
        pgList.add("2018-EN");
        req.setPrograms(pgList);
        req.setPens(new ArrayList<>());

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,null);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testArchiveAllStudentAchievement() {
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
        int res = commonService.archiveAllStudentAchievements(studentID);
        assertThat(res).isEqualTo(1);
    }

    @Test
    public void testSaveSchoolReports_thenReturnCreateSuccess() {
        // ID
        final String schoolOfRecord = "123123112";
        final String reportTypeCode = "NONGRADPRJ";
        final SchoolReports schoolReports = new SchoolReports();
        schoolReports.setReportTypeCode(reportTypeCode);
        schoolReports.setSchoolOfRecord(schoolOfRecord);
        schoolReports.setReport("TEST Report Body");

        final SchoolReportsEntity schoolReportsEntity = new SchoolReportsEntity();
        schoolReportsEntity.setReportTypeCode(reportTypeCode);
        schoolReportsEntity.setSchoolOfRecord(schoolOfRecord);
        schoolReportsEntity.setReport("TEST Report Body");

        final Optional<SchoolReportsEntity> optionalEmpty = Optional.empty();

        when(this.schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(schoolOfRecord, reportTypeCode)).thenReturn(optionalEmpty);
        when(this.schoolReportsRepository.save(schoolReportsEntity)).thenReturn(schoolReportsEntity);

        var result = commonService.saveSchoolReports(schoolReports);

        assertThat(result).isNotNull();
        assertThat(result.getSchoolOfRecord()).isEqualTo(schoolOfRecord);
        assertThat(result.getReportTypeCode()).isEqualTo(schoolReports.getReportTypeCode());
    }

    @Test
    public void testSaveSchoolReportsWithExistingOne_thenReturnUpdateSuccess() {

        final String schoolOfRecord = "123123112";
        final String reportTypeCode = "NONGRADPRJ";

        final SchoolReports schoolReports = new SchoolReports();
        schoolReports.setReportTypeCode(reportTypeCode);
        schoolReports.setSchoolOfRecord(schoolOfRecord);
        schoolReports.setReport("TEST Report Body");

        final SchoolReportsEntity schoolReportsEntity = new SchoolReportsEntity();
        schoolReportsEntity.setReportTypeCode(reportTypeCode);
        schoolReportsEntity.setSchoolOfRecord(schoolOfRecord);
        schoolReportsEntity.setReport("TEST Report Body");

        final Optional<SchoolReportsEntity> optional = Optional.of(schoolReportsEntity);

        when(this.schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(schoolOfRecord, reportTypeCode)).thenReturn(optional);
        when(this.schoolReportsRepository.save(schoolReportsEntity)).thenReturn(schoolReportsEntity);

        var result = commonService.saveSchoolReports(schoolReports);

        assertThat(result).isNotNull();
        assertThat(result.getSchoolOfRecord()).isEqualTo(schoolOfRecord);
        assertThat(result.getReportTypeCode()).isEqualTo(schoolReports.getReportTypeCode());
    }

    @Test
    public void testGetAllSchoolReportList() {
        final String mincode = "123456*";
        final String mincode2 = "12345631231";
        // Certificate Type
        final GradReportTypes gradReportTypes = new GradReportTypes();
        gradReportTypes.setCode("NONGRADPRJ");
        gradReportTypes.setDescription("non grad projected");


        // Student Certificate Types
        final List<SchoolReportsEntity> schoolReportsEntityList = new ArrayList<>();
        final SchoolReportsEntity schoolReports = new SchoolReportsEntity();
        schoolReports.setId(UUID.randomUUID());
        schoolReports.setSchoolOfRecord(mincode2);
        schoolReports.setReportTypeCode(gradReportTypes.getCode());
        schoolReportsEntityList.add(schoolReports);

        final SchoolReportsEntity schoolReports2 = new SchoolReportsEntity();
        schoolReports2.setId(UUID.randomUUID());
        schoolReports2.setSchoolOfRecord(mincode2);
        schoolReports2.setReportTypeCode(gradReportTypes.getCode());

        schoolReportsEntityList.add(schoolReports2);

        School schObj = new School();
        schObj.setMinCode(mincode2);
        schObj.setSchoolName("aadada");

        final GradReportTypesEntity gradReportTypesEntity = new GradReportTypesEntity();
        gradReportTypesEntity.setCode("NONGRADPRJ");
        gradReportTypesEntity.setDescription("non grad projected");

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getSchoolByMincodeUrl(),mincode2))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(School.class)).thenReturn(Mono.just(schObj));

        when(schoolReportsRepository.findBySchoolOfRecordContains("123456")).thenReturn(schoolReportsEntityList);
        when(gradReportTypesRepository.findById(gradReportTypes.getCode())).thenReturn(Optional.of(gradReportTypesEntity));

        var result = commonService.getAllSchoolReportList(mincode,"accessToken");

        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getSchoolOfRecord()).isEqualTo(mincode2);
        assertThat(result.get(0).getReportTypeCode()).isEqualTo(gradReportTypes.getCode());
        assertThat(result.get(1).getSchoolOfRecord()).isEqualTo(mincode2);
        assertThat(result.get(1).getReportTypeCode()).isEqualTo(gradReportTypes.getCode());
    }

    @Test
    public void testGetAllSchoolReportList_withoutwildcard() {
        final String mincode = "12345631231";
        final String mincode2 = "12345631231";
        // Certificate Type
        final GradReportTypes gradReportTypes = new GradReportTypes();
        gradReportTypes.setCode("NONGRADPRJ");
        gradReportTypes.setDescription("non grad projected");


        // Student Certificate Types
        final List<SchoolReportsEntity> schoolReportsEntityList = new ArrayList<>();
        final SchoolReportsEntity schoolReports = new SchoolReportsEntity();
        schoolReports.setId(UUID.randomUUID());
        schoolReports.setSchoolOfRecord(mincode2);
        schoolReports.setReportTypeCode(gradReportTypes.getCode());
        schoolReportsEntityList.add(schoolReports);

        final SchoolReportsEntity schoolReports2 = new SchoolReportsEntity();
        schoolReports2.setId(UUID.randomUUID());
        schoolReports2.setSchoolOfRecord(mincode2);
        schoolReports2.setReportTypeCode(gradReportTypes.getCode());

        schoolReportsEntityList.add(schoolReports2);

        School schObj = new School();
        schObj.setMinCode(mincode2);
        schObj.setSchoolName("aadada");

        final GradReportTypesEntity gradReportTypesEntity = new GradReportTypesEntity();
        gradReportTypesEntity.setCode("NONGRADPRJ");
        gradReportTypesEntity.setDescription("non grad projected");

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getSchoolByMincodeUrl(),mincode2))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(School.class)).thenReturn(Mono.just(schObj));

        when(schoolReportsRepository.findBySchoolOfRecord("12345631231")).thenReturn(schoolReportsEntityList);
        when(gradReportTypesRepository.findById(gradReportTypes.getCode())).thenReturn(Optional.of(gradReportTypesEntity));

        var result = commonService.getAllSchoolReportList(mincode,"accessToken");

        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getSchoolOfRecord()).isEqualTo(mincode2);
        assertThat(result.get(0).getReportTypeCode()).isEqualTo(gradReportTypes.getCode());
        assertThat(result.get(1).getSchoolOfRecord()).isEqualTo(mincode2);
        assertThat(result.get(1).getReportTypeCode()).isEqualTo(gradReportTypes.getCode());
    }

    @Test
    public void testGetSchoolReportByType() {

        final String mincode = "123456789";
        final String reportTypeCode = "TEST";
        int currentYear = LocalDate.now().getYear();

        final SchoolReportsEntity schoolReports = new SchoolReportsEntity();
        schoolReports.setId(new UUID(1,1));
        schoolReports.setReportTypeCode(reportTypeCode);
        schoolReports.setSchoolOfRecord(mincode);
        schoolReports.setReport("TEST Report Body");

        when(schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(mincode, reportTypeCode)).thenReturn(Optional.of(schoolReports));
        var result = commonService.getSchoolReportByType(mincode, reportTypeCode);

        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=123456789_"+currentYear+"00_"+reportTypeCode+".pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testUpdateSchoolReport() {
        String mincode = "123123123";
        String reportTypeCode = "NONGRADPRJ";
        SchoolReportsEntity ent = new SchoolReportsEntity();
        ent.setSchoolOfRecord(mincode);
        ent.setReport("dfd");
        ent.setId(new UUID(1,2));
        ent.setReportTypeCode(reportTypeCode);

        when(schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(mincode,reportTypeCode)).thenReturn(Optional.of(ent));
        boolean res = commonService.updateSchoolReports(mincode,reportTypeCode);
        assertThat(res).isTrue();
    }

    @Test
    public void testGetAllSchoolStudentCertificatePostingList() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        // Student Certificate Types
        final List<SchoolStudentCredentialDistribution> list = new ArrayList<>();
        final SchoolStudentCredentialDistribution credentialDistribution = new SchoolStudentCredentialDistribution(UUID.randomUUID(),"E",studentID,"IP");
        list.add(credentialDistribution);

        final List<SchoolStudentCredentialDistribution> list2 = new ArrayList<>();
        final SchoolStudentCredentialDistribution credentialDistribution2 = new SchoolStudentCredentialDistribution(UUID.randomUUID(),"ACHV",studentID,"COMPL");
        list2.add(credentialDistribution2);

        when(gradStudentReportsRepository.findByReportUpdateDate()).thenReturn(list);
        when(gradStudentTranscriptsRepository.findByTranscriptUpdateDate()).thenReturn(list2);
        var result = commonService.getAllStudentTranscriptAndReportsPosting();

        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);

    }

    @Test
    public void testUpdateStudentCredentialPosting() {
        UUID studentId = new UUID(1,1);
        String credentialTypeCode = "E";
        GradStudentTranscriptsEntity ent = new GradStudentTranscriptsEntity();
        ent.setStudentID(studentId);
        ent.setTranscript("dfd");
        ent.setId(new UUID(1,2));
        ent.setTranscriptTypeCode("E");

        when(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCode(studentId,credentialTypeCode)).thenReturn(Optional.of(ent));
        boolean res = commonService.updateStudentCredentialPosting(studentId,credentialTypeCode);
        assertThat(res).isTrue();
    }

    @Test
    public void testUpdateStudentCredentialPosting_CERT() {
        UUID studentId = new UUID(1,1);
        String credentialTypeCode = "ACHV";
        GradStudentReportsEntity ent = new GradStudentReportsEntity();
        ent.setStudentID(studentId);
        ent.setReport("dfd");
        ent.setId(new UUID(1,2));
        ent.setGradReportTypeCode("ACHV");

        when(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentId,credentialTypeCode)).thenReturn(Optional.of(ent));
        boolean res = commonService.updateStudentCredentialPosting(studentId,credentialTypeCode);
        assertThat(res).isTrue();
    }

    @Test
    public void testGetStudentCredentialByType_TRAN() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String type = "TRAN";

        // Student Certificate Types
        final GradStudentTranscriptsEntity studentTranscript = new GradStudentTranscriptsEntity();
        studentTranscript.setId(UUID.randomUUID());
        studentTranscript.setStudentID(studentID);
        studentTranscript.setTranscript("TEST Certificate Body");
        studentTranscript.setDocumentStatusCode("COMPL");
        studentTranscript.setTranscriptTypeCode("BC1996-PUB");


        when(gradStudentTranscriptsRepository.findByStudentID(studentID)).thenReturn(List.of(studentTranscript));
        var result = commonService.getStudentCredentialByType(studentID, type);
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_TRAN_transcript.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentCredentialByType_ACHV() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String type = "ACHV";

        // Student Certificate Types
        final GradStudentReportsEntity studentReport = new GradStudentReportsEntity();
        studentReport.setId(UUID.randomUUID());
        studentReport.setStudentID(studentID);
        studentReport.setReport("TEST Certificate Body");
        studentReport.setDocumentStatusCode("COMPL");
        studentReport.setGradReportTypeCode("ACHV");


        when(gradStudentReportsRepository.findByStudentID(studentID)).thenReturn(List.of(studentReport));
        var result = commonService.getStudentCredentialByType(studentID, type);
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).hasToString("[inline; filename=student_ACHV_achievement.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentCredentialByType_GRAD() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String type = "GRAD";
        var result = commonService.getStudentCredentialByType(studentID, type);
        assertThat(result).isNull();
    }

    @Test
    public void testCheckStudentCertificateExistsForSCCP_without_SCCP_Certificate() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String type = "GRAD";

        when(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeIn(eq(studentID), any())).thenReturn(new ArrayList<>());

        var result = commonService.checkStudentCertificateExistsForSCCP(studentID);
        assertThat(result).isFalse();
    }

    @Test
    public void testCheckStudentCertificateExistsForSCCP_with_SCCP_Certificate() {
        // UUID
        final UUID studentID = UUID.randomUUID();

        GradStudentCertificatesEntity gradStudentCertificates = new GradStudentCertificatesEntity();
        gradStudentCertificates.setId(UUID.randomUUID());
        gradStudentCertificates.setStudentID(studentID);
        gradStudentCertificates.setGradCertificateTypeCode("SC");

        when(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeIn(eq(studentID), any())).thenReturn(Arrays.asList(gradStudentCertificates));

        var result = commonService.checkStudentCertificateExistsForSCCP(studentID);
        assertThat(result).isTrue();
    }
}
