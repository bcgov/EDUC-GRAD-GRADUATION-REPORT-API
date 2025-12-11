package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import java.io.Serializable;

public class OtherProgram implements Serializable {

    private static final long serialVersionUID = 2L;

    private String programCode;
    private String programName;

    public OtherProgram() {
    }

    public OtherProgram(String programCode, String programName) {
        this.programCode = programCode;
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
}
