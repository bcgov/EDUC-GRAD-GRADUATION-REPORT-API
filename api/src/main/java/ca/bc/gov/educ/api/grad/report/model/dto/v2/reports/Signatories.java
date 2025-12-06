package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.ByteArrayInputStream;

/**
 * Represents a set of signatures passed into each certificate report. The
 * signatures for the Minister of Education (MoE) and Minister of Advanced
 * Education (MoAE) are usually fixed while the schoolSignatory signatory
 * (schoolSignatory) varies with the Ministry School code. The signature set
 * contains filenames without filename extensions.
 *
 * @author CGI Information Management Consultants Inc.
 */
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
