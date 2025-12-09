package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.util.concurrent.ConcurrentHashMap;

public class ParametersImpl<K, V>
        extends ConcurrentHashMap<K, V> implements Parameters<K, V> {

    private static final long serialVersionUID = -6543917105276076522L;
}
