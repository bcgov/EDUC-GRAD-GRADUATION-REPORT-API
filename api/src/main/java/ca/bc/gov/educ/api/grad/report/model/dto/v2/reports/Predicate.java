package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.util.Collection;

/**
 * Defines a method that can evaluate whether a given parameter passes a test.
 * The test is defined in classes that implement this interface.
 *
 * @author CGI Information Management Consultants Inc.
 * @param <C> The type of collection to filter.
 */
public interface Predicate<C> {

    /**
     * Use the specified parameter to perform a test that returns true or false.
     *
     * @param c The collection element to evaluate, should not be changed.
     * @return true The object has passed the test.
     */
    public boolean evaluate(C c);

    /**
     * Filters the given collection without modification.
     *
     * @param target The collection to filter.
     * @return The given collection after the filter has been applied.
     */
    public Collection<C> filter(Collection<C> target);
}
