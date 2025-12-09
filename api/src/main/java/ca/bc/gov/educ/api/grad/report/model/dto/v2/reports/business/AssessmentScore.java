package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business;

import ca.bc.gov.educ.api.grad.report.constants.AssessmentCode;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.BusinessEntity;

import static ca.bc.gov.educ.api.grad.report.constants.AssessmentCode.NO_RESPONSE;

public class AssessmentScore extends BusinessEntity {

    /**
     * Look-up code for textual descriptions pertaining to the student's
     * assessment score category.
     */
    private AssessmentCode assessmentCode = NO_RESPONSE;

    /**
     * The student's actual score, which must be less than or equal to the
     * maximum score.
     */
    private Integer studentScore = -1;

    /**
     * Maximum possible score for the assessment category defined by the
     * assessment code.
     */
    private Integer maximumScore = -1;

    /**
     * Used to create instances of the outer class.
     */
    public static final class Builder extends BusinessEntity.Builder<AssessmentScore, Builder> {

        /**
         * Returns the builder used to construct outer class instances.
         *
         * @return this
         */
        @Override
        protected Builder thisBuilder() {
            return this;
        }

        /**
         * Returns an outer class instance without attributes initialized.
         *
         * @return New instance.
         */
        @Override
        protected AssessmentScore createObject() {
            return new AssessmentScore();
        }

        /**
         * Sets the assessment code used to look up text strings for GNA report.
         *
         * @param assessmentCode Non-null instance.
         * @return thisBuilder
         */
        public Builder withAssessmentCode(
                final AssessmentCode assessmentCode) {
            getObject().setAssessmentCode(assessmentCode);
            return thisBuilder();
        }

        /**
         * Sets the student score.
         *
         * @param studentScore -1 or positive integer, not null.
         * @return thisBuilder
         */
        public Builder withStudentScore(
                final Integer studentScore) {
            getObject().setStudentScore(studentScore);
            return thisBuilder();
        }

        /**
         * Sets the student score.
         *
         * @param maximumScore Positive integer, never null.
         * @return thisBuilder
         */
        public Builder withMaximumScore(
                final Integer maximumScore) {
            getObject().setMaximumScore(maximumScore);
            return thisBuilder();
        }
    }

    /**
     * Returns the look-up code for textual descriptions pertaining to the
     * student's assessment score category.
     *
     * @return A non-null instance (PLAN_AND_DESIGN by default).
     */
    public AssessmentCode getAssessmentCode() {
        return this.assessmentCode;
    }

    /**
     * Changes the look-up code for textual descriptions pertaining to the
     * student's assessment score category.
     *
     * @param assessmentCode A non-null value.
     */
    public void setAssessmentCode(final AssessmentCode assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    /**
     * Returns student's actual score.
     *
     * @return -1 means "no response" (as "NR" on the report), 0 should never be
     * returned; otherwise, a small non-null value.
     */
    public Integer getStudentScore() {
        return this.studentScore;
    }

    /**
     * Sets the student's actual score.
     *
     * @param studentScore -1, or a positive integer, null values are displayed blank on report.
     */
    public void setStudentScore(final Integer studentScore) {
        this.studentScore = studentScore;
    }

    /**
     * Returns the maximum possible score for the assessment category defined by
     * the assessment code.
     *
     * @return Never null, possibly zero if not initialized.
     */
    public Integer getMaximumScore() {
        return this.maximumScore;
    }

    /**
     * Sets the maximum possible score for the assessment category defined by
     * the assessment code.
     *
     * @param maximumScore Always a positive integer, null values are displayed blank on report.
     */
    public void setMaximumScore(final Integer maximumScore) {
        this.maximumScore = maximumScore;
    }
}
