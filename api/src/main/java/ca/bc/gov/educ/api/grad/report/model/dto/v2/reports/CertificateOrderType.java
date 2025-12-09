package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.CertificateType;

public interface CertificateOrderType extends OrderType {
    CertificateType getCertificateType();
}
