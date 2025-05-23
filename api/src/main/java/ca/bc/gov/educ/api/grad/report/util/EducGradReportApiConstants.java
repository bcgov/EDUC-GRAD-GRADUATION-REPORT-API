package ca.bc.gov.educ.api.grad.report.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class EducGradReportApiConstants {

    private EducGradReportApiConstants(){}

    public static final String CORRELATION_ID = "correlationID";
    public static final String USER_NAME = "User-Name";
    public static final String REQUEST_SOURCE = "Request-Source";
    public static final String API_NAME = "EDUC-GRAD-REPORT-API";

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_REPORT_API_ROOT_MAPPING = "/api/" + API_VERSION + "/graduationreports";
    public static final String GRAD_REPORT_API_V2_ROOT_MAPPING = "/api/v2/graduationreports";

    public static final String REPORT_COUNT = "/count";
    public static final String REPORT_ARCHIVE = "/archive";
    public static final String REPORT_DELETE = "/delete";

    public static final String GET_ALL_CERTIFICATE_TYPE_MAPPING = "/certificatetype";
    public static final String GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING = "/certificatetype/{certTypeCode}";

    public static final String GET_ALL_TRANSCRIPT_TYPE_MAPPING = "/transcripttype";
    public static final String GET_ALL_TRANSCRIPT_TYPE_BY_CODE_MAPPING = "/transcripttype/{tranTypeCode}";

    public static final String GET_ALL_PROGRAM_CERTIFICATES_TRANSCRIPTS_MAPPING = "/allprogramcertificates";

    public static final String GET_ALL_REPORT_TYPE_MAPPING = "/reporttype";
    public static final String GET_ALL_REPORT_TYPE_BY_CODE_MAPPING = "/reporttype/{reportTypeCode}";

    public static final String GET_ALL_DOCUMENT_STATUS_MAPPING = "/documentstatus";
    public static final String GET_ALL_DOCUMENT_STATUS_CODE_MAPPING = "/documentstatus/{documentStatusCode}";

    public static final String UPDATE_STUDENT_CREDENTIAL = "/studentcredential";
    public static final String UPDATE_STUDENT_CREDENTIAL_POSTING = "/studentcredential/posting";
    public static final String UPDATE_SCHOOL_REPORTS = "/updateschoolreport";

    public static final String STUDENT_REPORT = "/studentreport";
    public static final String STUDENT_REPORT_BY_STUDENTID = "/studentreport/{studentID}";
    public static final String STUDENT_REPORTS = "/studentreports";
    public static final String STUDENT_REPORTS_BY_GUIDS = "/studentreportsbystudentid";
    public static final String SCHOOL_REPORT = "/schoolreport";
    public static final String STUDENT_CERTIFICATE = "/studentcertificate";
    public static final String STUDENT_CERTIFICATES = "/studentcertificates";
    public static final String STUDENT_TRANSCRIPT = "/studenttranscript";
    public static final String STUDENT_CERTIFICATE_BY_STUDENTID = "/studentcertificate/{studentID}";
    public static final String STUDENT_TRANSCRIPT_BY_STUDENTID = "/studenttranscript/{studentID}";
    public static final String STUDENT_REPORTS_BY_STUDENTID = "/studentreport/{studentID}";
    public static final String STUDENT_TRANSCRIPT_PSI = "psi/studenttranscript/{studentID}";
    public static final String CHECK_SCCP_CERTIFICATE_EXISTS = "/check-sccp-certificate-exists";

    public static final String STUDENT_CREDENTIAL_BUSINESS = "business/studentcredential/{studentID}/{type}";

    public static final String SCHOOL_REPORTS_ROOT_MAPPING = GRAD_REPORT_API_V2_ROOT_MAPPING + "/schoolreports";
    public static final String DISTRICT_REPORTS_ROOT_MAPPING = GRAD_REPORT_API_V2_ROOT_MAPPING + "/district-report";
    public static final String UPDATE_SCHOOL_REPORTS_UPDATE_DETAILS = "/{schoolOfRecordId}/{reportTypeCode}/reset-update-user";
    public static final String DELETE_SCHOOL_REPORT = "/{schoolOfRecordId}/{reportTypeCode}";
    public static final String DELETE_DISTRICT_REPORT = "/{districtId}/{reportTypeCode}";
    public static final String GET_SCHOOL_REPORT_DATA = "/{schoolOfRecordId}/{reportTypeCode}/report-data";

    public static final String SCHOOL_REPORTS_BY_MINCODE = "/schoolreport/{mincode}";
    public static final String SCHOOL_REPORTS_BY_REPORT_TYPE = "/schoolreport/type/{reportType}";

    public static final String STUDENT_CERTIFICATE_BY_DIST_DATE_N_STATUS = "/getcertificatesfordistribution";
    public static final String STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS = "/gettranscriptsfordistribution";
    public static final String STUDENT_FOR_SCHOOL_REPORT = "/getstudentsforschoolreport";
    public static final String STUDENT_FOR_SCHOOL_YEAREND_REPORT = "/getstudentsforschoolyearendreport";

    public static final String STUDENT_TRANSCRIPT_N_REPORTS_POSTING = "/gettranscriptsandreportsforposting";

    public static final String STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS_YEARLY = "/gettranscriptsfordistributionyearly";
    public static final String USER_REQUEST_DIS_RUN = "/userrequest/{credentialType}";
    public static final String USER_REQUEST_DIS_RUN_WITH_NULL_DISTRIBUTION_DATE = "/userrequest/notyetdistributed/{credentialType}";

    public static final String GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING = "/certificate/{certificateTypeCode}";
    public static final String GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING = "/report/{reportTypeCode}";
    
    public static final String DELETE_ACHIEVEMENTS_BY_STUDENTID = "/studentachievement/{studentID}";
    public static final String ARCH_ACHIEVEMENTS_BY_STUDENTID = "/archiveachievement/{studentID}";
    
    public static final String GET_ALL_PROGRAM_CERTIFICATES_MAPPING = "/programcertificates";
    public static final String GET_PROGRAM_TRANSCRIPTS_MAPPING = "/programtranscripts";

    public static final String SEARCH_MAPPING = "/search";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "API_GRAD_REPORT";
    protected static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "API_GRAD_REPORT";
    protected static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SECOND_DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    public static final String SECOND_DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String TRAX_DATE_FORMAT = "yyyyMM";

    @Value("${endpoint.grad-student-api.get-students-for-yearly-run}")
    private String studentsForYearlyDistribution;

    @Value("${endpoint.grad-student-api.get-students-for-school-run}")
    private String studentsForSchoolDistribution;

    @Value("${endpoint.grad-student-api.student-for-special-grad-run-list}")
    private String gradStudentApiStudentForSpcGradListUrl;

    @Value("${endpoint.grad-trax-api.school-clob-by-school-id.url}")
    private String schoolClobBySchoolIdUrl;
    @Value("${endpoint.grad-trax-api.school-by-school-id.url}")
    private String schoolBySchoolIdUrl;
    @Value("${endpoint.grad-trax-api.all-schools.url}")
    private String allSchoolsUrl;

    @Value("${endpoint.grad-trax-api.district-by-dist-no.url}")
    private String districtByDistrictNumberUrl;
    @Value("${endpoint.grad-trax-api.district-by-district-id.url}")
    private String districtByDistrictIdUrl;

    // Splunk LogHelper Enabled
    @Value("${splunk.log-helper.enabled}")
    private boolean splunkLogHelperEnabled;

    @Value("${endpoint.keycloak.getToken}")
    private String tokenUrl;

    @Value("${authorization.user}")
    private String userName;

    @Value("${authorization.password}")
    private String password;

    @Value("${spring.security.oauth2.client.provider.graduation-report-client.token-uri}")
    private String gradReportClientTokenUrl;

    @Value("${spring.security.oauth2.client.registration.graduation-report-client.client-id}")
    private String gradReportClientUserName;

    @Value("${spring.security.oauth2.client.registration.graduation-report-client.client-secret}")
    private String gradReportClientPassword;

    public static HttpHeaders getHeaders (String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(username, password);
        return httpHeaders;
    }

}
