package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.util.List;

/**
 * Represents information required to generate a student transcript report.
 *
 * @author CGI Information Management Consultants Inc.
 */
public interface AchievementReport extends StudentReport {

    /**
     * Sets the container for achievement results. The transcript should also
     * include an issue date.
     *
     * @param achievement The object that contains a list of transcript results.
     */
    void setAchievement(Achievement achievement);

    /**
     * Sets the container for assessment results. The transcript should also
     * include an issue date.
     *
     * @param assessment The object that contains a list of transcript results.
     */
    void setAssessment(Assessment assessment);

    /**
     * Sets the list of reasons why the student's graduation status is
     * incomplete.
     *
     * @param nonGradReasons Reasons why the student has not yet graduated.
     * @param graduationMessageText The graduation message text (e.g., "...this
     * student has satisfied...with Honours...").
     */
    void setGraduationStatus(List<NonGradReason> nonGradReasons,
            String graduationMessageText);

    /**
     * Sets the student's graduation program. The report uses this to classify
     * the report as either 1950_1986 (adult) or 1995_2004_SCCP, which
     * determines the transcript layout.
     *
     * @param gradProgram The program that the user might have graduated from.
     */
    void setGraduationProgram(GradProgram gradProgram);

    /**
     * Sets whether the report is generated in preview mode. A preview report
     * can have a different appearance than an official report. A preview report
     * can have a colour logo, a watermark, or other aspects that are
     * contextual. An official report uses a B&W logo and would not have a
     * printed watermark (for student transcripts, the watermark is a feature of
     * the official paper; for unofficial copies, no watermark is currently
     * used).
     *
     * @param preview true Custom settings for previewing the report.
     */
    void setPreview(boolean preview);

    /**
     * Sets the student's academic award data. This is used in the compilation
     * of the XML transcripts. This data relates more to graduation requirements
     * over any types of awards.
     *
     * @param graduationData The academic data regarding graduation
     * requirements.
     */
    void setGraduationData(GraduationData graduationData);

    /**
     * Indicates that the HTML version of the transcript should include extra
     * columns for interim marks (percentage and letter grade).
     *
     * @param interim true means to include interim marks.
     */
    void setInterim(boolean interim);
}
