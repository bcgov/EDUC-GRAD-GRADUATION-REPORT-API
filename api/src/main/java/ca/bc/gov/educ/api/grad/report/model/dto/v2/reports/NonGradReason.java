package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface NonGradReason extends DomainEntity {

    /**
     * Returns the code that represents why the student did not fulfill their
     * graduation requirements.
     *
     * @return A non-null String, should not be empty.
     */
    String getCode();

    /**
     * Returns the description of why the student did not fulfill their
     * graduation requirements.
     *
     * @return A non-null String, should not be empty.
     */
    String getDescription();
}
