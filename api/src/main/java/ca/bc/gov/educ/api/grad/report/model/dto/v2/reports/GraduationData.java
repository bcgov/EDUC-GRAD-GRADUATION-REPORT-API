package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.time.LocalDate;
import java.util.List;

public interface GraduationData extends DomainEntity {

    /**
     * Returns true iff the student has graduated. When this returns false, the
     * graduation date will not contain valid data.
     *
     * @return false The student has not graduated.
     */
    boolean hasGraduated();

    /**
     * Get the date the student graduated. This will return the current date if
     * the user has not graduated.
     *
     * @return Date of graduation.
     */
    LocalDate getGraduationDate();

    /**
     * Get the truncated date the student graduated in the form of yyyy/MM. This will return the current date if
     * the user has not graduated.
     *
     * @return Date of graduation.
     */
    String getTruncatedGraduationDate();

    /**
     * Get the full date the student graduated in the form of yyyy-MM-dd. This will return the current date if
     * the user has not graduated.
     *
     * @return Date of graduation.
     */
    String getFullGraduationDate();

    /**
     * Get the honours flag.
     *
     * @return
     */
    Boolean getHonorsFlag();

    /**
     * Get the dogwood flag
     *
     * @return
     */
    Boolean getDogwoodFlag();

    /**
     * Get the total credits used for graduation
     *
     * @return
     */
    String getTotalCreditsUsedForGrad();


    /**
     * Get the list of program codes.
     *
     * @return
     */
    List<String> getProgramCodes();

    /**
     * Get the list of program code Names.
     *
     * @return
     */
    List<String> getProgramNames();

}
