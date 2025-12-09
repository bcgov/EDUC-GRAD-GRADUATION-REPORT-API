package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;

public interface GradProgram extends DomainEntity {

    /**
     * Returns the graduation program code (also known as the requirement year).
     * See GraduationProgramCode for a list of possible values.
     *
     * @return A non-null String, never empty.
     */
    GraduationProgramCode getCode();

    /**
     * Changes the graduation program code.
     *
     * @param code The new graduation program code.
     */
    void setCode(GraduationProgramCode code);

    public String getExpiryDate();

    public void setExpiryDate(String value);
}
