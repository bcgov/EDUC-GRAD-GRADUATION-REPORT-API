package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import ca.bc.gov.educ.api.grad.report.repository.GradCertificateTypesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradReportTypesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentCertificatesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentReportsRepository;
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

    @Autowired
    EducGradReportApiConstants constants;

    @Autowired
    private CommonService commonService;

    @MockBean
    private GradStudentCertificatesRepository gradStudentCertificatesRepository;

    @MockBean
    private GradStudentReportsRepository gradStudentReportsRepository;
    
    @MockBean
	private GradCertificateTypesRepository gradCertificateTypesRepository;

	@MockBean
	private GradReportTypesRepository gradReportTypesRepository;

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

        when(this.gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCode(studentID, gradCertificateType.getCode())).thenReturn(optionalEmpty);
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
        studentCertificateEntity.setGradCertificateTypeCode(gradCertificateType.getCode());

        final Optional<GradStudentCertificatesEntity> optional = Optional.of(studentCertificateEntity);

        when(this.gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCode(studentID, gradCertificateType.getCode())).thenReturn(optional);
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

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID, reportTypeCode)).thenReturn(optionalEmpty);
        when(this.gradStudentReportsRepository.save(gradStudentReportEntity)).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradReports(gradStudentReport);

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

        when(this.gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID, reportTypeCode)).thenReturn(optional);
        when(this.gradStudentReportsRepository.save(gradStudentReportEntity)).thenReturn(gradStudentReportEntity);

        var result = commonService.saveGradReports(gradStudentReport);

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

        final GradStudentReportsEntity gradStudentReport = new GradStudentReportsEntity();
        gradStudentReport.setId(reportID);
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        when(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID, reportTypeCode)).thenReturn(Optional.of(gradStudentReport));
        var result = commonService.getStudentReportByType(studentID, reportTypeCode);
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
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        when(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCode(studentID, gradCertificateType.getCode())).thenReturn(Optional.of(studentCertificate));
        var result = commonService.getStudentCertificateByType(studentID, gradCertificateType.getCode());
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

        // Student Certificate Types
        final List<GradStudentReportsEntity> gradStudentReportsList = new ArrayList<>();
        final GradStudentReportsEntity studentReport1 = new GradStudentReportsEntity();
        studentReport1.setId(UUID.randomUUID());
        studentReport1.setPen(pen);
        studentReport1.setStudentID(studentID);
        studentReport1.setGradReportTypeCode(gradReportTypes.getCode());
        gradStudentReportsList.add(studentReport1);

        final GradStudentReportsEntity studentReport2 = new GradStudentReportsEntity();
        studentReport2.setId(UUID.randomUUID());
        studentReport2.setPen(pen);
        studentReport2.setStudentID(studentID);
        studentReport2.setGradReportTypeCode(gradReportTypes.getCode());
        gradStudentReportsList.add(studentReport2);
        
        final GradReportTypesEntity gradReportTypesEntity = new GradReportTypesEntity();
        gradReportTypesEntity.setCode("SC");
        gradReportTypesEntity.setDescription("School Completion Certificate");
        
        when(gradStudentReportsRepository.findByStudentID(studentID)).thenReturn(gradStudentReportsList);
        when(gradReportTypesRepository.findById(gradReportTypes.getCode())).thenReturn(Optional.of(gradReportTypesEntity));

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
    	Mockito.when(gradStudentReportsRepository.deleteByStudentID(studentID)).thenReturn(2L);
    	Mockito.when(gradStudentCertificatesRepository.deleteByStudentID(studentID)).thenReturn(2L);
    	commonService.getAllStudentAchievement(studentID);
    }
}
