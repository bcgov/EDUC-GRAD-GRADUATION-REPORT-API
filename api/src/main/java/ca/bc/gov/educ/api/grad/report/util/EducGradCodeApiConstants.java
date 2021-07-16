package ca.bc.gov.educ.api.grad.report.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class EducGradCodeApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_CODE_API_ROOT_MAPPING = "/api/" + API_VERSION + "/code";
    public static final String GET_ALL_COUNTRY_MAPPING = "/country";
    public static final String GET_ALL_COUNTRY_BY_CODE_MAPPING = "/country/{countryCode}";
    
    public static final String GET_ALL_PROVINCE_MAPPING = "/province";
    public static final String GET_ALL_PROVINCE_BY_CODE_MAPPING = "/province/{provinceCode}";
    
    public static final String GET_ALL_PROGRAM_MAPPING = "/program";
    public static final String GET_ALL_PROGRAM_BY_CODE_MAPPING = "/program/{programCode}";
    
    public static final String GET_ALL_UNGRAD_MAPPING = "/ungradreason";
    public static final String GET_ALL_UNGRAD_BY_CODE_MAPPING = "/ungradreason/{reasonCode}";
    
    public static final String GET_ALL_CERTIFICATE_TYPE_MAPPING = "/certificatetype";
    public static final String GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING = "/certificatetype/{certTypeCode}";
    
    public static final String GET_ALL_GRAD_MESSAGING_MAPPING = "/gradmessages";
    public static final String GET_ALL_GRAD_MESSAGING_BY_PRG_CODE_AND_MESSAGE_TYPE_MAPPING = "/gradmessages/pgmCode/{pgmCode}/msgType/{msgType}";
    
    public static final String GET_ALL_GRAD_CAREER_PROGRAM_MAPPING = "/careerprogram";
    public static final String GET_ALL_GRAD_CAREER_PROGRAM_BY_CODE_MAPPING = "/careerprogram/{cpCode}";
    
    public static final String GET_ALL_GRAD_STATUS_CODE_MAPPING = "/gradstatus";
    public static final String GET_ALL_GRAD_STATUS_CODE_BY_CODE_MAPPING = "/gradstatus/{statusCode}";
    
    public static final String GET_ALL_GRAD_PROGRAM_TYPE_CODE_MAPPING = "/gradprogramtype";
    public static final String GET_ALL_GRAD_PROGRAM_TYPE_CODE_BY_CODE_MAPPING = "/gradprogramtype/{typeCode}";
    
    public static final String GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_MAPPING = "/gradrequirementtype";
    public static final String GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_BY_CODE_MAPPING = "/gradrequirementtype/{typeCode}";
    
    public static final String GET_ALL_REPORT_TYPE_MAPPING = "/reporttype";
    public static final String GET_ALL_REPORT_TYPE_BY_CODE_MAPPING = "/reporttype/{reportTypeCode}";
    
    public static final String GET_ALL_STUDENT_STATUS_MAPPING = "/studentstatus";
    public static final String GET_ALL_STUDENT_STATUS_BY_CODE_MAPPING = "/studentstatus/{statusCode}";
    
    
    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "CodeAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "CodeAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
    
    @Value("${endpoint.grad-common-api.grad_certificate_list_by_certificate_code.url}")
    private String gradStudentCertificateByCertificateTypeCode;
    
    @Value("${endpoint.grad-common-api.student_ungrad_list_by_ungrad_reason_code.url}")
    private String gradStudentUngradReasonByUngradReasonCode;
    
    @Value("${endpoint.grad-common-api.student_career_program_list_by_career_program_code.url}")
    private String gradStudentCareerProgramByCareerProgramCode;
    
    @Value("${endpoint.grad-program-management-api.program_list_by_requirement_type_code.url}")
    private String gradRequirementTypeByRequirementTypeCode;
    
    @Value("${endpoint.grad-common-api.grad_report_list_by_report_code.url}")
    private String gradReportTypeByReportTypeCode;
    
    @Value("{endpoint.graduation-status-api.check-grad-student-status.url}")
    private String gradStudentStatusByStatusCode;

}
