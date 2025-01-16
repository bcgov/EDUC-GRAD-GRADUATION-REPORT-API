package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecord;
import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CommonServiceTest {

    @Autowired
    CommonService commonService;

    @MockBean
    GradStudentCertificatesRepository gradStudentCertificatesRepository;

    @MockBean
    GradStudentTranscriptsRepository gradStudentTranscriptsRepository;

    @MockBean
    RESTService restService;

    @MockBean
    GradStudentReportsRepository gradStudentReportsRepository;

    @MockBean
    SchoolReportRepository schoolReportRepository;


    @Before
    public void setUp() {
        openMocks(this);
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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,new StudentSearchRequest(),false);
        assertThat(result).isNotEmpty();

    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OC_SearchRequest() {

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        StudentSearchRequest searchRequest = new StudentSearchRequest();
        searchRequest.setPens(new ArrayList<>());
        searchRequest.getPens().add("12345678");

        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun("OC",searchRequest,true);
        assertThat(result).isNotEmpty();

        searchRequest.setActivityCode("USERDIST");

        result = commonService.getStudentCredentialsForUserRequestDisRun("OT",searchRequest,false);
        assertThat(result).isNotEmpty();

    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OC_with_Null_DistributionDate() {

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,new StudentSearchRequest(),true);
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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        List<UUID> result = commonService.getStudentsForSpecialGradRun(req);
        assertThat(result).hasSize(1);

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,false);
        assertThat(result).hasSize(1);

    }

    @Test
    public void testGetStudentCredentialsWithNullDistributionDateForUserRequestDisRun_OT() {

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,true);
        assertThat(result).hasSize(1);

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,false);
        assertThat(result).hasSize(1);

    }

    @Test
    public void testGetStudentCredentialsWithNullDistributionDateForUserRequestDisRun_OT_Prgm() {

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

        when(this.restService.post(any(), any(), any())).thenReturn(res);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,true);
        assertThat(result).hasSize(1);
    }

    @Test
    public void testCountByStudentGuidsAndReportType() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(gradStudentReportsRepository.countByStudentGuidsAndReportType(List.of(uuid), "reportType")).thenReturn(1);
        Integer count = commonService.countByStudentGuidsAndReportType(List.of(uuid), "reportType");
        assertThat(count).isNotNull().isEqualTo(1);

        Mockito.when(gradStudentReportsRepository.countByReportType("reportType")).thenReturn(1);
        count = commonService.countByStudentGuidsAndReportType(List.of(), "reportType");
        assertThat(count).isNotNull().isEqualTo(1);
    }

    @Test
    public void testCountBySchoolOfRecordsAndReportType() {
        UUID schoolId = UUID.randomUUID();
        Mockito.when(schoolReportRepository.countBySchoolOfRecordIdInAndReportTypeCode(List.of(schoolId), "reportType")).thenReturn(1);
        Integer count = commonService.countBySchoolOfRecordsAndReportType(List.of(schoolId), "reportType");
        assertThat(count).isNotNull().isEqualTo(1);

        Mockito.when(schoolReportRepository.countByReportTypeCode("reportType")).thenReturn(1);
        count = commonService.countBySchoolOfRecordsAndReportType(List.of(), "reportType");
        assertThat(count).isNotNull().isEqualTo(1);
    }
}
