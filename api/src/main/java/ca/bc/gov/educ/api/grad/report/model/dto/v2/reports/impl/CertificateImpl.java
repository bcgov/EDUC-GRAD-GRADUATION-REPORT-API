package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.constants.CertificateType;
import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.exception.InvalidParameterException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.Certificate;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.OrderType;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SignatureBlockType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Mock certificate used for testing.
 *
 * @author CGI Information Management Consultants Inc.
 */

public class CertificateImpl implements Certificate, Serializable {

    private static final long serialVersionUID = 2L;

    private Date issued;
    private String certStyle;
    private CertificateOrderTypeImpl orderType;
    private Map<String, SignatureBlockType> signatureBlockTypes;

    public CertificateImpl() {
    }

    public CertificateImpl(final Date issued) {
        this.issued = issued;
    }

    @JsonProperty("certStyle")
    public String getCertStyle() {
        return certStyle;
    }

    public void setCertStyle(String certStyle) {
        this.certStyle = certStyle;
    }

    @Override
    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public Date getIssued() {
        return this.issued;
    }

    public void setIssued(Date issued) {
        this.issued = issued;
    }

    @JsonDeserialize(as = CertificateOrderTypeImpl.class)
    public OrderType getOrderType() {
        if(orderType == null) throw new InvalidParameterException("null");
        return orderType;
    }

    @Override
    public Map<String, SignatureBlockType> getSignatureBlockTypes() {
        return this.signatureBlockTypes;
    }

    public void setSignatureBlockTypes(Map<String, SignatureBlockType> signatureBlockTypes) {
        this.signatureBlockTypes = signatureBlockTypes;
    }

    public void setOrderType(OrderType orderType) {
        if(!(orderType instanceof CertificateOrderTypeImpl)) {
            final RuntimeException dse = new RuntimeException("Order Type must be instance of CertificateOrderType");
            throw dse;
        }
        this.orderType = (CertificateOrderTypeImpl)orderType;
    }

    public CertificateType getCertificateType() {
        return orderType.getCertificateType();
    }

}
