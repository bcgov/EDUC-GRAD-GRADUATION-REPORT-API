
package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.ReportFormat;

public final class StudentTranscriptReport extends BusinessReport {

    private static final long serialVersionUID = 3L;
    
    public StudentTranscriptReport(
            final byte[] reportData,
            final ReportFormat reportFormat,
            final String filename,
            final String reportName) {
        super(reportData, reportFormat, filename, reportName);
    }
}
