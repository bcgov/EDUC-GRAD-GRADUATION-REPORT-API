package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public abstract class AbstractPredicate<C> implements Predicate<C>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<C> filter(final Collection<C> target) {
        final Collection<C> result = createCollection(target.size());

        for (final C element : target) {
            if (evaluate(element)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Creates the collection wherein the filtered items are placed with a
     * default capacity of 16 elements.
     *
     * @return An empty array list by default.
     */
    protected Collection<C> createCollection() {
        return createCollection(16);
    }

    /**
     * Creates the collection wherein the filtered items are placed.
     *
     * @param size The initial capacity.
     * @return An empty array list by default.
     */
    protected Collection<C> createCollection(final int size) {
        return new ArrayList<>(size);
    }
}
