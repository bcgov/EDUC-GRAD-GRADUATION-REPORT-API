package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.CertificateType;

import java.util.Date;
import java.util.Map;

public interface Certificate {

    public static final String CERT_STYLE_ORIGINAL = "Original";
    public static final String CERT_STYLE_REPRINT = "Reprint";
    public static final String CERT_STYLE_BLANK = "Blank";

    /**
     * Returns the date (year and month) when the certificate was issued to the
     * student.
     *
     * @return A non-null Date instance.
     */
    Date getIssued();

    /**
     * Returns the flag if the certificate is origin or replacement
     *
     * @return A non-null Date instance.
     */
    String getCertStyle();

    /**
     * Gets certificate type.
     *
     * @return the certificate type
     */
    OrderType getOrderType();

    /**
     * Gets signature block types.
     *
     * @return the Map of SignatureBlockType types
     */

    Map<String, SignatureBlockType> getSignatureBlockTypes();

    /**
     * Gets certificate type.
     *
     * @return the CertificateType
     */
    CertificateType getCertificateType();
}
