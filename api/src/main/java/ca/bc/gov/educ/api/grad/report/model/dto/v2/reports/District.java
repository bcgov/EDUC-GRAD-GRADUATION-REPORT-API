package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface District {

    /**
     * Gets distno.
     *
     * @return the distno
     */
    String getDistno();

    /**
     * Returns the postal mailing address for this school.
     *
     * @return An address for sending packages to a school.
     */
    PostalAddress getAddress();

    /**
     * Gets district name.
     *
     * @return the distno
     */
    String getName();

    /**
     * Gets distno.
     *
     * @return the distno
     */
    String getDistrictNumber();

    /**
     * Gets district name.
     *
     * @return the distno
     */
    String getDistrictName();

}
