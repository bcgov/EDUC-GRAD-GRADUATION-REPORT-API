package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Assessment implements Serializable {

    private static final long serialVersionUID = 2L;

    private LocalDate issueDate;
    private List<AssessmentResult> results;

    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate value) {
        this.issueDate = value;
    }

    @JsonProperty("results")
    public List<AssessmentResult> getResults() {
        return results;
    }

    public void setResults(List<AssessmentResult> value) {
        this.results = value;
    }
}
