package ca.bc.gov.educ.api.grad.report.reporting.jasper.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.Predicate;
import net.sf.jasperreports.export.SimpleExporterConfiguration;

public class XmlExporterConfiguration extends SimpleExporterConfiguration {

    private Predicate<String> predicate;

    public XmlExporterConfiguration(final Predicate<String> predicate) {
        this.predicate = predicate;
    }

    public Predicate<String> getPredicate() {
        return this.predicate;
    }

    public void setPredicate(final Predicate<String> predicate) {
        this.predicate = predicate;
    }
}
