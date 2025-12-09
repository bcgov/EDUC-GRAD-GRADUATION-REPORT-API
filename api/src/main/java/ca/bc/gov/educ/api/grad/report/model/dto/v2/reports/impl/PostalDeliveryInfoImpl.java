package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;


import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.PostalAddressImpl;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.PostalDeliveryInfo;

import java.io.Serializable;
import java.util.Date;

public class PostalDeliveryInfoImpl extends PostalAddressImpl implements PostalDeliveryInfo, Serializable {

    private static final long serialVersionUID = 2L;

    private String name = "";
    private String attentionTo = "";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAttentionTo() {
        return attentionTo;
    }

    @Override
    public void setAttentionTo(String attentionTo) {
        this.attentionTo = attentionTo;
    }

    @Override
    public void setCountryCode(String countryCode) {
        super.setCountry(countryCode);
    }

    @Override
    public void setPostalCode(String code) {
        super.setCode(code);
    }

    @Override
    public String getEntityId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getCreatedOn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCreatedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getLastUpdatedOn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLastUpdatedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
