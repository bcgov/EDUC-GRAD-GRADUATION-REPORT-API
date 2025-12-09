package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;
import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;

import java.util.Date;
import java.util.List;

public interface Transcript extends DomainEntity {

    /**
     * Returns a list of results that contains courses and associated marks a
     * student achieved.
     *
     * @return The list of marks for a student transcript.
     */
    List<TranscriptResult> getResults();

    /**
     * Sorts the transcript results.
     *
     * @param graduationProgramCode The student's graduation program code used
     * to determine how to sort the results.
     * @return The list of results, sorted according to the program code
     * requirements.
     */
    List<TranscriptResult> getResults(GraduationProgramCode graduationProgramCode);

    /**
     * Returns the date that the transcript was issued. On the transcript
     * report, this is the report date.
     *
     * @return Date that the transcript was issued.
     */
    Date getIssueDate();

    /**
     * Convenience method that answers whether the transcript has any transcript
     * results.
     *
     * @return true iff getResults().isEmpty() == true.
     */
    boolean isEmpty();

    /**
     * Convenience method that answers whether the transcript is interim.
     *
     * @return true iff getResults().isEmpty() == true.
     */
    boolean getInterim();

    /**
     * Convenience method that answers whether the transcript is blank.
     *
     * @return true iff getResults().isEmpty() == true.
     */
    boolean getBlank();

    /**
     * Returns the type of the transcript
     *
     * @return transcript type
     */
    TranscriptTypeCode getTranscriptTypeCode();

}
