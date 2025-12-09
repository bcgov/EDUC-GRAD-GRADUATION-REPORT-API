package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;
import ca.bc.gov.educ.api.grad.report.util.TranscriptResultListDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Transcript implements Serializable {

    private String interim = "";
    private Date issueDate;
    private Code transcriptTypeCode = new Code(TranscriptTypeCode.NOPROG.getCode());
    private List<TranscriptResult> results = new ArrayList<>();

    public String getInterim() {
        return interim;
    }

    public void setInterim(String value) {
        this.interim = value;
    }

    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date value) {
        this.issueDate = value;
    }

    @JsonProperty("results")
    @JsonDeserialize(using = TranscriptResultListDeserializer.class)
    public List<TranscriptResult> getResults() {
        return results;
    }

    public void setResults(List<TranscriptResult> value) {
        this.results = value;
    }

    @JsonProperty("code")
    @JsonDeserialize(as = Code.class)
    public Code getTranscriptTypeCode() {
        return transcriptTypeCode;
    }

    public void setTranscriptTypeCode(Code transcriptTypeCode) {
        this.transcriptTypeCode = transcriptTypeCode;
    }
}
