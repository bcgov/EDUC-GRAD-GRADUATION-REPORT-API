package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class PostalAddressImpl implements Serializable {

    private static final long serialVersionUID = 2L;

    private String streetLine1 = "";
    private String streetLine2 = "";
    private String streetLine3 = "";
    private String city = "";
    private String region = "";
    private String code = "";
    private String country = "";

    private static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "Not supported yet.";
    private static final String COUNTRY_NAME_CANADA = "CANADA";


    @JsonProperty("address1")
    public String getStreetLine1() {
        return streetLine1;
    }


    @JsonProperty("address2")
    public String getStreetLine2() {
        return streetLine2;
    }


    @JsonProperty("address3")
    public  String getStreetLine3() {
        return streetLine3;
    }


    @JsonProperty("city")
    public  String getCity() {
        return city;
    }


    @JsonProperty("provinceName")
    public  String getRegion() {
        return region;
    }


    @JsonProperty("postal")
    public  String getPostalCode() {
        switch(StringUtils.trimToEmpty(getCountryCode()).toUpperCase()) {
            case "CA", COUNTRY_NAME_CANADA:
                if(StringUtils.isNotBlank(this.code) && this.code.length() == 6) {
                    return new StringBuilder(this.code).insert(3, " ").toString();
                } else {
                    return this.code;
                }
            case "US", "USA":
                if(StringUtils.isNotBlank(this.code) && this.code.length() == 9) {
                    return new StringBuilder(this.code).insert(5, "-").toString();
                } else {
                    return this.code;
                }
            default:
                return this.code;
        }
    }


    @JsonProperty("countryCode")
    public  String getCountryCode() {
        return country;
    }

    /**
     * set street.
     *
     * @param streetLine1
     */
    public void setStreetLine1(final String streetLine1) {
        this.streetLine1 = streetLine1;
    }

    /**
     * set street.
     *
     * @param streetLine2
     */
    public void setStreetLine2(final String streetLine2) {
        this.streetLine2 = streetLine2;
    }

    /**
     * set street.
     *
     * @param streetLine3
     */
    public void setStreetLine3(final String streetLine3) {
        this.streetLine3 = streetLine3;
    }

    /**
     * set city name.
     *
     * @param city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * set the Province name.
     *
     * @param region
     */
    public void setRegion(final String region) {
        this.region = region;
    }

    /**
     * set the country.
     *
     * @param country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * set the code (e.g., post, postal, zip).
     *
     * @param code
     */
    public void setCode(final String code) {
        this.code = code;
    }


    public String getFormattedAddressForLabels() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStreetLine1());
        if(StringUtils.isNotBlank(getStreetLine2())) {
            sb.append("\n").append(getStreetLine2());
        }
        if(StringUtils.isNotBlank(getStreetLine3())) {
            sb.append("\n").append(getStreetLine3());
        }
        if(StringUtils.isNotBlank(getCity())) {
            sb.append("\n").append(getCity());
        }
        if(StringUtils.isNotBlank(getRegion())) {
            sb.append(" ").append(getRegion());
        }
        if(StringUtils.isNotBlank(getCountryCode()) && !"CA".equalsIgnoreCase(getCountryCode()) && !COUNTRY_NAME_CANADA.equalsIgnoreCase(getCountryCode())) {
            sb.append("\n").append(getCountryCode());
        }

        if(StringUtils.isNotBlank(getPostalCode())) {
            sb.append("CA".equalsIgnoreCase(getCountryCode()) || COUNTRY_NAME_CANADA.equalsIgnoreCase(getCountryCode()) || StringUtils.isBlank(getCountryCode()) ? "  " : " ").append(getPostalCode());
        }
        return sb.toString();
    }


    public String getEntityId() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }


    public Date getCreatedOn() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }


    public String getCreatedBy() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }


    public Date getLastUpdatedOn() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }


    public String getLastUpdatedBy() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }


    public Long getId() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE); //To change body of generated methods, choose Tools | Templates.
    }

}