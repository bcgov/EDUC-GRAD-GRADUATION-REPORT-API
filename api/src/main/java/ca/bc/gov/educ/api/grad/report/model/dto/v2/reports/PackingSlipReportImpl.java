package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.DestinationType;
import ca.bc.gov.educ.api.grad.report.reporting.adapter.BusinessEntityAdapter;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.ReportImpl;

public final class PackingSlipReportImpl extends ReportImpl implements PackingSlipReport {

    private static final long serialVersionUID = 3L;

    public final static String REPORT_NAME = "PackingSlip";

    /**
     * Information to include on the packing slip.
     */
    private ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.PackingSlipDetails packingSlipDetails;

    /**
     * Constructs a new report using the default report template for packing
     * slips.
     */
    public PackingSlipReportImpl() {
        super(REPORT_NAME);
    }

    /**
     * Returns the main object used to fill the report.
     *
     * @return this.packingSlipDetails
     */
    @Override
    public Object getDataSource() {
        return this.packingSlipDetails;
    }

    @Override
    public boolean isPreview() {
        return false;
    }

    /**
     * Sets the information to write on the packing slip.
     *
     * @param details Mailing address and contents overview details.
     */
    @Override
    public void setPackingSlipDetails(final PackingSlipDetails details) {
        this.packingSlipDetails = BusinessEntityAdapter.adapt(details);
    }

    /**
     * Sets the order type so that the report can determine what fields to
     * include, and whether to add a blank page. Delegates to the packing slip
     * details instance.
     *
     * @param orderType Either TRANSCRIPT or CERTIFICATE.
     * @throws NullPointerException if the order type is null.
     */
    @Override
    public void setOrderType(final OrderType orderType) {
        if (orderType == null) {
            throw new NullPointerException("Order type is null (must be transcript or certificate).");
        }

        getPackingSlipDetails().setOrderTypeName(orderType.getName());
    }

    /**
     * Sets the name of the destination for where the print materials are slated
     * to be delivered. This will be either a student or PSI. Delegates to the
     * packing slip details instance.
     *
     * @param destinationType PSI or null.
     */
    @Override
    public void setDestinationType(final DestinationType destinationType) {
        if (destinationType != null) {
            getPackingSlipDetails().setDestinationType(
                    destinationType);
        }
    }

    /**
     * Returns a filename suitable for saving this report. Builds up a string
     * based on the packing slip details settings.
     *
     * @return A non-null file instance.
     */
    @Override
    public String getFilename() {
        final StringBuilder result = new StringBuilder(128);
        result.append(getName());

        String s = getPackingSlipDetails().getOrderTypeName();
        result.append('-').append(s);

        s = getPackingSlipDetails().getDestinationType().name();
        result.append('-').append(s);
        result.append(getFilenameExtension());

        return result.toString();
    }

    /**
     * Returns the packing slip details to use for populating the report.
     *
     * @return A non-null instance.
     */
    private ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.PackingSlipDetails getPackingSlipDetails() {
        return this.packingSlipDetails;
    }
}

