package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.ReportFormat;

import java.util.Objects;

public class BusinessReport extends AbstractDomainEntity implements ReportDocument {

    private static final long serialVersionUID = 1L;

    private final byte[] reportData;
    private final ReportFormat repotFormat;
    private final String reportName;
    private final String filename;

    /**
     * Class Constructor populates all the object attributes during
     * construction. Uses an empty report name.
     *
     * @param reportData
     * @param reportFormat
     * @param filename
     */
    public BusinessReport(
            final byte[] reportData,
            final ReportFormat reportFormat,
            final String filename) {
        this(reportData, reportFormat, filename, "");
    }

    /**
     * Class Constructor populates all the object attributes during
     * construction.
     *
     * @param reportData
     * @param reportFormat
     * @param filename
     * @param reportName
     */
    public BusinessReport(
            final byte[] reportData,
            final ReportFormat reportFormat,
            final String filename,
            final String reportName) {
        this.reportData = reportData;
        this.repotFormat = reportFormat;
        this.reportName = reportName;
        this.filename = filename;
    }


    public byte[] getReportData() {
        return this.reportData;
    }


    public String getReportTypeName() {
        return this.reportName;
    }


    public ReportFormat getReportFormat() {
        return this.repotFormat;
    }


    public String getFilename() {
        return this.filename;
    }


    public Long getId() {
        throw new UnsupportedOperationException("Not supported.");
    }


    public byte[] asBytes() {
        return getReportData();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BusinessReport that = (BusinessReport) o;
        return reportName.equals(that.reportName);
    }


    public int hashCode() {
        return Objects.hash(super.hashCode(), reportName);
    }
}
