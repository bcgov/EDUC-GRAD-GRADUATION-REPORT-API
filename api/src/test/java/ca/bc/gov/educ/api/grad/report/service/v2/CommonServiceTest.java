package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.ReportGradStudentData;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.YearEndReportRequest;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportEntityId;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportMonthlyEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportYearEndEntity;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static ca.bc.gov.educ.api.grad.report.constants.ReportingSchoolTypesEnum.SCHOOL_AT_GRAD;
import static ca.bc.gov.educ.api.grad.report.constants.ReportingSchoolTypesEnum.SCHOOL_OF_RECORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
    SchoolReportMonthlyRepository schoolReportMonthlyRepository;

    @MockBean
    SchoolReportYearEndRepository schoolReportYearEndRepository;

    @MockBean
    RESTService restService;

    @MockBean
    GradStudentReportsRepository gradStudentReportsRepository;

    @MockBean
    SchoolReportRepository schoolReportRepository;

    @MockBean
    SchoolReportLightRepository schoolReportLightRepository;

    @MockBean
    SchoolCacheService schoolCache;


    @Before
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testGetStudentCredentialsForUserRequestDisRun_OC() {

        String credentialType = "OC";
        GraduationStudentRecordSearchResult res = new GraduationStudentRecordSearchResult();

        List<UUID> studList= new ArrayList<>();
        studList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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

        studentList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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
        studList.add(new UUID(1,1));
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

    @Test
    public void testGetSchoolReportGradStudentData() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        SchoolReportMonthlyEntity schoolReportEntity = new SchoolReportMonthlyEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportMonthlyEntity schoolReportEntity2 = new SchoolReportMonthlyEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YRD4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportMonthlyRepository.findStudentForSchoolReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);
        List<ReportGradStudentData> result = commonService.getSchoolReportGradStudentData();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentData() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity), PageRequest.of(0, 1), 2));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity2), PageRequest.of(1, 1), 2));

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentDataWithFilteredSchools() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        UUID schoolId = UUID.randomUUID();
        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        student1.setSchoolAtGradId(schoolId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);

        YearEndReportRequest yearEndReportRequest = YearEndReportRequest.builder().schoolIds(List.of(schoolId)).build();
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData(yearEndReportRequest);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentDataWithFilteredSchoolCategoryCodes() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        UUID schoolId = UUID.randomUUID();
        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        School school = new School();
        school.setSchoolCategoryCode("INDEPEND");
        school.setDistrictId(UUID.randomUUID().toString());
        when(schoolCache.getSchool(schoolId)).thenReturn(school);

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        student1.setSchoolAtGradId(schoolId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);

        YearEndReportRequest yearEndReportRequest = YearEndReportRequest.builder().schoolCategoryCodes(List.of("INDEPEND")).build();
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData(yearEndReportRequest);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentDataWithFilteredSchoolCategoryCodesInDistrict() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        UUID schoolId = UUID.randomUUID();
        UUID districtId = UUID.randomUUID();
        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        School school = new School();
        school.setSchoolCategoryCode("INDEPEND");
        school.setDistrictId(districtId.toString());
        when(schoolCache.getSchool(schoolId)).thenReturn(school);

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        student1.setSchoolAtGradId(schoolId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);

        YearEndReportRequest yearEndReportRequest = YearEndReportRequest.builder().schoolCategoryCodes(List.of("INDEPEND")).districtIds(List.of(districtId)).build();
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData(yearEndReportRequest);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentDataWithFilteredSchoolCategoryCodesNotInDistrict() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        UUID schoolId = UUID.randomUUID();
        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));
        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        School school = new School();
        school.setSchoolCategoryCode("INDEPEND");
        school.setDistrictId(UUID.randomUUID().toString());
        when(schoolCache.getSchool(schoolId)).thenReturn(school);

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        student1.setSchoolAtGradId(schoolId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);

        YearEndReportRequest yearEndReportRequest = YearEndReportRequest.builder().schoolCategoryCodes(List.of("INDEPEND")).districtIds(List.of(UUID.randomUUID())).build();
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData(yearEndReportRequest);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetSchoolYearEndReportGradStudentDataWithFilteredDistricts() {
        UUID studentId = UUID.randomUUID();
        UUID studentId2 = UUID.randomUUID();
        UUID schoolId = UUID.randomUUID();
        UUID districtId = UUID.randomUUID();

        SchoolReportYearEndEntity schoolReportEntity = new SchoolReportYearEndEntity();
        schoolReportEntity.setSchoolReportEntityId(new SchoolReportEntityId(studentId, "EBDR", "E", SCHOOL_AT_GRAD.name()));
        SchoolReportYearEndEntity schoolReportEntity2 = new SchoolReportYearEndEntity();
        schoolReportEntity2.setSchoolReportEntityId(new SchoolReportEntityId(studentId2, "YED4", "E", SCHOOL_OF_RECORD.name()));

        when(schoolReportYearEndRepository.findStudentForSchoolYearEndReport(any())).thenReturn(new PageImpl<>(List.of(schoolReportEntity, schoolReportEntity2), PageRequest.of(0, 1), 1));

        School school = new School();
        school.setDistrictId(districtId.toString());
        when(schoolCache.getSchool(schoolId)).thenReturn(school);

        List<ReportGradStudentData> reportGradStudentDataList = new ArrayList<>();
        var student1 = createReportGradStudentData();
        student1.setGraduationStudentRecordId(studentId);
        student1.setSchoolAtGradId(schoolId);
        reportGradStudentDataList.add(student1);
        var student2 = createReportGradStudentData();
        student2.setGraduationStudentRecordId(studentId2);
        reportGradStudentDataList.add(student2);
        reportGradStudentDataList.add(createReportGradStudentData());

        when(restService.postForList(any(), any(), eq(ReportGradStudentData.class))).thenReturn(reportGradStudentDataList);

        YearEndReportRequest yearEndReportRequest = YearEndReportRequest.builder().districtIds(List.of(districtId)).build();
        List<ReportGradStudentData> result = commonService.getSchoolYearEndReportGradStudentData(yearEndReportRequest);
        assertEquals(1, result.size());
    }

    @Test
    public void testArchiveSchoolReports() {
        UUID schoolReportGuid = UUID.randomUUID();
        UUID schoolReportId = UUID.randomUUID();
        Mockito.when(schoolReportLightRepository.countBySchoolOfRecordsAndReportType(List.of(schoolReportId), "reportType".toUpperCase())).thenReturn(1);
        Mockito.when(schoolReportLightRepository.getReportSchoolOfRecordsByReportType("reportType".toUpperCase())).thenReturn(List.of(schoolReportId));
        Mockito.when(schoolReportRepository.deleteSchoolOfRecordsNotMatchingSchoolReports(List.of(schoolReportGuid), List.of(schoolReportId), "reportTypeARC".toUpperCase())).thenReturn(1);
        Mockito.when(schoolReportRepository.archiveSchoolReports(List.of(schoolReportId), "reportType".toUpperCase(), "reportTypeARC".toUpperCase(), 1L)).thenReturn(1);
        Integer count = commonService.archiveSchoolReports(1L, List.of(schoolReportId), "reportType".toUpperCase());
        assertThat(count).isNotNull().isEqualTo(1);
    }

    @Test
    public void testArchiveSchoolReportsEmpty() {
        UUID schoolReportId = UUID.randomUUID();
        Mockito.when(schoolReportLightRepository.countBySchoolOfRecordsAndReportType(List.of(schoolReportId), "reportType".toUpperCase())).thenReturn(0);
        Mockito.when(schoolReportLightRepository.getReportSchoolOfRecordsByReportType("reportType".toUpperCase())).thenReturn(List.of(schoolReportId));
        Mockito.when(schoolReportRepository.archiveSchoolReports(new ArrayList<>(), "reportType".toUpperCase(), "reportTypeARC".toUpperCase(), 1L)).thenReturn(0);
        Integer count = commonService.archiveSchoolReports(1L, new ArrayList<>(), "reportType".toUpperCase());
        assertThat(count).isNotNull().isEqualTo(0);
    }

    private ReportGradStudentData createReportGradStudentData() {
        ReportGradStudentData reportGradStudentData = new ReportGradStudentData();
        reportGradStudentData.setGraduationStudentRecordId(UUID.randomUUID());
        reportGradStudentData.setTranscriptTypeCode("BC2018-IND");
        reportGradStudentData.setSchoolOfRecordId(UUID.randomUUID());
        reportGradStudentData.setStudentStatus("CUR");
        reportGradStudentData.setCertificateTypes(Collections.singletonList(createGradCertificateTypes()));
        return reportGradStudentData;
    }

    private GradCertificateTypes createGradCertificateTypes() {
        GradCertificateTypes certificateTypes = new GradCertificateTypes();
        certificateTypes.setCode("E");
        certificateTypes.setDescription("Dogwood");
        return certificateTypes;
    }
}
