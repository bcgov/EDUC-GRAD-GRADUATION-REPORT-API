package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;


public interface Course extends DomainEntity {

    /**
     * Returns the course name (e.g., "LD History AP 12").
     *
     * @return A non-null string that contains the name of the course taken.
     */
    String getName();

    /**
     * Returns the course code associated with the course name (e.g., "XHIAP").
     *
     * @return A non-null string that contains the course code (5 digits max).
     */
    String getCode();

    /**
     * Returns the code that represents the course level (e.g., "12", "12A").
     *
     * @return A non-null string that contains the course level (3 digits max).
     */
    String getLevel();

    /**
     * Returns a string that represents the credits earned by a student for
     * completing this course suitable for displaying to the user. This can
     * return results such as {2p, 2, (4), 4, 1}.
     *
     * @return A non-null, possibly empty String.
     */
    String getCredits();

    /**
     * Returns when the course was offered.
     *
     * @return The date the course was offered (or taken?).
     */
    String getSessionDate();

    String getType();

    /**
     * Returns the related course code.
     *
     * @return
     */
    String getRelatedCourse();

    /**
     * Returns the grade level of the related course.
     *
     * @return
     */
    String getRelatedLevel();

}
