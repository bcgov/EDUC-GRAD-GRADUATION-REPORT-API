package ca.bc.gov.educ.api.grad.report.reporting.jasper.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.ReportDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportDocumentImpl implements ReportDocument {

    private static final long serialVersionUID = 1L;

    /**
     * Data that constitutes the filled report.
     */
    private final byte[] reportContent;

    /**
     * Constructs a new instance with a set of bytes that contains the report's
     * data in its final output format.
     *
     * @param content The report content.
     */
    public ReportDocumentImpl(final byte[] content) {
        this.reportContent = content;
    }

    /**
     * Returns the final, filled report as a format- and engine-agnostic series
     * of bytes.
     *
     * @return Bytes that represent the final, filled report, never null,
     * possibly empty.
     */
    @Override
    public byte[] asBytes() {
        return this.reportContent == null ? new byte[0] : this.reportContent;
    }

    /**
     * Helper method to save the document to a file. This will overwrite the
     * given file.
     *
     * @param file The path and filename to contain the report document content.
     * @throws IOException Could not write the report document.
     */
    public void save(final File file) throws IOException {
        // Write the bytes and close the stream on either success or error.
        try (final FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(reportContent);
        }
    }
}
