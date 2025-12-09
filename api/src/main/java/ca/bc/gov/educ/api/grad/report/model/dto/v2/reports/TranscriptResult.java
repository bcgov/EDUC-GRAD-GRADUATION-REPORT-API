package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface TranscriptResult extends DomainEntity {

    /**
     * Returns a course the student attended.
     *
     * @return A course associated with a mark
     */
    Course getCourse();

    /**
     * Returns the mark the student received for the course.
     *
     * @return A non-null Mark instance.
     */
    Mark getMark();

    /**
     * Indicates whether the course met the graduation requirements.
     *
     * @return Graduation requirement, if any, that the course met.
     */
    String getRequirementMet();

    /**
     * If course met the graduation requirements then return course name.
     *
     * @return Graduation requirement, if any, that the course met.
     */
    String getRequirementMetName();

    /**
     * Returns whether course credit was granted through the Equivalency ("E")
     * or Challenge ("C") process.
     *
     * @return Either "C" or "E", or empty string.
     */
    String getEquivalencyChallenge();

    /**
     * TODO: Change to Boolean.
     *
     * Indicates whether the course is used for graduation.
     *
     * @return Graduation requirement, if any, that the course met.
     */
    String getUsedForGrad();

}
