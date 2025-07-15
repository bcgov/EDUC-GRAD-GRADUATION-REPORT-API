package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.District;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class InstituteServiceTest {

    @Autowired
    EducGradReportApiConstants constants;
    @MockBean
    RESTService restService;
    @MockBean
    @Qualifier("graduationReportApiClient")
    WebClient graduationServiceWebClient;
    @Autowired
    InstituteService instituteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    void testGetSchool_whenValidSchoolId_returnSuccess() {
        UUID schoolId = UUID.randomUUID();
        School school = new School();
        school.setSchoolId(schoolId.toString());
        school.setDisplayName("My School");

        when(restService.get(String.format(constants.getSchoolBySchoolIdUrl(), schoolId),
                School.class, graduationServiceWebClient)).thenReturn(school);

        School response = instituteService.getSchool(schoolId);

        assertNotNull(response);
        assertEquals(schoolId.toString(), response.getSchoolId());
    }

    @Test
    void testGetAllSchools_returnSuccess() {
        School schoolOne = new School();
        schoolOne.setSchoolId(UUID.randomUUID().toString());
        schoolOne.setDisplayName("My School");

        School schoolTwo = new School();
        schoolTwo.setSchoolId(UUID.randomUUID().toString());
        schoolTwo.setDisplayName("Your School");

        List<School> schools = List.of(schoolOne, schoolTwo);

        when(restService.get(constants.getAllSchoolsUrl(), List.class, graduationServiceWebClient)).thenReturn(schools);

        List<School> response = instituteService.getAllSchools();
        assertThat(response).isNotNull().isNotEmpty().hasSize(2);
    }

    @Test
    void testGetDistrict_whenValidDistrictId_returnSuccess() {
        UUID districtId = UUID.randomUUID();
        District district = new District();
        district.setDistrictId(districtId.toString());
        district.setDisplayName("My District");

        when(restService.get(String.format(constants.getSchoolBySchoolIdUrl(), districtId),
                District.class, graduationServiceWebClient)).thenReturn(district);

        District response = instituteService.getDistrict(districtId);

        assertNotNull(response);
        assertEquals(districtId.toString(), response.getDistrictId());
    }

}
