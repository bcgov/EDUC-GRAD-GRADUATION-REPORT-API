package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.constants.PaperType;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.OrderType;

public abstract class OrderTypeImpl implements OrderType {

    private static final long serialVersionUID = 4L;

    /**
     * Paper used for printing.
     */
    private PaperType paperType;

    /**
     * Returns a value used for both media type and media colour.
     *
     * @return A non-null, non-empty string.
     */
    public PaperType getPaperType() {
        return this.paperType;
    }

    /**
     * Sets paper type used for printing this order.
     *
     * @param paperType The paper used for printing the order.
     */
    public void setPaperType(final PaperType paperType) {
        this.paperType = paperType;
    }

    /**
     * Returns the paper's media type to use for setting the print instructions.
     *
     * @return A non-null string, never empty.
     */
    @Override
    public String getMediaType() {
        return getPaperType().toString();
    }

    /**
     * Returns the paper's colour type to use for setting the print
     * instructions.
     *
     * @return A non-null string, never empty.
     */
    @Override
    public String getMediaColour() {
        return getPaperType().toString();
    }
}
