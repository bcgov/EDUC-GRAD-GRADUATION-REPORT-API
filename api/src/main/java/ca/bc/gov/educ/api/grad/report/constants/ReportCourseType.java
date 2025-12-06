package ca.bc.gov.educ.api.grad.report.constants;

public enum ReportCourseType {

    /**
     * Report course type of 1.
     */
    PROVINCIALLY_EXAMINABLE("1", "Provincially Examinable"),
    /**
     * Report course type of 2.
     */
    NONPROVINCIALLY_EXAMINABLE("2", "Non-provincially Examinable"),
    /**
     * Report course type of 3.
     */
    ASSESSMENT("3", "Assessment");

    private final String code;
    private final String description;

    ReportCourseType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Returns the enum associated with the given code.
     *
     * @param code The code to find the value of.
     * @return The course type code for the given code.
     */
    public static ReportCourseType valueFrom(final String code) {
        for (final ReportCourseType rct : values()) {
            if (rct.isCode(code)) {
                return rct;
            }
        }

        throw new IllegalArgumentException("No such course type code <" + code + ">.");
    }

    public boolean isCode(final String code) {
        final String thisCode = getCode();

        return thisCode.equals(code);
    }

    public String getCode() {
        return this.code;
    }

    private String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
