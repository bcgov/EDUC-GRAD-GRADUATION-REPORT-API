package ca.bc.gov.educ.api.grad.report.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum AchievementType {

    DEFAULT("Default", PaperType.ACHIEVEMENT);

    private String reportName;
    private PaperType paperType;

    AchievementType() {
    }

    /**
     * Constructs a new enumerated type for a certificate.
     *
     * @param reportName Certificate subreport template filename.
     */
    AchievementType(final String reportName, final PaperType paperType) {

        this.reportName = reportName;
        this.paperType = paperType;
    }

    /**
     * Returns the paper type required for printing the certificate.
     *
     * @return A non-null instance for controlling a Xerox printer.
     */
    public PaperType getPaperType() {
        return this.paperType;
    }

    /**
     * Returns the file name for this certificate report type.
     *
     * @return The report file name.
     */
    @Override
    public String toString() {
        return this.reportName;
    }

    public String getReportName() {
        return reportName;
    }
  
    void setReportName(String reportName) {
        this.reportName = reportName;
    }

    void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    @JsonCreator
    public static AchievementType forValue(@JsonProperty("reportName") final String reportName) {
        for (AchievementType certificateType : AchievementType.values()) {
            if (certificateType.getReportName().equals(reportName)) {
                return certificateType;
            }
        }
        return null;
    }
}
