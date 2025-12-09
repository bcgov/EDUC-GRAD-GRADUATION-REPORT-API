package ca.bc.gov.educ.api.grad.report.reporting.jasper.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AbstractPredicate;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.ParameterPredicate;

public class ParameterPredicateImpl extends AbstractPredicate<String> implements ParameterPredicate {
    private String key;

    public ParameterPredicateImpl() {};

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
    
    @Override
    public boolean evaluate(final String c) {
        return c.startsWith(getKey());
    }
}
