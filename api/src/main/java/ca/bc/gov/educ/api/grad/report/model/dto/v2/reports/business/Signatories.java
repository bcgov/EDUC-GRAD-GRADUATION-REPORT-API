package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.BusinessEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.ByteArrayInputStream;

@EqualsAndHashCode(callSuper = true)
@Data
public final class Signatories extends BusinessEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Filename for the signature of the School Superintendent / Principal /
     * Inspector. By default, the "independent" signature is used.
     */
    private ByteArrayInputStream schoolSignatory;
    private ByteArrayInputStream ministerOfEducation;
    private ByteArrayInputStream ministerOfAdvancedEducation;
    private ByteArrayInputStream assistantDeputyMinister;
}
