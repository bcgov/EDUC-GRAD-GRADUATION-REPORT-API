package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.ReportGradStudentData;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.YearEndReportRequest;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportLightRepository;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service("commonServiceV2")
public class CommonService {

    final GradStudentCertificatesRepository gradStudentCertificatesRepository;
    final GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    final GradStudentReportsRepository gradStudentReportsRepository;
    final SchoolReportRepository schoolReportRepository;
    final SchoolReportLightRepository schoolReportLightRepository;
    final SchoolReportYearEndRepository schoolReportYearEndRepository;
    final SchoolReportMonthlyRepository schoolReportMonthlyRepository;
    final RESTService restService;
    final EducGradReportApiConstants constants;

    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    public static final int PAGE_SIZE = 1000;

    @Autowired
    public CommonService(GradStudentCertificatesRepository gradStudentCertificatesRepository, GradStudentTranscriptsRepository gradStudentTranscriptsRepository, GradStudentReportsRepository gradStudentReportsRepository, SchoolReportRepository schoolReportRepository, RESTService restService, SchoolReportYearEndRepository schoolReportYearEndRepository, EducGradReportApiConstants constants, SchoolReportMonthlyRepository schoolReportMonthlyRepository,SchoolReportLightRepository schoolReportLightRepository) {
        this.gradStudentCertificatesRepository = gradStudentCertificatesRepository;
        this.gradStudentTranscriptsRepository = gradStudentTranscriptsRepository;
        this.gradStudentReportsRepository = gradStudentReportsRepository;
        this.schoolReportRepository = schoolReportRepository;
        this.schoolReportYearEndRepository = schoolReportYearEndRepository;
        this.schoolReportMonthlyRepository = schoolReportMonthlyRepository;
        this.schoolReportLightRepository = schoolReportLightRepository;
        this.restService = restService;
        this.constants = constants;
    }


    public List<StudentCredentialDistribution> getStudentCredentialsForUserRequestDisRun(String credentialType, StudentSearchRequest studentSearchRequest, boolean onlyWithNullDistributionDate) {
        List<StudentCredentialDistribution> scdList = new ArrayList<>();
        if(StringUtils.isBlank(studentSearchRequest.getActivityCode())) {
            studentSearchRequest.setActivityCode("USERDIST" + StringUtils.upperCase(credentialType));
        }
        List<UUID> studentIDs = studentSearchRequest.getStudentIDs();
        if(studentIDs == null || studentIDs.isEmpty()) {
            studentIDs = getStudentsForSpecialGradRun(studentSearchRequest);
        }
        if (!studentIDs.isEmpty()) {
            int partitionSize = 1000;
            List<List<UUID>> partitions = new LinkedList<>();
            for (int i = 0; i < studentIDs.size(); i += partitionSize) {
                partitions.add(studentIDs.subList(i, Math.min(i + partitionSize, studentIDs.size())));
            }
            if (credentialType.equalsIgnoreCase("OC") || credentialType.equalsIgnoreCase("RC")) {
                processCertificate(partitions, studentSearchRequest, scdList, onlyWithNullDistributionDate);
            } else if (credentialType.equalsIgnoreCase("OT") || credentialType.equalsIgnoreCase("RT")) {
                processTranscript(partitions, studentSearchRequest, scdList, onlyWithNullDistributionDate);
            }
        }
        return scdList;
    }

    private void processCertificate(List<List<UUID>> partitions, StudentSearchRequest studentSearchRequest, List<StudentCredentialDistribution> scdList, boolean onlyWithNullDistributionDate) {
        for (List<UUID> subList : partitions) {
            List<StudentCredentialDistribution> scdSubList;
            if (studentSearchRequest != null && studentSearchRequest.getPens() != null && !studentSearchRequest.getPens().isEmpty()) {
                scdSubList = onlyWithNullDistributionDate?
                        gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(subList) :
                        gradStudentCertificatesRepository.findRecordsForUserRequestByStudentIdOnly(subList);
            } else {
                scdSubList = onlyWithNullDistributionDate?
                        gradStudentCertificatesRepository.findRecordsWithNullDistributionDateForUserRequest(subList)
                        : gradStudentCertificatesRepository.findRecordsForUserRequest(subList);
            }
            if (!scdSubList.isEmpty()) {
                scdList.addAll(scdSubList);
            }
        }
    }

    private void processTranscript(List<List<UUID>> partitions, StudentSearchRequest studentSearchRequest, List<StudentCredentialDistribution> scdList, boolean onlyWithNullDistributionDate) {
        for (List<UUID> subList : partitions) {
            List<StudentCredentialDistribution> scdSubList;
            if (studentSearchRequest != null && studentSearchRequest.getPens() != null && !studentSearchRequest.getPens().isEmpty()) {
                scdSubList = onlyWithNullDistributionDate?
                        gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(subList)
                        : gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(subList);
            } else {
                scdSubList = onlyWithNullDistributionDate?
                        gradStudentTranscriptsRepository.findRecordsWithNullDistributionDateForUserRequest(subList)
                        : gradStudentTranscriptsRepository.findRecordsForUserRequest(subList);
            }
            if (!scdSubList.isEmpty()) {
                scdList.addAll(scdSubList);
            }
        }
    }

    public List<UUID> getStudentsForSpecialGradRun(StudentSearchRequest req) {
        GraduationStudentRecordSearchResult res = this.restService.post(constants.getGradStudentApiStudentForSpcGradListUrl(), req, GraduationStudentRecordSearchResult.class);
        if (res != null && !res.getStudentIDs().isEmpty())
            return res.getStudentIDs();
        return new ArrayList<>();
    }

    public Integer countByStudentGuidsAndReportType(List<UUID> studentGuidsString, String reportType) {
        Integer reportsCount = 0;
        if(studentGuidsString != null && !studentGuidsString.isEmpty()) {
            List<UUID> studentGuids = new ArrayList<>(studentGuidsString);
            reportsCount += gradStudentReportsRepository.countByStudentGuidsAndReportType(studentGuids, reportType);
        } else {
            reportsCount += gradStudentReportsRepository.countByReportType(reportType);
        }
        return reportsCount;
    }

    public Integer countBySchoolOfRecordsAndReportType(List<UUID> schoolOfRecords, String reportType) {
        Integer reportsCount = 0;
        if(schoolOfRecords != null && !schoolOfRecords.isEmpty()) {
            reportsCount += schoolReportRepository.countBySchoolOfRecordIdInAndReportTypeCode(schoolOfRecords, reportType);
        } else {
            reportsCount += schoolReportRepository.countByReportTypeCode(reportType);
        }
        return reportsCount;
    }

    public List<ReportGradStudentData> getSchoolReportGradStudentData() {
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> students = schoolReportMonthlyRepository.findStudentForSchoolReport(nextPage);
        return processReportGradStudentDataList(students, YearEndReportRequest.builder().build());
    }

    public List<ReportGradStudentData> getSchoolYearEndReportGradStudentData(YearEndReportRequest yearEndReportRequest) {
        logger.debug("getSchoolYearEndReportGradStudentData>");
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> schoolStudents = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(nextPage);
        return processReportGradStudentDataList(schoolStudents, yearEndReportRequest);
    }

    public List<ReportGradStudentData> getSchoolYearEndReportGradStudentData() {
        logger.debug("getSchoolYearEndReportGradStudentData>");
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> students = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(nextPage);
        return processReportGradStudentDataList(students, YearEndReportRequest.builder().build());
    }

    private List<ReportGradStudentData> processReportGradStudentDataList(Page<SchoolReportEntity> students, YearEndReportRequest yearEndReportRequest) {
        List<ReportGradStudentData> result = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        if(students.hasContent()) {
            PageRequest nextPage;
            result.addAll(getNextPageStudentsFromGradStudentApi(students, yearEndReportRequest));
            final int totalNumberOfPages = students.getTotalPages();
            logger.debug("Total number of pages: {}, total rows count {}", totalNumberOfPages, students.getTotalElements());

            List<Callable<Object>> tasks = new ArrayList<>();

            for (int i = 1; i < totalNumberOfPages; i++) {
                nextPage = PageRequest.of(i, PAGE_SIZE);
                UUIDPageTask pageTask = new UUIDPageTask(nextPage, yearEndReportRequest);
                tasks.add(pageTask);
            }

            processReportGradStudentDataTasksAsync(tasks, result);
        }
        logger.debug("Completed in {} sec, total objects acquired {}", (System.currentTimeMillis() - startTime) / 1000, result.size());
        return result;
    }

    private synchronized List<ReportGradStudentData> getNextPageStudentsFromGradStudentApi(Page<SchoolReportEntity> students, YearEndReportRequest yearEndReportRequest) {
        List<ReportGradStudentData> result = new ArrayList<>();
        List<UUID> studentGuidsInBatch = students.getContent().stream().map(SchoolReportEntity::getGraduationStudentRecordId).distinct().toList();
        List<ReportGradStudentData> studentsInBatch = getReportGradStudentData(studentGuidsInBatch);

        if(studentsInBatch != null && yearEndReportRequest.getDistrictIds() != null && !yearEndReportRequest.getDistrictIds().isEmpty()) {
            studentsInBatch.removeIf(st -> (!yearEndReportRequest.getDistrictIds().contains(st.getDistrictId())));
        }
        if(studentsInBatch != null && yearEndReportRequest.getSchoolIds() != null && !yearEndReportRequest.getSchoolIds().isEmpty()) {
            studentsInBatch.removeIf(st -> (!yearEndReportRequest.getSchoolIds().contains(st.getSchoolOfRecordId())));
        }

        for(SchoolReportEntity e: students.getContent()) {
            String paperType = e.getPaperType();
            String certificateTypeCode = e.getCertificateTypeCode(); //either transcript or certificate codes
            ReportGradStudentData s = getReportGradStudentDataByGraduationStudentRecordIdFromList(e.getGraduationStudentRecordId(), studentsInBatch);
            if(s != null) {
                ReportGradStudentData dataResult = SerializationUtils.clone(s);
                dataResult.setPaperType(paperType);
                if("YED4".equalsIgnoreCase(paperType) && Set.of("CUR", "TER", "ARC").contains(s.getStudentStatus())) {
                    dataResult.setTranscriptTypeCode(certificateTypeCode);
                    result.add(dataResult);
                }
                if (!"YED4".equalsIgnoreCase(paperType)) {
                    dataResult.setCertificateTypeCode(certificateTypeCode);
                    result.add(dataResult);
                }
            }
        }
        return result;
    }

    private synchronized ReportGradStudentData getReportGradStudentDataByGraduationStudentRecordIdFromList(UUID id, List<ReportGradStudentData> studentsInBatch) {
        for(ReportGradStudentData s: studentsInBatch) {
            if(s.getGraduationStudentRecordId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    private synchronized List<ReportGradStudentData> getReportGradStudentData(List<UUID> studentGuids) {
        return this.restService.postForList(constants.getStudentsForSchoolDistribution(), studentGuids, ReportGradStudentData.class);
    }

    private void processReportGradStudentDataTasksAsync(List<Callable<Object>> tasks, List<ReportGradStudentData> result) {
        if(tasks.isEmpty()) return;
        List<Future<Object>> executionResult;
        ExecutorService executorService = Executors.newWorkStealingPool();
        try {
            executionResult = executorService.invokeAll(tasks);
            for (Future<?> f : executionResult) {
                Object o = f.get();
                if(o instanceof Pair<?, ?>) {
                    Pair<PageRequest, List<ReportGradStudentData>> taskResult = (Pair<PageRequest, List<ReportGradStudentData>>) o;
                    result.addAll(taskResult.getRight());
                    logger.debug("Page {} processed successfully", taskResult.getLeft().getPageNumber());
                } else {
                    logger.error("Error during the task execution: {}", f.get());
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            logger.error("Multithreading error during the task execution: {}", ex.getLocalizedMessage());
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }
    }

    class UUIDPageTask implements Callable<Object> {

        private final PageRequest pageRequest;
        private final YearEndReportRequest yearEndReportRequest;

        public UUIDPageTask(PageRequest pageRequest, YearEndReportRequest yearEndReportRequest) {
            this.pageRequest = pageRequest;
            this.yearEndReportRequest = yearEndReportRequest;
        }

        @Override
        public Object call() throws Exception {
            Page<SchoolReportEntity> students = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(pageRequest);
            return Pair.of(pageRequest, getNextPageStudentsFromGradStudentApi(students, yearEndReportRequest));
        }
    }

    @Transactional
    public Integer archiveSchoolReports(long batchId, List<UUID> schoolOfRecordIds, String reportType) {
        if(schoolOfRecordIds != null && !schoolOfRecordIds.isEmpty()) {
            return archiveSchoolReportsBySchoolOfRecordAndReportType(batchId, schoolOfRecordIds, reportType);
        } else {
            schoolOfRecordIds = schoolReportLightRepository.getReportSchoolOfRecordsByReportType(reportType);
            return archiveSchoolReportsBySchoolOfRecordAndReportType(batchId, schoolOfRecordIds, reportType);
        }
    }

    private Integer archiveSchoolReportsBySchoolOfRecordAndReportType(long batchId, List<UUID> schoolOfRecordIds, String reportType) {
        Integer updatedReportsCount = 0;
        Integer deletedReportsCount = 0;
        Integer originalReportsCount = 0;
        String archivedReportType = StringUtils.appendIfMissing(reportType, "ARC", "ARC");
        if(schoolOfRecordIds != null && !schoolOfRecordIds.isEmpty()) {
            List<UUID> reportGuids = schoolReportLightRepository.getReportGuidsBySchoolOfRecordsAndReportType(schoolOfRecordIds, reportType);
            originalReportsCount += schoolReportLightRepository.countBySchoolOfRecordsAndReportType(schoolOfRecordIds, reportType);
            updatedReportsCount += schoolReportRepository.archiveSchoolReports(schoolOfRecordIds, reportType, archivedReportType, batchId);
            if(updatedReportsCount > 0 && originalReportsCount.equals(updatedReportsCount)) {
                deletedReportsCount += schoolReportRepository.deleteSchoolOfRecordsNotMatchingSchoolReports(reportGuids, schoolOfRecordIds, archivedReportType);
                logger.debug("{} School Reports deleted", deletedReportsCount);
            }
        }
        return updatedReportsCount;
    }
}
