package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;


import ca.bc.gov.educ.api.grad.report.constants.PaperType;

import java.io.Serializable;

public interface OrderType extends Serializable {

    /**
     * Returns the name of the order type suitable for viewing by humans. The
     * return value is displayed on the packing slips verbatim.
     *
     * @return A human-readable text string ("Certificates" or "Transcripts").
     */
    String getName();

    /**
     * Returns the media colour defined by BC Mail Plus, which will vary
     * depending on the type of item being printed.
     *
     * @return A non-null, non-empty string.
     */
    String getMediaColour();

    /**
     * Returns the media type defined by BC Mail Plus, which will vary depending
     * on the type of item being printed.
     *
     * @return A non-null, non-empty string.
     */
    String getMediaType();


    /**
     * Gets paper type.
     *
     * @return the paper type
     */
    PaperType getPaperType();
}
