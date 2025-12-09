package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface MailingAddress {

    /**
     * Used to terminate lines for formatted addresses.
     */
    String LINE_SEPARATOR = "\n";

    /**
     * Line 1 of the street address.
     *
     * @return A non-null String instance, should not be empty.
     */
    String getStreetLine1();

    /**
     * Line 2 of the street address.
     *
     * @return A non-null String instance, possibly empty.
     */
    String getStreetLine2();

    /**
     * Line 3 of the street address.
     *
     * @return A non-null String instance, possibly empty.
     */
    String getStreetLine3();

    /**
     * City name.
     *
     * @return A non-null String instance, should not be empty.
     */
    String getCity();

    /**
     * Province, State, or other region.
     *
     * @return A non-null String instance, might be empty.
     */
    String getRegion();

    /**
     * Destination country.
     *
     * @return A non-null String instance, might be empty.
     */
    String getCountryCode();

    /**
     * Returns the postal code (zip code, postcode, etc.).
     *
     * @return A mostly unique identifier for the address.
     */
    String getPostalCode();

    /**
     * Returns the formatted address depending on country formats.
     *
     * @return formatted country specific address
     */
    String getFormattedAddressForLabels();
}
