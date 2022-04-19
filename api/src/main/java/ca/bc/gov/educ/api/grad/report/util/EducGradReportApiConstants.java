package ca.bc.gov.educ.api.grad.report.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class EducGradReportApiConstants {

    private EducGradReportApiConstants(){}

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_REPORT_API_ROOT_MAPPING = "/api/" + API_VERSION + "/graduationreports";

    public static final String GET_ALL_CERTIFICATE_TYPE_MAPPING = "/certificatetype";
    public static final String GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING = "/certificatetype/{certTypeCode}";

    public static final String GET_ALL_TRANSCRIPT_TYPE_MAPPING = "/transcripttype";
    public static final String GET_ALL_TRANSCRIPT_TYPE_BY_CODE_MAPPING = "/transcripttype/{tranTypeCode}";

    public static final String GET_ALL_PROGRAM_CERTIFICATES_TRANSCRIPTS_MAPPING = "/allprogramcertificates";

    public static final String GET_ALL_REPORT_TYPE_MAPPING = "/reporttype";
    public static final String GET_ALL_REPORT_TYPE_BY_CODE_MAPPING = "/reporttype/{reportTypeCode}";

    public static final String GET_ALL_DOCUMENT_STATUS_MAPPING = "/documentstatus";
    public static final String GET_ALL_DOCUMENT_STATUS_CODE_MAPPING = "/documentstatus/{documentStatusCode}";

    public static final String UPDATE_STUDENT_CREDENTIAL = "/studentcredential/{studentID}/{credentialTypeCode}/{paperType}";

    public static final String STUDENT_REPORT = "/studentreport";
    public static final String STUDENT_CERTIFICATE = "/studentcertificate";
    public static final String STUDENT_TRANSCRIPT = "/studenttranscript";
    public static final String STUDENT_CERTIFICATE_BY_STUDENTID = "/studentcertificate/{studentID}";
    public static final String STUDENT_TRANSCRIPT_BY_STUDENTID = "/studenttranscript/{studentID}";
    public static final String STUDENT_REPORTS_BY_STUDENTID = "/studentreport/{studentID}";

    public static final String STUDENT_CERTIFICATE_BY_DIST_DATE_N_STATUS = "/getcertificatesfordistribution";
    public static final String STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS = "/gettranscriptsfordistribution";

    public static final String STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS_YEARLY = "/gettranscriptsfordistributionyearly";

    public static final String GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING = "/certificate/{certificateTypeCode}";
    public static final String GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING = "/report/{reportTypeCode}";
    
    public static final String DELETE_ACHIEVEMENTS_BY_STUDENTID = "/studentachievement/{studentID}";
    
    public static final String GET_ALL_PROGRAM_CERTIFICATES_MAPPING = "/programcertificates";
    public static final String GET_PROGRAM_TRANSCRIPTS_MAPPING = "/programtranscripts";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "API_GRAD_REPORT";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "API_GRAD_REPORT";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";

    @Value("${endpoint.grad-student-api.get-students-for-yearly-run}")
    private String studentsForYearlyDistribution;

}
