package ca.bc.gov.educ.api.grad.report.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum CertificateType {

    A("A", PaperType.CERTIFICATE_A),
    AI("AI", PaperType.CERTIFICATE_AI),
    EI("EI", PaperType.CERTIFICATE_EI),
    SC("SC", PaperType.CERTIFICATE_SC),
    SCI("SCI", PaperType.CERTIFICATE_SCI),
    SCF("SCF", PaperType.CERTIFICATE_SCF),
    E("E", PaperType.CERTIFICATE_E),
    S("S", PaperType.CERTIFICATE_S),
    F("F", PaperType.CERTIFICATE_F),
    O("O", PaperType.CERTIFICATE_O),
    FN("FN", PaperType.CERTIFICATE_FN),
    FNA("FNA", PaperType.CERTIFICATE_FNA),
    SCFN("SCFN", PaperType.CERTIFICATE_SCFN),
    ;

    private String reportName;
    private PaperType paperType;

    CertificateType() {
    }

    /**
     * Constructs a new enumerated type for a certificate.
     *
     * @param reportName Certificate subreport template filename.
     */
    private CertificateType(final String reportName, final PaperType paperType) {
        this.reportName = reportName;
        this.paperType = paperType;
    }

    /**
     * Returns the paper type required for printing the certificate.
     *
     * @return A non-null instance for controlling a Xerox printer.
     */
    public PaperType getPaperType() {
        return this.paperType;
    }

    /**
     * Returns the file name for this certificate report type.
     *
     * @return The report file name.
     */
    @Override
    public String toString() {
        return this.reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    @JsonCreator
    public static CertificateType forValue(@JsonProperty("reportName") final String reportName) {
        for (CertificateType certificateType : CertificateType.values()) {
            if (certificateType.getReportName().equals(reportName)) {
                return certificateType;
            }
        }
        return null;
    }
}
