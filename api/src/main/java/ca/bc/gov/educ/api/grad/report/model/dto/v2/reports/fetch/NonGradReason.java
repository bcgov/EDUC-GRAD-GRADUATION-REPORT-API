package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import java.io.Serializable;

public class NonGradReason implements Serializable {
    private static final long serialVersionUID = 2L;

    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }
}
