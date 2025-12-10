package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.*;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl.*;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.JasperReportImpl;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.ParameterPredicateImpl;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.ReportDocumentImpl;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.TranscriptJasperReportImpl;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
@Service
public class ReportServiceImpl extends AbstractReportService implements ReportService {

    private static final String CLASSNAME = ReportServiceImpl.class.getName();
    private static transient final Logger LOG = Logger.getLogger(CLASSNAME);

    private static final long serialVersionUID = 2L;
    private final GradReportSignatureService gradReportSignatureService;

    public ReportServiceImpl(GradReportSignatureService gradReportSignatureService) {
        this.gradReportSignatureService = gradReportSignatureService;
    }
    /**
     * @throws IOException Could not read resources required for filling the
     * report (e.g., resource bundle or report template).
     * @param report The report to fill and export.
     */
    @Override
    @PermitAll
    public ReportDocument export(final Report report) {
        return super.export(report);
    }

    /**
     * @inheritDoc
     */
    @Override
    public TranscriptReport createTranscriptReport(TranscriptTypeCode transcriptTypeCode, GradProgram program) {
        return new TranscriptReportImpl(transcriptTypeCode, program, gradReportSignatureService);
    }

    /**
     * @inheritDoc
     */
    @Override
    public CertificateReport createCertificateReport() {
        return new CertificateReportImpl(gradReportSignatureService);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraduationReport createSchoolDistributionReport() {
        return new SchoolGraduationReportImpl("SchoolDistribution");
    }

    @Override

    public GraduationReport createDistrictDistributionYearEndCredentialsReport() {
        return new SchoolGraduationReportImpl("DistrictDistYearEndCred");
    }

    @Override

    public GraduationReport createDistrictDistributionYearEndNonGradCredentialsReport() {
        return new SchoolGraduationReportImpl("DistrictDistYearEndNonGradCred");
    }

    @Override

    public GraduationReport createSchoolDistributionYearEndNewCredentialsReport() {
        return new SchoolGraduationReportImpl("SchoolDistYearEndCred");
    }

    @Override

    public GraduationReport createSchoolDistributionYearEndIssuedTranscriptsReport() {
        return new SchoolGraduationReportImpl("SchoolDistYearEndTran");
    }

    @Override
    public GraduationReport createSchoolLabelReport() {
        return new SchoolGraduationReportImpl("SchoolLabel");
    }

    /**
     * @inheritDoc
     */
    @Override

    public GraduationReport createSchoolGraduationReport() {
        return new SchoolGraduationReportImpl("SchoolGraduation");
    }

    @Override

    public GraduationReport createSchoolNonGraduationReport() {
        return new SchoolNonGraduationReportImpl("SchoolNonGraduation");
    }

    @Override
    public GraduationReport createStudentNonGradProjectedReport() {
        return new StudentNonGradProjectedReportImpl();
    }

    @Override
    public GraduationReport createStudentNonGradReport() {
        return new StudentNonGradReportImpl();
    }

    /**
     * @inheritDoc
     */
    @Override
    public ReportDocument createReportDocument(final byte[] bytes) {
        return new ReportDocumentImpl(bytes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ParameterPredicate createParameterPredicate() {
        final String methodName = "createParameterPredicate()";


        final ParameterPredicate pp = new ParameterPredicateImpl();


        return pp;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Parameters<String, Object> createParameters() {
        final String methodName = "createParameters()";

        final Parameters<String, Object> parameters = new LinkedParameters<>();
        
        return parameters;
    }

    @Override
    public AchievementReport createAchievementReport() {
        return new AchievementReportImpl();
    }

    @Override
    public AchievementReport createAchievementReport(String reportName) {
        return new AchievementReportImpl(reportName);
    }

    /**
     * Based on the type of report, this will return an internal representation
     * of the given report instance that can use JasperReports without exposing
     * said library to the calling client. (Prevents marshaling of JasperReports
     * library classes, which won't be found on the client side.)
     *
     * @param report The report to coerce to a JasperReport implementation.
     * @return The given report coerced into a corresponding JasperReport
     * implementation (e.g., a TranscriptReport becomes a
     * TranscriptJasperReportImpl).
     */
    @Override
    protected JasperReportImpl createJasperReportImpl(final Report report) {
        final JasperReportImpl result;

        if (report instanceof TranscriptReport) {
            result = new TranscriptJasperReportImpl(report);
        } else {
            result = new JasperReportImpl(report);
        }

        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public PackingSlipReport createPackingSlipReport() {
        return new PackingSlipReportImpl();
    }
}
