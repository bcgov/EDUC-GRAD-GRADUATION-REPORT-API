package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class Certificate implements Serializable {
    private static final long serialVersionUID = 2L;

    private LocalDate issued;
    private String certStyle;
    private OrderType orderType;
    private boolean isOrigin;

    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public LocalDate getIssued() {
        return issued;
    }

    public void setIssued(LocalDate value) {
        this.issued = value;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType value) {
        this.orderType = value;
    }

    public boolean getIsOrigin() {
        return isOrigin;
    }

    public void setIsOrigin(boolean origin) {
        isOrigin = origin;
    }

    public String getCertStyle() {
        return certStyle;
    }

    public void setCertStyle(String certStyle) {
        this.certStyle = certStyle;
    }
}
