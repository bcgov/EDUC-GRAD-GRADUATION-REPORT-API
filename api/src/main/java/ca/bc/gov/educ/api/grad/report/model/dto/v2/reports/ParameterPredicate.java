package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface ParameterPredicate extends Predicate<String> {

    /**
     * TODO: How is this used?
     *
     * @param key Comment constraints and expected values.
     */
    void setKey(String key);
}
