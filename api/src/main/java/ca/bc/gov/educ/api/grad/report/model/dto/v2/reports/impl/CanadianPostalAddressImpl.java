package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.CanadianPostalAddress;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.PostalAddressImpl;

import java.io.Serializable;
import java.util.Date;

public class CanadianPostalAddressImpl extends PostalAddressImpl implements CanadianPostalAddress, Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * Empty constructor to initialize an empty address instance.
     */
    public CanadianPostalAddressImpl() {
    }


    public String getEntityId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Date getCreatedOn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getCreatedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Date getLastUpdatedOn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getLastUpdatedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getProvince() {
        return getRegion();
    }

    /**
     * Set the province name.
     *
     * @param province
     */
    public void setProvince(String province) {
        setRegion(province);
    }

    public void setPostalCode(String postalCode) {
        setCode(postalCode);
    }
}
