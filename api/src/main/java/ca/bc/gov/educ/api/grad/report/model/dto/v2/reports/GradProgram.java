package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;

public final class GradProgram extends AbstractDomainEntity {

    private static final long serialVersionUID = 3L;

    private GraduationProgramCode code;
    private String programCode;
    private String programName;
    private String expiryDate;

    public GradProgram() {

    }

    public GradProgram(GraduationProgramCode code, String expiryDate) {
        setCode(code);
        setExpiryDate(expiryDate);
    }
    
    public GraduationProgramCode getCode() {
        return this.code;
    }
    
    public void setCode(final GraduationProgramCode code) {
        this.code = code;
    }
    
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCode() {
        this.code = GraduationProgramCode.valueFrom(programCode, programName);
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }
}
