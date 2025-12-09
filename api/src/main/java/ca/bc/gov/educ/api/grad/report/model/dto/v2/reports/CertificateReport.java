package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;


import ca.bc.gov.educ.api.grad.report.constants.CertificateSubType;
import ca.bc.gov.educ.api.grad.report.constants.CertificateType;

public interface CertificateReport extends StudentReport {

    /**
     * Sets the type of report to generate. This value is passed into the report
     * as the P_REPORT_TYPE parameter.
     *
     * @param crt Non-null certificate type.
     */
    void setReportType(CertificateType crt);

    /**
     * Sets the report subtype to generate. This value is passed into the report
     * as the P_REPORT_SUBTYPE parameter.
     *
     * @param crs Will be INDEPENDENT, FRANCOPHONE, or not set (DEFAULT).
     */
    void setReportSubtype(CertificateSubType crs);

    /**
     * Sets the certificate information to present on the report.
     *
     * @param certificate Contains issue date.
     */
    void setCertificate(Certificate certificate);
}
