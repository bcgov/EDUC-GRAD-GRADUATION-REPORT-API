package ca.bc.gov.educ.api.grad.report.util;

public class PermissionsConstants {

    private PermissionsConstants() {
    }

    public static final String _PREFIX = "#oauth2.hasAnyScope('";
    public static final String _SUFFIX = "')";

    public static final String UPDATE_GRADUATION_STUDENT_REPORTS = _PREFIX + "UPDATE_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_GRADUATION_STUDENT_REPORTS = _PREFIX + "READ_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_GRADUATION_STUDENT_CERTIFICATES = _PREFIX + "READ_GRAD_STUDENT_CERTIFICATE_DATA" + _SUFFIX;
    public static final String UPDATE_GRADUATION_STUDENT_CERTIFICATES = _PREFIX + "UPDATE_GRAD_STUDENT_CERTIFICATE_DATA" + _SUFFIX;
    public static final String DELETE_STUDENT_ACHIEVEMENT_DATA = _PREFIX + "UPDATE_GRAD_STUDENT_CERTIFICATE_DATA','UPDATE_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_PROGRAM_CERTIFICATE_TRANSCRIPT = _PREFIX + "READ_GRAD_CERTIFICATE_CODE_DATA','READ_GRAD_TRANSCRIPT_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_CERTIFICATE = _PREFIX + "READ_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_TRANSCRIPT = _PREFIX + "READ_GRAD_TRANSCRIPT_CODE_DATA" + _SUFFIX;
    public static final String DELETE_CERTIFICATE_TYPE = _PREFIX + "DELETE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String UPDATE_CERTIFICATE_TYPE = _PREFIX + "UPDATE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String CREATE_CERTIFICATE_TYPE = _PREFIX + "CREATE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_REPORT = _PREFIX + "READ_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String DELETE_REPORT_TYPE = _PREFIX + "DELETE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String UPDATE_REPORT_TYPE = _PREFIX + "UPDATE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String CREATE_REPORT_TYPE = _PREFIX + "CREATE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_DOCUMENT_STATUS = _PREFIX + "READ_GRAD_DOCUMENT_STATUS_CODE_DATA" + _SUFFIX;
}
