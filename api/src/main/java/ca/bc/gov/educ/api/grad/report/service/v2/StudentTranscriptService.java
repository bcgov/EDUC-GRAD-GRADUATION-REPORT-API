package ca.bc.gov.educ.api.grad.report.service.v2;


import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;
import ca.bc.gov.educ.api.grad.report.constants.ReportFormat;
import ca.bc.gov.educ.api.grad.report.exception.DataException;
import ca.bc.gov.educ.api.grad.report.exception.DomainServiceException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.*;

import java.io.IOException;
import java.util.List;

public interface StudentTranscriptService extends BusinessService {

    StudentTranscriptReport buildOfficialTranscriptReport()
            throws DomainServiceException, IOException, DataException;

    /**
     * Creates the student's unofficial transcript in the selected format
     *
     * @return A filled transcript report suitable for sending to a PSI.
     * @throws DomainServiceException
     * @throws IOException
     * @throws DataException
     */
    public StudentTranscriptReport buildUnOfficialTranscriptReport(final ReportFormat format)
            throws DomainServiceException, IOException, DataException;

    /**
     * Retrieves a transcript that contains course results and the report date.
     *
     * @param pen The PEN of the student of which transcript to fetch.
     * @return A transcript for the queried user.
     * @throws DomainServiceException Could not read data from TRAX.
     */
    Transcript getTranscript(String pen) throws DomainServiceException;

    /**
     * Retrieves basic transcript information for the given PEN. This is for
     * determining whether the student with the given PEN has any transcript
     * results and what the issue date is. It does NOT contain the list of
     * transcriptResults and will throw an exception if this method is called.
     *
     *
     * @param pen Student number for which information is desired.
     * @return A lightweight transcript object for the passed PEN.
     * @throws DomainServiceException Could not read data from TRAX.
     */
    Transcript getTranscriptInformation(String pen) throws DomainServiceException;

    /**
     * Retrieves a new instance of Linked Parameters to be used when preserving
     * insertion order of data is required.
     *
     * @return new instance of Linked Parameters.
     */
    Parameters createParameters();

    /**
     * Sorts the transcript results according to business rules determined by
     * graduation program code. The results must be sorted prior to passing them
     * into the report because of the logic the report uses to insert blank rows
     * that delineate different courses.
     *
     * @param transcriptResults The list of results to sort.
     * @param programCode The program code that influences sorting behaviour.
     * @return A sorted list for suitable for the given program code.
     */
    List<TranscriptResult> sort(
            List<TranscriptResult> transcriptResults,
            GraduationProgramCode programCode);
}
