package ca.bc.gov.educ.api.grad.report.util;

public class PermissionsConstants {

    private PermissionsConstants() {
    }

    public static final String _PREFIX = "hasAuthority('";
    public static final String _SUFFIX = "')";

    public static final String UPDATE_GRADUATION_STUDENT_REPORTS = _PREFIX + "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_GRADUATION_STUDENT_REPORTS = _PREFIX + "SCOPE_READ_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_GRADUATION_STUDENT_CERTIFICATES = _PREFIX + "SCOPE_READ_GRAD_STUDENT_CERTIFICATE_DATA" + _SUFFIX;
    public static final String UPDATE_GRADUATION_STUDENT_CERTIFICATES = _PREFIX + "SCOPE_UPDATE_GRAD_STUDENT_CERTIFICATE_DATA" + _SUFFIX;
    public static final String DELETE_STUDENT_ACHIEVEMENT_DATA = _PREFIX + "SCOPE_UPDATE_GRAD_STUDENT_CERTIFICATE_DATA" + _SUFFIX
        + " and " + _PREFIX + "SCOPE_UPDATE_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
    public static final String READ_PROGRAM_CERTIFICATE_TRANSCRIPT = _PREFIX + "SCOPE_READ_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX
        + " and " + _PREFIX + "SCOPE_READ_GRAD_TRANSCRIPT_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_CERTIFICATE = _PREFIX + "SCOPE_READ_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_TRANSCRIPT = _PREFIX + "SCOPE_READ_GRAD_TRANSCRIPT_CODE_DATA" + _SUFFIX;
    public static final String DELETE_CERTIFICATE_TYPE = _PREFIX + "SCOPE_DELETE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String UPDATE_CERTIFICATE_TYPE = _PREFIX + "SCOPE_UPDATE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String CREATE_CERTIFICATE_TYPE = _PREFIX + "SCOPE_CREATE_GRAD_CERTIFICATE_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_REPORT = _PREFIX + "SCOPE_READ_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String DELETE_REPORT_TYPE = _PREFIX + "SCOPE_DELETE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String UPDATE_REPORT_TYPE = _PREFIX + "SCOPE_UPDATE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String CREATE_REPORT_TYPE = _PREFIX + "SCOPE_CREATE_GRAD_REPORT_CODE_DATA" + _SUFFIX;
    public static final String READ_GRAD_DOCUMENT_STATUS = _PREFIX + "SCOPE_READ_GRAD_DOCUMENT_STATUS_CODE_DATA" + _SUFFIX;
}
