package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.GraduationStudentRecordSearchResult;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.service.BaseService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.ThreadLocalStateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.*;

@Service("commonServiceV2")
public class CommonService extends BaseService {

    final GradStudentCertificatesRepository gradStudentCertificatesRepository;
    final GradStudentTranscriptsRepository gradStudentTranscriptsRepository;

    @Autowired
    public CommonService(GradStudentCertificatesRepository gradStudentCertificatesRepository, GradStudentTranscriptsRepository gradStudentTranscriptsRepository) {
        this.gradStudentCertificatesRepository = gradStudentCertificatesRepository;
        this.gradStudentTranscriptsRepository = gradStudentTranscriptsRepository;
    }


    public List<StudentCredentialDistribution> getStudentCredentialsForUserRequestDisRun(String credentialType, StudentSearchRequest studentSearchRequest, boolean onlyWithNullDistributionDate, String accessToken) {
        List<StudentCredentialDistribution> scdList = new ArrayList<>();
        if(StringUtils.isBlank(studentSearchRequest.getActivityCode())) {
            studentSearchRequest.setActivityCode("USERDIST" + StringUtils.upperCase(credentialType));
        }
        List<UUID> studentIDs = studentSearchRequest.getStudentIDs();
        if(studentIDs == null || studentIDs.isEmpty()) {
            studentIDs = getStudentsForSpecialGradRun(studentSearchRequest, accessToken);
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

    public List<UUID> getStudentsForSpecialGradRun(StudentSearchRequest req, String accessToken) {
        GraduationStudentRecordSearchResult res = this.webClient.post()
                .uri(constants.getGradStudentApiStudentForSpcGradListUrl())
                .headers(h -> {
                    h.setBearerAuth(accessToken);
                    h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
                })
                .body(BodyInserters.fromValue(req))
                .retrieve()
                .bodyToMono(GraduationStudentRecordSearchResult.class)
                .block();
        if (res != null && !res.getStudentIDs().isEmpty())
            return res.getStudentIDs();
        return new ArrayList<>();
    }
}
