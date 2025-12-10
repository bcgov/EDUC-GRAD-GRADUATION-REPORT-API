package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface Mark extends DomainEntity {

    /**
     * On the transcript report, this is the Gr. 12 School % column.
     *
     * @return A number from 0 - 100, or a three-letter code.
     */
    String getSchoolPercent();

    /**
     * Returns the best school percentage value for an examination result.
     *
     * @return A number from 0 - 100.
     */
    String getBestSchoolPercent();

    /**
     * On the transcript report, this is the Gr. 12 Exam % column.
     *
     * @return A number from 0 - 100, or a three-letter code.
     */
    String getExamPercent();

    /**
     * Returns the best examination percentage value for an examination result.
     *
     * @return A number from 0 - 100.
     */
    String getBestExamPercent();

    /**
     * Final grade as a percentage, but also includes status codes.
     *
     * @return A number from 0 - 100 or a three-letter code.
     */
    String getFinalPercent();

    /**
     * Final grade percentage translated into a letter grade.
     *
     * @return A letter, typically between A through F with + and - symbols.
     */
    String getFinalLetterGrade();

    /**
     * Interim grade as a percentage, but also includes status codes.
     *
     * @return A number from 0 - 100 or a three-letter code.
     */
    String getInterimPercent();

    /**
     * Interim grade percentage translated into a letter grade.
     *
     * @return A letter, typically between A through F with + and - symbols.
     */
    String getInterimLetterGrade();
}
