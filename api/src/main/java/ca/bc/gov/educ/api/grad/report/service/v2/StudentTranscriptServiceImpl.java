package ca.bc.gov.educ.api.grad.report.service.v2;


import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;
import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.constants.ReportFormat;
import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;
import ca.bc.gov.educ.api.grad.report.exception.DataException;
import ca.bc.gov.educ.api.grad.report.exception.DomainServiceException;
import ca.bc.gov.educ.api.grad.report.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.*;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GraduationData;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client.GradSearchStudent;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client.NonGradReason;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client.ReportData;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl.*;
import ca.bc.gov.educ.api.grad.report.model.entity.ProgramCertificateTranscriptEntity;
import ca.bc.gov.educ.api.grad.report.repository.ProgramCertificateTranscriptRepository;
import ca.bc.gov.educ.api.grad.report.repository.StudentTranscriptRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.Serial;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ca.bc.gov.educ.api.grad.report.constants.ReportCourseType.ASSESSMENT;
import static ca.bc.gov.educ.api.grad.report.constants.ReportCourseType.PROVINCIALLY_EXAMINABLE;
import static ca.bc.gov.educ.api.grad.report.constants.RequirementNames.getName;
import static ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.BusinessEntity.nullSafe;
import static java.text.NumberFormat.getIntegerInstance;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;


@Slf4j
@Service
public class StudentTranscriptServiceImpl extends GradReportService {

    @Serial
    private static final long serialVersionUID = 5L;

    private static final String CLASSNAME = StudentTranscriptService.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASSNAME);
    @Autowired
    ReportApiConstants reportApiConstants;
    @Autowired
    @Qualifier("gradReportEducStudentApiClient")
    WebClient educStudentWebClient;
    /**
     * Sort order for ungraded courses (to bottom, above assessments).
     */
    private static final String SORT_UNGRADED = "99";

    /**
     * Sort order for assessments (to bottom, below ungraded).
     */
    private static final String SORT_ASSESSMENT = "100";

    private final ProgramCertificateTranscriptRepository programCertificateTranscriptRepository;

    private final StudentTranscriptRepository studentTranscriptRepository;

    @Autowired
    public StudentTranscriptServiceImpl(ProgramCertificateTranscriptRepository programCertificateTranscriptRepository, StudentTranscriptRepository studentTranscriptRepository) {
        this.programCertificateTranscriptRepository = programCertificateTranscriptRepository;
        this.studentTranscriptRepository = studentTranscriptRepository;
    }
    
    public StudentTranscriptReport buildOfficialTranscriptReport() throws IOException {
        return createTranscriptReport(ReportFormat.PDF, false);
    }


    public StudentTranscriptReport buildUnOfficialTranscriptReport(final ReportFormat format) throws IOException {
        return createTranscriptReport(format, true);
    }

    public Transcript getTranscript(final String pen) {
        final Transcript transcriptInfo = getTranscriptInformation(pen);
        final StudentInfo studentInfo = getStudentInfo(pen);
        final TranscriptTypeCode transcriptTypeCode = transcriptInfo.getTranscriptTypeCode();
        final GradProgram program = createGradProgram(studentInfo.getGradReqYear());
        final List<TranscriptCourse> transcriptCourses = getTranscriptCourseList(pen, transcriptInfo.getInterim(), StringUtils.isBlank(program.getExpiryDate()));
        final Date reportDate = transcriptInfo.getIssueDate();

        final Transcript transcript = adapt(
                transcriptTypeCode,
                program.getCode(),
                transcriptCourses,
                reportDate,
                transcriptInfo.getInterim()
        );

        return transcript;
    }
    
    public Transcript getTranscriptInformation(final String pen) {
        final String methodName = "getTranscriptInformation(String)";

        final Transcript transcript;

        try {
            ReportData reportData = getReportData(methodName);
            transcript = gradDataConvertionBean.getTranscript(reportData);

        } catch (Exception ex) {
            String msg = "Failed to access transcript data for student with PEN: ".concat(pen);
            final DataException dex = new DataException(null, null, msg, ex);
            log.error(msg, dex);
            throw dex;
        }

        return transcript;
    }

    private GradProgram createGradProgram(String code) {
        final String methodName = "createGradProgram(String)";

        final GradProgram gradProgram;

        try {
            ReportData reportData = getReportData(methodName);
            if (reportData.getGradProgram() == null || reportData.getGradProgram().getCode() == null) {
                EntityNotFoundException dse = new EntityNotFoundException(
                        getClass(),
                        "GRAD_PROGRAM_MISSING",
                        "Grad Program or Grad Program Code is null");
                log.error(methodName, dse);
                throw dse;
            }

            gradProgram = gradDataConvertionBean.getGradProgram(reportData);

        } catch (Exception ex) {
            String msg = "Failed to get grad program : ".concat(code);
            final DataException dex = new DataException(null, null, msg, ex);
            log.error(methodName, dex);
            throw dex;
        }

        return gradProgram;
    }


    private StudentTranscriptReport createTranscriptReport(final ReportFormat format, final boolean preview) {
        final PersonalEducationNumber pen = getStudentPEN();
        final StudentTranscriptReport report = getStudentTranscriptReport(
                pen, format, preview, null
        );
        return report;
    }

    private List<TranscriptCourse> getTranscriptCourseList(final String pen, final boolean interim, final boolean openGradProgram) {
        final String methodName = "getTranscriptCourseList(String, boolean)";

        final List<TranscriptCourse> results;

        ReportData reportData = getReportData(methodName);
        if(interim) {
            results = filterCourses(gradDataConvertionBean.getTranscriptCourses(reportData), openGradProgram);
        } else {
            results = gradDataConvertionBean.getTranscriptCourses(reportData);
        }

        LOG.log(Level.FINE,
                "Retrieved the collection of exam results for PEN: {0} INTERIM: {1}",
                new Object[]{pen, interim});

        if (results != null && !results.isEmpty()) {
            LOG.log(Level.FINE,
                    "Total courses {0} retrieved  for PEN: {1}",
                    new Object[]{results.size(), pen});
            LOG.log(Level.FINEST, "Retrieved student transcript course results:");
            for (TranscriptCourse result : results) {
                LOG.log(Level.FINEST, "{0} {1}",
                        new Object[]{result.getCourseName(), result.getFinalLetterGrade()});
            }
        }

        LOG.log(Level.FINE, "Completed call to TRAX.");
        return results;
    }

    private Transcript adapt(
            final TranscriptTypeCode transcriptTypeCode,
            final GraduationProgramCode graduationProgramCode,
            final List<TranscriptCourse> transcriptCourses,
            final Date issueDate,
            final boolean interim) {
        final List<TranscriptResult> transcriptResults = adapt(
                graduationProgramCode, transcriptCourses);

        final TranscriptImpl transcript = new TranscriptImpl();
        transcript.setTranscriptTypeCode(transcriptTypeCode);
        transcript.setIssueDate(issueDate);
        transcript.setResults(transcriptResults);
        transcript.setInterim(interim);

        return transcript;
    }


    private GradProgram adapt(
            final String graduationProgramCode,
            final TranscriptTypeCode transcriptTypeCode) {
        final String m_ = "adapt(schoolCategoryCode, graduationProgramCode,transcriptTypeCode )";

        GradProgram result = createGradProgram(graduationProgramCode);

        if(GraduationProgramCode.PROGRAM_BLANK.getCode().equalsIgnoreCase(graduationProgramCode) && transcriptTypeCode != null) {
            List<ProgramCertificateTranscriptEntity> entities = programCertificateTranscriptRepository.findByTranscriptTypeCode(transcriptTypeCode.getCode());
            if(!entities.isEmpty()) {
                GradProgram gradProgram = getGraduationProgram(entities.get(0).getGraduationProgramCode());
                if(gradProgram != null) {
                    result = gradProgram;
                }
            }
        }

        return result;
    }

    private List<TranscriptResult> adapt(
            final GraduationProgramCode programCode,
            final List<TranscriptCourse> transcriptCourses) {
        List<TranscriptResult> transcriptResults = Collections.emptyList();

        if (transcriptCourses != null) {
            final int size = transcriptCourses.size();
            transcriptResults = new ArrayList<>(size);

            for (final TranscriptCourse transcriptCourse : transcriptCourses) {
                final String courseType = transcriptCourse.getCourseType();

                final String eq = transcriptCourse.getEquivalency();
                final String req = transcriptCourse.getRequirement();
                final String ufg = transcriptCourse.getUsedForGrad();
                final String reqName = getName(req, programCode.toString());
                final TranscriptResultImpl tResult = new TranscriptResultImpl(
                        req, eq, ufg, reqName);

                final String courseName = transcriptCourse.getCourseName();
                final String courseCode = transcriptCourse.getCourseCode();
                final String courseLevel = transcriptCourse.getCourseLevel();
                final String traxCredits = transcriptCourse.getCredits();
                final String relatedCourse = transcriptCourse.getRelatedCourse();
                final String relatedLevel = transcriptCourse.getRelatedLevel();

                final String sessionDate = transcriptCourse.getSessionDate();
                final CourseImpl course = new CourseImpl(
                        courseName, courseCode,
                        courseLevel, traxCredits,
                        sessionDate, courseType,
                        relatedCourse, relatedLevel);

                tResult.setCourse(course);

                String schoolPct = transcriptCourse.getSchoolPercent();
                String examPct = transcriptCourse.getExamPercent();
                if(StringUtils.isNumeric(courseLevel)) {
                    int lv = Integer.parseInt(courseLevel);
                    if(lv < 12) {
                        schoolPct = "";
                        examPct = "";
                    }
                }
                String finalPct = transcriptCourse.getFinalPercent();
                final String finalLetterGrade = transcriptCourse.getFinalLetterGrade();
                final String interimPct = transcriptCourse.getInterimMark();
                final String interimLetterGrade = transcriptCourse.getInterimLetterGrade();

                final MarkImpl mark = new MarkImpl();
                mark.setSchoolPercent(schoolPct);
                mark.setExamPercent(examPct);

                // Marks should only contain interim OR final
                if ("0".equals(finalPct)) {
                    finalPct = "";
                }

                if (finalPct.isEmpty() && finalLetterGrade.isEmpty()) {
                    mark.setInterimLetterGrade(interimLetterGrade);
                    mark.setInterimPercent(interimPct);
                } else {
                    mark.setFinalLetterGrade(finalLetterGrade);
                    mark.setFinalPercent(finalPct);
                }

                tResult.setMark(mark);
                transcriptResults.add(tResult);
            }

            sort(transcriptResults, programCode);
        }

        return transcriptResults;
    }

    /**
     * Convert non-graduation reasons from a TRAX map to an STs list.
     *
     * @param studentInfo The student instance that has non-grad reasons to
     * convert.
     *
     * @return The map of non-grad reasons converted from a map to a list of
     * NonGradReasons instances.
     */
    private List<NonGradReason> adaptReasons(final StudentInfo studentInfo) {
        final String methodName = "adaptReasons(StudentInfo)";

        final Map<String, String> nonGradReasons = studentInfo.getNonGradReasons();
        final List<NonGradReason> result = new ArrayList<>();

        for (Map.Entry<String, String> entry : nonGradReasons.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            NonGradReason r = new NonGradReason();
            r.setCode(key);
            r.setDescription(value);
            result.add(r);
        }

        return result;
    }

    private synchronized StudentTranscriptReport createReport(
            final ReportFormat reportFormat,
            final boolean preview,
            final Student student,
            final School school,
            final String logo,
            final Transcript transcript,
            final GradProgram program,
            final List<NonGradReason> nonGradReasons,
            final String gradMessage,
            final Date issueDate,
            final Parameters<String, Object> parameters,
            final GraduationData graduationData) throws DomainServiceException {
        final TranscriptTypeCode transcriptTypeCode = transcript.getTranscriptTypeCode();

        final TranscriptReport report = reportService.createTranscriptReport(transcriptTypeCode, program);

        if (parameters != null) {
            report.setParameters(parameters);
        }

        // Indicate official/unofficial
        report.setPreview(preview);

        report.setStudent(student);
        report.setSchool(school, logo);
        report.setGraduationProgram(program);
        report.setTranscript(transcript);
        report.setGraduationStatus(nonGradReasons, gradMessage);
        report.setReportDate(issueDate);
        report.setFormat(reportFormat);
        report.setGraduationData(graduationData);

        final boolean interim = transcript.getInterim();
        report.setInterim(interim);
        report.setBlank(StringUtils.isBlank(student.getPen().getPen()));

        ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.Student stu = (ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.business.Student)report.getDataSource();
        LOG.log(Level.FINE, "DataSource Student created {0}.", new Object[]{stu.getPEN()});

        final ReportDocument document = reportService.export(report);
        LOG.log(Level.FINE, "Created document {0} for student {1}.", new Object[]{document, student.getPen()});

        final String filename = report.getFilename();
        final byte[] content = document.asBytes();

        if (isEmpty(content)) {
            final String msg = "The generated report output is empty.";
            DomainServiceException dse = new DomainServiceException(msg);
            log.error(msg, dse);
            throw dse;
        }

        final StudentTranscriptReport transcriptReport = new StudentTranscriptReport(
                content, reportFormat, filename, "Transcript"
        );
        LOG.log(Level.FINE, "Created StudentTranscriptReport {0} for student {1}.", new Object[]{transcriptReport, student.getPen()});


        return transcriptReport;
    }

    public Parameters<String, Object> createParameters() {
        final String methodName = "createParameters()";
        
        Parameters<String, Object> parameters = reportService.createParameters();


        return parameters;
    }

    public GraduationReport createGraduationReport() {
        throw new NotImplementedException("Method createGraduationReport() not implemented");
    }

    public StudentTranscriptReport getStudentTranscriptReport(
            final PersonalEducationNumber personalEducationNumber,
            final ReportFormat format,
            final boolean preview,
            final Parameters<String, Object> parameters) throws DomainServiceException {
        final String pen = personalEducationNumber.getValue();
        final StudentInfo studentInfo = getStudentInfo(pen);

        final String programCode = studentInfo.getGradReqYear();
        final String logo = studentInfo.getLogo();
        final Transcript transcript = getTranscript(pen);

        final Student student = adaptStudent(personalEducationNumber, studentInfo);
        final School school = adaptSchool(studentInfo, true);

        final GradProgram program = adapt(programCode, transcript.getTranscriptTypeCode());

        final GraduationData graduationData = adaptGraduationData(studentInfo, transcript);

        final String gradMessage = studentInfo.getGradMessage();
        final List<NonGradReason> nonGradReasons = adaptReasons(studentInfo);

        Date issueDate = transcript.getIssueDate();

        if(preview && StringUtils.isNotBlank(personalEducationNumber.getEntityId())) {
            LOG.log(Level.FINE, "Preview transcript get Last Update Date");
            UUID graduationStudentRecordId = UUID.fromString(personalEducationNumber.getEntityId());
            Optional<Date> transcriptLastUpdateDate = studentTranscriptRepository.getTranscriptLastUpdateDate(graduationStudentRecordId);
            if(transcriptLastUpdateDate.isPresent()) {
                issueDate = transcriptLastUpdateDate.get();
            }
        }

        final StudentTranscriptReport report = createReport(
                format,
                preview,
                student,
                school,
                logo,
                transcript,
                program,
                nonGradReasons,
                gradMessage,
                issueDate,
                parameters,
                graduationData
        );

        return report;
    }

    private String getCreditsUsedForGrad(final Transcript transcript) {
        final List<TranscriptResult> results = transcript.getResults();
        int totalCredits = 0;

        for (final TranscriptResult transcriptResult : results) {
            final String credits = nullSafe(transcriptResult.getUsedForGrad());
            totalCredits += parseCredits(credits);
        }

        return "" + totalCredits;
    }

    private GraduationData adaptGraduationData(
            final StudentInfo studentInfo,
            final Transcript transcript) {
        final Object[] params = {studentInfo, transcript};
        final GraduationData graduationData = new GraduationDataImpl();

        LocalDate gradDate = studentInfo.getGradDate();

        ((GraduationDataImpl) graduationData).setGraduationDate(gradDate);
        ((GraduationDataImpl) graduationData).setHonorsFlag(studentInfo.isHonourFlag());
        ((GraduationDataImpl) graduationData).setDogwoodFlag(studentInfo.isDogwoodFlag());
        ((GraduationDataImpl) graduationData).setProgramNames(studentInfo.getAcademicProgram());
        final String creditsUsedForGrad = getCreditsUsedForGrad(transcript);
        ((GraduationDataImpl) graduationData).setTotalCreditsUsedForGrad(creditsUsedForGrad);

        return graduationData;
    }

    private List<TranscriptCourse> filterCourses(final List<TranscriptCourse> results, boolean openGradProgram) {

        final List<TranscriptCourse> resultOfCourses = new ArrayList<>();
        for (final TranscriptCourse course : results) {

            if (!resultOfCourses.contains(course)) {
                final TranscriptCourse interimCourse
                        = getInterimCourse(course, results, openGradProgram);

                if (!(resultOfCourses.contains(interimCourse))) {
                    resultOfCourses.add(interimCourse);
                }
            }
        }

        return resultOfCourses;
    }

    private TranscriptCourse getInterimCourse(
            TranscriptCourse course,
            final List<TranscriptCourse> results, boolean openGradProgram) {
        //Check for duplicate courses
        for (final TranscriptCourse compareCourse : results) {
            if (course.equals(compareCourse)) continue;
            //Check and compare two courses for duplication and if required
            //replace course based on requirement.
            if (isInterimCourse(course, compareCourse, openGradProgram)) {
                course = compareCourse;
            }
        }
        return course;

    }

    private boolean isInterimCourse(TranscriptCourse source, TranscriptCourse target, boolean openGradProgram) {
        if (openGradProgram) { // GRAD-2525
            return source.courseEquals(target) && source.compareCourse(target);
        } else { // GRAD2-2222
            return source.courseEquals(target) && !source.isCompletedCourseUsedForGrad() && source.compareCourse(target);
        }
    }
    
    public List<TranscriptResult> sort(
            final List<TranscriptResult> results,
            final GraduationProgramCode code) {

        // Perform multiple stable sorts over the transcript results.
        final Comparator<TranscriptResult> comparator = createComparator(code);
        Collections.sort(results, comparator);

        // Sort the ungraded courses to the end of the list, which will then
        // be pushed up using the assessment comparator.
        final Comparator<TranscriptResult> ungraded = createUngradedComparator();
        Collections.sort(results, ungraded);

        // Sort the ReportCourseType.ASSESSMENT values to the end of the
        // list. This could use a comparator chain.
        final Comparator<TranscriptResult> assessments = createAssessmentComparator();
        Collections.sort(results, assessments);

        return results;
    }

    private Comparator<TranscriptResult> createComparator(
            final GraduationProgramCode code) {
        final Comparator<TranscriptResult> result;

        switch (code) {
            case PROGRAM_1950, PROGRAM_1986:
            // 1995, 2004, 2018, etc.
            default:
                result = createRegularComparator();
                break;
        }

        return result;
    }

    private Comparator<TranscriptResult> createAdultComparator() {
        return (tr1, tr2) -> {
            final String code1 = getCourseCode(tr1);
            final String code2 = getCourseCode(tr2);

            return new CompareToBuilder()
                    .append(code1, code2)
                    .toComparison();
        };
    }

    private Comparator<TranscriptResult> createRegularComparator() {
        return (tr1, tr2) -> {
            final int level1 = getCourseLevel(tr1);
            final int level2 = getCourseLevel(tr2);
            final String name1 = getCourseName(tr1);
            final String name2 = getCourseName(tr2);

            return new CompareToBuilder()
                    .append(level1, level2)
                    .append(name1, name2)
                    .toComparison();
        };
    }

    private Comparator<TranscriptResult> createUngradedComparator() {
        return (tr1, tr2) -> {
            final int level1 = getCourseLevel(tr1);
            final int level2 = getCourseLevel(tr2);

            final int comparison = new CompareToBuilder()
                    .append(level1, level2)
                    .toComparison();

            return SORT_UNGRADED.equals("" + level1)
                    || SORT_UNGRADED.equals("" + level2)
                    ? comparison
                    : 0;

        };
    }

    private Comparator<TranscriptResult> createAssessmentComparator() {
        return (tr1, tr2) -> {
            final int level1 = getCourseLevel(tr1);
            final int level2 = getCourseLevel(tr2);

            final String reportCourseType1 = getReportCourseType(tr1);
            final String reportCourseType2 = getReportCourseType(tr2);

            final String courseCode1 = getCourseCode(tr1);
            final String courseCode2 = getCourseCode(tr2);

            final int comparison = new CompareToBuilder()
                    .append(level1, level2)
                    .append(reportCourseType1, reportCourseType2)
                    .append(courseCode1, courseCode2)
                    .toComparison();

            return ASSESSMENT.isCode(reportCourseType1)
                    || ASSESSMENT.isCode(reportCourseType2)
                            ? comparison
                            : 0;
        };
    }

    private String getReportCourseType(final TranscriptResult tr) {
        final Course c = tr.getCourse();
        return c == null
                ? PROVINCIALLY_EXAMINABLE.getCode()
                : c.getType();
    }

    private int getCourseLevel(final TranscriptResult tr) {
        final Course c = tr.getCourse();
        final String cl = c == null ? "" : c.getLevel().trim();
        final String type = c == null ? "" : c.getType();

        final String level = ASSESSMENT.isCode(type)
                ? SORT_ASSESSMENT
                : SORT_UNGRADED;
        final String courseLevel = cl.isEmpty() ? level : cl;
        int result = 0;

        try {
            // Parse "12A" to 12, "11F" to 11, "10" to 10, for sort purposes.
            final NumberFormat nf = getIntegerInstance();
            result = nf.parse(courseLevel).intValue();
        } catch (final Exception ex) {
            LOG.log(Level.SEVERE,
                    "Could not parse course level into integer: " + courseLevel,
                    ex);
        }

        return result;
    }

    private String getCourseName(final TranscriptResult tr) {
        final Course c = tr.getCourse();
        final String name = c == null ? "" : c.getName();

        return nullSafe(name);
    }

    private String getCourseCode(final TranscriptResult tr) {
        final Course c = tr.getCourse();
        final String code = c == null ? "" : c.getCode();

        return nullSafe(code);
    }

    public GradSearchStudent getStudentByIDFromStudentApi(String studentID) {
        final String methodName = "getStudentByIDFromStudentApi(String pen)";
        log.trace(CLASSNAME, methodName);
        try {
            GradSearchStudent stud = restService.get(String.format(reportApiConstants.getPenStudentApiByIDUrl(),studentID),
                    new ParameterizedTypeReference<>() {}, educStudentWebClient);
            if(stud != null) {
                return stud;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
