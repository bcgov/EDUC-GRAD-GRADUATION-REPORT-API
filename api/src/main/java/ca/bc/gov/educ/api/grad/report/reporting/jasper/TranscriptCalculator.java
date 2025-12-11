package ca.bc.gov.educ.api.grad.report.reporting.jasper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class TranscriptCalculator {

    private static final String CLASSNAME = TranscriptCalculator.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASSNAME);

    /**
     * Helper method used by unit tests.
     *
     * @param rows The number of rows to display on a transcript.
     * @param page The page number that requires a page break.
     * @return The number of rows to display on the given page.
     */
    public static int calcPageBreak(final int rows, final int page) {
        return calcPageBreak(rows, page, 23, 30);
    }

    /**
     * Calculates where to put a page break in the student transcript.
     *
     * @param rows Total number of rows to display on a transcript.
     * @param page The page number that requires a page break.
     * @param shortPage The number of rows on a short page (i.e., a page with
     * non-graduation message text).
     * @param longPage The number of rows on a long page (i.e., has no
     * non-graduation message text).
     * @return The number of rows to display on the given page prior to
     * inserting a page break.
     */
    public static int calcPageBreak(
            int rows,
            final int page,
            final int shortPage,
            final int longPage) {
        final int pages = (int) Math.ceil((rows - shortPage) / (double) longPage) + 1;
        final int pageBreaks[] = new int[pages];

        for (int i = 0; i < pages; i++) {
            if (rows > longPage) {
                rows -= pageBreaks[i] = longPage;
            } else if (rows > shortPage) {
                rows -= pageBreaks[i] = rows - 1;
            } else {
                pageBreaks[i] = rows + 1;
            }
        }

        int pageBreak = Integer.MAX_VALUE;

        try {
            pageBreak = pageBreaks[page - 1];
        } catch (final Exception ex) {
            final String msg = format("calcPageBreak( %d, %d, %d, %d )",
                    rows, page, shortPage, longPage);
            LOG.log(Level.FINE, msg);
        }

        return pageBreak;
    }
}
