package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecord;
import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CommonServiceTest {

    @Autowired EducGradReportApiConstants constants;
    @Autowired
    CommonService commonService;
    @MockBean
    GradStudentCertificatesRepository gradStudentCertificatesRepository;
    @MockBean GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    @MockBean
    SchoolReportRepository schoolReportsRepository;
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

        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,new StudentSearchRequest(),false,null);
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

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

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun("OC",searchRequest,true,"accessToken");
        assertThat(result).isNotEmpty();

        searchRequest.setActivityCode("USERDIST");

        result = commonService.getStudentCredentialsForUserRequestDisRun("OT",searchRequest,false,"accessToken");
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);
        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,new StudentSearchRequest(),true,null);
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,false,null);
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,true,null);
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,false,null);
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

        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(constants.getGradStudentApiStudentForSpcGradListUrl())).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GraduationStudentRecordSearchResult.class)).thenReturn(Mono.just(res));

        Mockito.when(gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(studentList)).thenReturn(scdSubList);

        List<StudentCredentialDistribution> result = commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,true,null);
        assertThat(result).hasSize(1);

    }
}
