package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public final class ExaminationResult extends ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.Mark {

    private static final long serialVersionUID = 1L;

    /**
     * School percentage mark for this course.
     */
    private String schoolPercent;

    /**
     * Exam percentage mark for this course.
     */
    private String examPercent;

    /**
     * Default (empty) constructor.
     */
    public ExaminationResult() {
    }

    /**
     * Returns the school percent the student achieved for this course.
     *
     * @return A number from 0 - 100, or a three-letter code.
     */
    @Override
    public String getSchoolPercent() {
        return nullSafe(this.schoolPercent);
    }

    /**
     * Returns the exam percent the student achieved for this course.
     *
     * @return A number from 0 - 100, or a three-letter code.
     */
    @Override
    public String getExamPercent() {
        return nullSafe(this.examPercent);
    }

    /**
     * Used by the builder to set the school percent.
     *
     * @param schoolPercent Passed in by the builder.
     */
    protected void setSchoolPercent(final String schoolPercent) {
        this.schoolPercent = schoolPercent;
    }

    /**
     * Used by the builder to set the exam percent.
     *
     * @param examPercent Passed in by the builder.
     */
    protected void setExamPercent(final String examPercent) {
        this.examPercent = examPercent;
    }

    /**
     * Used to create instances of the outer class.
     */
    public static final class Builder
            extends ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.Mark.Builder<ExaminationResult, Builder> {

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
         * @return New ExaminationResult instance.
         */
        @Override
        protected ExaminationResult createObject() {
            return new ExaminationResult();
        }

        /**
         * Sets the school percent the student achieved for this course.
         *
         * @param schoolPercent A number from 0 - 100, or a three-letter code.
         * @return thisBuilder
         */
        public Builder withSchoolPercent(final String schoolPercent) {
            getObject().setSchoolPercent(schoolPercent);
            return thisBuilder();
        }

        /**
         * Sets the exam percent the student achieved for this course.
         *
         * @param examPercent A number from 0 - 100, or a three-letter code.
         * @return thisBuilder
         */
        public Builder withExamPercent(final String examPercent) {
            getObject().setExamPercent(examPercent);
            return thisBuilder();
        }

        /**
         * Convenience method to set the school percent value.
         *
         * @param schoolPercent Value to convert to a String.
         * @return thisBuilder
         */
        public Builder withSchoolPercent(final Integer schoolPercent) {
            return withSchoolPercent(schoolPercent.toString());
        }

        /**
         * Convenience method to set the exam percent value.
         *
         * @param examPercent Value to convert to a String.
         * @return thisBuilder
         */
        public Builder withExamPercent(final Integer examPercent) {
            return withExamPercent(examPercent.toString());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "schoolPercent=" + schoolPercent + ", examPercent=" + examPercent + '}';
    }
}
