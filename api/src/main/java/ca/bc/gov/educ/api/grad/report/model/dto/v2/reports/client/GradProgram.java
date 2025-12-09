package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import java.io.Serializable;


public class GradProgram implements Serializable {
    private Code code = new Code();

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
