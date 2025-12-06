package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class NonGradReason extends AbstractDomainEntity implements Serializable {

    private static final long serialVersionUID = 3L;

    private String code;
    private String description;

    public NonGradReason() {}


    public String getCode() {
        return StringUtils.startsWith(code, "!") ? "" : code;
    }


    public String getDescription() {
        return this.description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public String toString() {
        return getDescription();
    }
}
