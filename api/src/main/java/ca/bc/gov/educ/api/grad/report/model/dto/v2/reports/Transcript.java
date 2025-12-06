package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;
import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A container for a list of transcript courses associated with a student.
 *
 * @author CGI Information Management Consultants Inc.
 */

public class Transcript extends AbstractDomainEntity implements Serializable {

    private static final long serialVersionUID = 3L;

    private boolean interim;
    private boolean blank;
    private Date issueDate;
    private TranscriptTypeCode transcriptTypeCode;
    private List<TranscriptResult> results = new ArrayList<>();
    
    public List<TranscriptResult> getResults() {
        return this.results;
    }


    @JsonIgnore
    public List<TranscriptResult> getResults(final GraduationProgramCode code) {
        return getResults();
    }
    
    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public Date getIssueDate() {
        return this.issueDate;
    }

    public boolean isEmpty() {
        return getResults().isEmpty();
    }

    /**
     *
     * @param issueDate
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Set an entire collection of transcript results.
     *
     * @param results
     */
    public void setResults(List<TranscriptResult> results) {
        // Prevents resetting the list to empty.
        if (results != null && !results.isEmpty()) {
            this.results = results;
        }
    }

    /**
     * Add an individual course to the transcript results list.
     *
     * @param result A transcript result to add to the list.
     */
    public void addResult(TranscriptResult result) {
        if (result != null) {
            getResults().add(result);
        }
    } 
    
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setInterim(boolean interim) {
        this.interim = interim;
    }
    
    public boolean getInterim() {
        return this.interim;
    }
    
    public boolean getBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }
    
    public TranscriptTypeCode getTranscriptTypeCode() {
        return transcriptTypeCode;
    }

    public void setTranscriptTypeCode(TranscriptTypeCode transcriptTypeCode) {
        this.transcriptTypeCode = transcriptTypeCode;
    }
}
