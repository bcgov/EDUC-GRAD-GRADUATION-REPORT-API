package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import java.io.Serializable;

public class GradProgram implements Serializable {
    private static final long serialVersionUID = 2L;

    private Code code;

    private String expiryDate = "";

    public Code getCode() {
        return code;
    }

    public void setCode(Code value) {
        this.code = value;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }

}
