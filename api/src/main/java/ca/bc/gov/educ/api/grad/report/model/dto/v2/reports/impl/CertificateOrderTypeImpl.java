package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.constants.CertificateType;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.CertificateOrderType;

public class CertificateOrderTypeImpl extends OrderTypeImpl
        implements CertificateOrderType {

    private static final long serialVersionUID = 3L;

    private String name;
    private CertificateType certificateType;

    public CertificateOrderTypeImpl() {
    }

    /**
     * Constructs with paper type based on the certificate that was ordered.
     *
     * @param certificateType Type of certificate ordered.
     */
    public CertificateOrderTypeImpl(final CertificateType certificateType) {
        this.certificateType = certificateType;
        setPaperType(certificateType.getPaperType());
    }

    /**
     * Returns the human-readable name for certificates.
     *
     * @return "Certificates"
     */
    @Override
    public String getName() {
        return "Certificates";
    }

    public void setName(String name) {
        this.name = name;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
        this.setPaperType(this.certificateType.getPaperType());
    }

}
