package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface CanadianPostalAddress extends PostalAddress {

    /**
     * Returns the two-letter province code, uppercase, with no spaces.
     *
     * @return Example, "BC"
     */
    String getProvince();
}
