package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.ReportFormat;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.Report;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.ReportDocument;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.JasperReportImpl;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.ReportDocumentImpl;

import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractReportService {

    /**
     * Creates a JasperReport instance and returns the report document generated
     * by filling and exporting.
     *
     * @return A non-null report document in the format requested by the report
     * parameter.
     *
     * @throws IOException Could not read resources required for filling the
     * report (e.g., resource bundle or report template).
     * @param report The report to fill and export.
     */
    public ReportDocument export(final Report report) throws IOException {
        final JasperReportImpl jasperReport = createJasperReportImpl(report);
        ReportDocument result = jasperReport.export();

        if (report.isFormat(ReportFormat.HTML)) {
            result = postprocess(result);
        }

        return result;
    }

    /**
     * Provides subclasses the opportunity to change the HTML before it is
     * presented to the end user. By default this replaces the fixed-width
     * inline style for the jrPage table element with a 100% width.
     *
     * @param report The HTML report document to process.
     * @return The HTML report document after processing.
     */
    private ReportDocument postprocess(final ReportDocument report) throws IOException {
        final byte[] bytes = report.asBytes();
        final String result = new String(bytes, UTF_8);

        // Match any fixed-width style and convert to 100%. This ensures that
        // HTML reports expand to fill their container element.
        final String replaced = result.replaceFirst("width: .*p.;", "width: 100%;");

        return new ReportDocumentImpl(replaced.getBytes(UTF_8));
    }

    /**
     * Subclasses can override this to provide a different type of report
     * implementation for Jasper.
     *
     * @param report
     * @return
     */
    protected JasperReportImpl createJasperReportImpl(final Report report) {
        final JasperReportImpl jasperReport = new JasperReportImpl(report);
        return jasperReport;
    }
}
