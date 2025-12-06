package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.io.Serializable;

public interface ReportDocument extends Serializable {

    byte[] asBytes();
}
