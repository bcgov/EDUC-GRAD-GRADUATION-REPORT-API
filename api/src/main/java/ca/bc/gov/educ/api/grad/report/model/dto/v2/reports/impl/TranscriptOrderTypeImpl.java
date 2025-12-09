package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;


import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.TranscriptOrderType;

import static ca.bc.gov.educ.api.grad.report.constants.PaperType.TRANSCRIPT;

/**
 * Responsible for creating order types that can print transcripts on the
 * correct paper type.
 *
 * @author CGI Information Management Consultants Inc.
 */
public class TranscriptOrderTypeImpl extends OrderTypeImpl
        implements TranscriptOrderType {

    private static final long serialVersionUID = 3L;

    private String name;

    /**
     * Constructs with paper type YED4.
     */
    public TranscriptOrderTypeImpl() {
        setPaperType(TRANSCRIPT);
    }

    /**
     * Returns the human-readable name for transcripts.
     *
     * @return "Transcripts"
     */
    @Override
    public String getName() {
        return "Transcripts";
    }

    public void setName(String name) {
        this.name = name;
    }
}
