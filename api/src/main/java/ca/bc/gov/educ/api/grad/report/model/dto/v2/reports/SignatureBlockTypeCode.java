package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class SignatureBlockTypeCode extends BaseEntity implements SignatureBlockType {

    private static final long serialVersionUID = 1L;

    private String signatureBlockTypeCode;
    private String label;
    private String description;


    @Override
    public String getCode() {
        return signatureBlockTypeCode;
    }

    @Override
    public String toString() {
        return StringUtils.replace(label, ",", ",\n");
    }
}
