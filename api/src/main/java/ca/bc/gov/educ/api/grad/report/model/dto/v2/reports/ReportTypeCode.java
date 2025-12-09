package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class ReportTypeCode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String reportTypeCode;
    private String label;
    private String description;
    private String displayOrder;
    private Date effectiveDate;
    private Date expiryDate;

}
