package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AbstractDomainEntity;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.NonGradReason;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class NonGradReasonImpl extends AbstractDomainEntity
        implements NonGradReason, Serializable {

    private static final long serialVersionUID = 3L;

    private String code;
    private String description;

    public NonGradReasonImpl() {}

    @Override
    public String getCode() {
        return StringUtils.startsWith(code, "!") ? "" : code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
