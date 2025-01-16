package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.repository.v2.SchoolReportRepository;
import ca.bc.gov.educ.api.grad.report.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("commonServiceV2")
public class CommonService extends BaseService {

    final GradStudentCertificatesRepository gradStudentCertificatesRepository;
    final GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    final GradStudentReportsRepository gradStudentReportsRepository;
    final SchoolReportRepository schoolReportRepository;
    final RESTService restService;

    @Autowired
    public CommonService(GradStudentCertificatesRepository gradStudentCertificatesRepository, GradStudentTranscriptsRepository gradStudentTranscriptsRepository, GradStudentReportsRepository gradStudentReportsRepository, SchoolReportRepository schoolReportRepository, RESTService restService) {
        this.gradStudentCertificatesRepository = gradStudentCertificatesRepository;
        this.gradStudentTranscriptsRepository = gradStudentTranscriptsRepository;
        this.gradStudentReportsRepository = gradStudentReportsRepository;
        this.schoolReportRepository = schoolReportRepository;
        this.restService = restService;
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
}
