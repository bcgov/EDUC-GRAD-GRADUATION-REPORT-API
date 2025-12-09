package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;


import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AbstractDomainEntity;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GradProgram;

public final class GradProgramImpl extends AbstractDomainEntity
        implements GradProgram {

    private static final long serialVersionUID = 3L;

    private GraduationProgramCode code;
    private String programCode;
    private String programName;
    private String expiryDate;

    public GradProgramImpl() {

    }

    public GradProgramImpl(GraduationProgramCode code, String expiryDate) {
        setCode(code);
        setExpiryDate(expiryDate);
    }

    @Override
    public GraduationProgramCode getCode() {
        return this.code;
    }

    @Override
    public void setCode(final GraduationProgramCode code) {
        this.code = code;
    }

    @Override
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

    @Override
    public String getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }
}
