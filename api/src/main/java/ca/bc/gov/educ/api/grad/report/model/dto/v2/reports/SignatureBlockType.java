package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.io.Serializable;

public interface SignatureBlockType extends Serializable {

    String getCode();

    String getLabel();

    String getDescription();

    void setLabel(String label);

    void setDescription(String description);
}
