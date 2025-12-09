package ca.bc.gov.educ.api.grad.report.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum PaperType {

    CERTIFICATE_SC("YED2"),
    CERTIFICATE_SCI("YED2"),
    CERTIFICATE_A("YEDB"),
    CERTIFICATE_AI("YEDB"),
    CERTIFICATE_EI("YEDR"),
    CERTIFICATE_E("YEDR"),
    CERTIFICATE_S("YEDR"),
    CERTIFICATE_SCF("YED2"),
    CERTIFICATE_F("YEDR"),
    CERTIFICATE_O("YEDR"),
    CERTIFICATE_FN("YEDR"),
    CERTIFICATE_FNA("YEDB"),
    CERTIFICATE_SCFN("YED2"),
    ACHIEVEMENT("YED4"),
    TRANSCRIPT("YED4"),
    SCHOOL("YED4");

    private final String code;

    /**
     * Sets the code.
     *
     * @param code The paper type code used for media type and media colour.
     */
    private PaperType(final String code) {
        this.code = code;
    }

    /**
     * Returns the media colour used for printing this paper type.
     *
     * @return The paper type code.
     */
    public String getMediaColour() {
        return getCode();
    }

    /**
     * Returns the media type used for printing this paper type.
     *
     * @return The paper type code.
     */
    public String getMediaType() {
        return getCode();
    }

    /**
     * Returns the paper type code, which can be used as the media type and
     * media colour.
     *
     * @return The paper type code that should correspond to either a specific
     * certificate type or a transcript.
     */
    @Override
    public String toString() {
        return getCode();
    }

    /**
     * Returns the code used for printing on this paper type.
     *
     * @return The media and print type code.
     */
    private String getCode() {
        return this.code;
    }

    @JsonCreator
    public static PaperType forValue(@JsonProperty("code") final String code) {
        for (PaperType paperType : PaperType.values()) {
            if (paperType.code.equals(code)) {
                return paperType;
            }
        }
        return null;
    }
}
