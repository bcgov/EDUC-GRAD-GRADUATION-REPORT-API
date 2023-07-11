package ca.bc.gov.educ.api.grad.report.service;


import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.model.transformer.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.ThreadLocalStateUtil;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


@Service
public class CommonService extends BaseService {

    @Autowired
    GradStudentCertificatesTransformer gradStudentCertificatesTransformer;
    @Autowired
    GradStudentCertificatesRepository gradStudentCertificatesRepository;
    @Autowired
    GradStudentReportsTransformer gradStudentReportsTransformer;
    @Autowired
    GradStudentReportsRepository gradStudentReportsRepository;
    @Autowired
    GradStudentTranscriptsTransformer gradStudentTranscriptsTransformer;
    @Autowired
    GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    @Autowired
    GradCertificateTypesRepository gradCertificateTypesRepository;
    @Autowired
    GradCertificateTypesTransformer gradCertificateTypesTransformer;
    @Autowired
    GradReportTypesRepository gradReportTypesRepository;
    @Autowired
    GradReportTypesTransformer gradReportTypesTransformer;
    @Autowired
    DocumentStatusCodeRepository documentStatusCodeRepository;
    @Autowired
    DocumentStatusCodeTransformer documentStatusCodeTransformer;
    @Autowired
    TranscriptTypesRepository transcriptTypesRepository;
    @Autowired
    TranscriptTypesTransformer transcriptTypesTransformer;
    @Autowired
    SchoolReportsTransformer schoolReportsTransformer;
    @Autowired
    SchoolReportsRepository schoolReportsRepository;
    @Autowired
    SchoolReportsLightRepository schoolReportsLightRepository;
    @Autowired
    SchoolReportYearEndRepository schoolReportYearEndRepository;
    @Autowired
    SchoolReportMonthlyRepository schoolReportMonthlyRepository;

    public static final int PAGE_SIZE = 1000;

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String PDF_FILE_NAME = "inline; filename=student_%s_%s.pdf";
    private static final String PDF_FILE_NAME_SCHOOL = "inline; filename=%s_%s00_%s.pdf";
    private static final String COMPLETED = "COMPL";
    private static final String TRAN = "transcript";
    private static final List<String> SCCP_CERT_TYPES = Arrays.asList("SC", "SCF", "SCI");

    @Transactional
    public GradStudentReports saveGradReports(GradStudentReports gradStudentReports, boolean isGraduated) {
        GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
        Optional<GradStudentReportsEntity> existingEntity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(gradStudentReports.getStudentID(), gradStudentReports.getGradReportTypeCode(), "ARCH");
        if (existingEntity.isPresent()) {
            GradStudentReportsEntity gradEntity = existingEntity.get();
            if (isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
                gradEntity.setDocumentStatusCode(COMPLETED);

            }
            if (gradStudentReports.getReport() != null && isClobDataChanged(gradEntity.getReport(), gradStudentReports.getReport())) {
                gradEntity.setReportUpdateDate(new Date());
                gradEntity.setReport(gradStudentReports.getReport());
            }
            return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
        } else {
            toBeSaved.setReportUpdateDate(new Date());
            return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
        }
    }

    @Transactional
    public GradStudentTranscripts saveGradTranscripts(GradStudentTranscripts gradStudentTranscripts, boolean isGraduated) {
        if (gradStudentTranscripts.isOverwrite()) {
            gradStudentTranscriptsRepository.deleteByStudentID(gradStudentTranscripts.getStudentID());
        }
        GradStudentTranscriptsEntity toBeSaved = gradStudentTranscriptsTransformer.transformToEntity(gradStudentTranscripts);
        Optional<GradStudentTranscriptsEntity> existingEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(gradStudentTranscripts.getStudentID(), gradStudentTranscripts.getTranscriptTypeCode(), "ARCH");
        if (existingEntity.isPresent()) {
            GradStudentTranscriptsEntity gradEntity = existingEntity.get();
            if (isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
                gradEntity.setDocumentStatusCode(COMPLETED);

            }
            if (gradStudentTranscripts.getTranscript() != null && isClobDataChanged(gradEntity.getTranscript(), gradStudentTranscripts.getTranscript())) {
                gradEntity.setTranscriptUpdateDate(new Date());
                gradEntity.setTranscript(gradStudentTranscripts.getTranscript());
            }
            return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(gradEntity));
        } else {
            toBeSaved.setTranscriptUpdateDate(new Date());
            return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(toBeSaved));
        }
    }

    @Transactional
    public GradStudentReports getStudentReportObjectByType(UUID studentID, String reportType, String documentStatusCode) {
        return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(studentID, reportType, documentStatusCode));
    }

    @Transactional
    public ResponseEntity<InputStreamResource> getSchoolReportByMincodeAndReportType(String mincode, String reportType) {
        SchoolReports studentReport = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(mincode, reportType));
        if (studentReport != null && studentReport.getReport() != null) {
            byte[] reportByte = Base64.decodeBase64(studentReport.getReport().getBytes(StandardCharsets.US_ASCII));
            ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME_SCHOOL, mincode, LocalDate.now().getYear(), reportType));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return null;
    }

    @Transactional
    public ResponseEntity<InputStreamResource> getStudentReportByType(UUID studentID, String reportType, String documentStatusCode) {
        GradStudentReports studentReport = getStudentReportObjectByType(studentID, reportType, documentStatusCode);
        if (studentReport != null && studentReport.getReport() != null) {
            byte[] reportByte = Base64.decodeBase64(studentReport.getReport().getBytes(StandardCharsets.US_ASCII));
            ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, reportType, "report"));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return null;
    }

    public boolean getStudentCertificate(String certificateType) {
        List<GradStudentCertificatesEntity> gradList = gradStudentCertificatesRepository.existsByCertificateTypeCode(certificateType);
        return !gradList.isEmpty();
    }

    public boolean getStudentReport(String reportType) {
        List<GradStudentReportsEntity> gradList = gradStudentReportsRepository.existsByReportTypeCode(reportType);
        return !gradList.isEmpty();
    }

    public boolean checkStudentCertificateExistsForSCCP(UUID studentID) {
        List<GradStudentCertificatesEntity> gradList = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeIn(studentID, SCCP_CERT_TYPES);
        return !gradList.isEmpty();
    }

    @Transactional
    public GradStudentCertificates saveGradCertificates(GradStudentCertificates gradStudentCertificates) {
        if (gradStudentCertificates.isOverwrite()) {
            gradStudentCertificatesRepository.deleteByStudentID(gradStudentCertificates.getStudentID());
        }
        GradStudentCertificatesEntity toBeSaved = gradStudentCertificatesTransformer.transformToEntity(gradStudentCertificates);
        Optional<GradStudentCertificatesEntity> existingEntity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(gradStudentCertificates.getStudentID(), gradStudentCertificates.getGradCertificateTypeCode(), COMPLETED);
        if (existingEntity.isPresent()) {
            GradStudentCertificatesEntity gradEntity = existingEntity.get();
            if (gradStudentCertificates.getCertificate() != null)
                gradEntity.setCertificate(gradStudentCertificates.getCertificate());
            return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(gradEntity));
        } else {
            return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(toBeSaved));
        }
    }

    @Transactional
    public GradStudentCertificates getStudentCertificateObjectByType(UUID studentID, String certificateType, String documentStatusCode) {
        return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID, certificateType, documentStatusCode));
    }

    @Transactional
    public ResponseEntity<InputStreamResource> getStudentCertificateByType(UUID studentID, String certificateType, String documentStatusCode) {
        GradStudentCertificates studentCertificate = getStudentCertificateObjectByType(studentID, certificateType, documentStatusCode);
        if (studentCertificate != null && studentCertificate.getCertificate() != null) {
            byte[] certificateByte = Base64.decodeBase64(studentCertificate.getCertificate().getBytes(StandardCharsets.US_ASCII));
            ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, certificateType, "certificate"));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return null;
    }

    public List<GradStudentCertificates> getAllStudentCertificateList(UUID studentID) {
        List<GradStudentCertificates> certList = gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentID(studentID));
        certList.forEach(cert -> {
            GradCertificateTypes types = gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.findById(cert.getGradCertificateTypeCode()));
            if (types != null)
                cert.setGradCertificateTypeLabel(types.getLabel());

            DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(cert.getDocumentStatusCode()));
            if (code != null)
                cert.setDocumentStatusLabel(code.getLabel());
        });
        return certList;
    }

    public List<GradStudentTranscripts> getAllStudentTranscriptList(UUID studentID) {
        List<GradStudentTranscripts> transcriptList = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentID(studentID));
        transcriptList.forEach(tran -> {
            TranscriptTypes types = transcriptTypesTransformer.transformToDTO(transcriptTypesRepository.findById(tran.getTranscriptTypeCode()));
            if (types != null)
                tran.setTranscriptTypeLabel(types.getLabel());

            DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(tran.getDocumentStatusCode()));
            if (code != null)
                tran.setDocumentStatusLabel(code.getLabel());
        });
        return transcriptList;
    }


    @Transactional
    public int archiveAllStudentAchievements(UUID studentID) {
        List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        boolean hasDocuments = false;
        if (!repList.isEmpty()) {
            gradStudentReportsRepository.deleteAll(repList);
            hasDocuments = true;
        }
        List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        if (!certList.isEmpty()) {
            hasDocuments = true;
            certList.forEach(cert -> {
                cert.setDocumentStatusCode("ARCH");
                gradStudentCertificatesRepository.save(cert);
            });
        }
        List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        if (!tranList.isEmpty()) {
            hasDocuments = true;
            gradStudentTranscriptsRepository.deleteAll(tranList);
        }
        if (hasDocuments) {
            return 1;
        } else {
            return 0;
        }

    }

    @Transactional
    public int getAllStudentAchievement(UUID studentID) {
        List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        boolean hasDocuments = false;
        if (!repList.isEmpty()) {
            gradStudentReportsRepository.deleteAll(repList);
            hasDocuments = true;
        }
        List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        if (!certList.isEmpty()) {
            hasDocuments = true;
            gradStudentCertificatesRepository.deleteAll(certList);
        }
        List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID, "ARCH");
        if (!tranList.isEmpty()) {
            hasDocuments = true;
            gradStudentTranscriptsRepository.deleteAll(tranList);
        }
        if (hasDocuments) {
            return 1;
        } else {
            return 0;
        }

    }

    public List<GradStudentReports> getAllStudentReportList(UUID studentID) {
        List<GradStudentReports> reportList = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentID(studentID));
        reportList.forEach(rep -> {
            GradReportTypes types = gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findById(rep.getGradReportTypeCode()));
            if (types != null)
                rep.setGradReportTypeLabel(types.getLabel());

            DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(rep.getDocumentStatusCode()));
            if (code != null)
                rep.setDocumentStatusLabel(code.getLabel());
        });
        return reportList;
    }

    public List<SchoolReports> getAllSchoolReportListByMincode(String mincode, String accessToken) {
        List<SchoolReports> reportList = new ArrayList<>();
        if (StringUtils.isNotBlank(mincode)) {
            if (StringUtils.contains(mincode, "*")) {
                reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordContainsOrderBySchoolOfRecord(StringUtils.strip(mincode, "*")));
            } else {
                reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordOrderBySchoolOfRecord(mincode));
            }
        }
        populateSchoolRepors(reportList);
        return reportList;
    }

    public List<SchoolReports> getAllSchoolReportListByReportType(String reportType, String mincode) {
        List<SchoolReportsLightEntity> schoolReportsLightEntityList;
        if(StringUtils.isBlank(mincode)) {
            schoolReportsLightEntityList = schoolReportsLightRepository.findByReportTypeCode(reportType);
        } else {
            schoolReportsLightEntityList = schoolReportsLightRepository.findByReportTypeCodeAndSchoolOfRecord(reportType, mincode);
        }
        List<SchoolReports> reportList = schoolReportsTransformer.transformToLightDTO(schoolReportsLightEntityList);
        populateSchoolRepors(reportList);
        return reportList;
    }

    private void populateSchoolRepors(List<SchoolReports> reportList) {
        reportList.forEach(rep -> {
            String accessToken = fetchAccessToken();
            GradReportTypes types = gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findById(rep.getReportTypeCode()));
            if (types != null)
                rep.setReportTypeLabel(types.getLabel());

            if (rep.getSchoolOfRecord() != null && rep.getSchoolOfRecord().length() > 3) {
                School schObj = getSchool(rep.getSchoolOfRecord(), accessToken);
                if (schObj != null) {
                    rep.setSchoolOfRecordName(schObj.getSchoolName());
                    rep.setSchoolCategory(schObj.getSchoolCategory());
                }
            } else if (rep.getSchoolOfRecord() != null) {
                District distObj = getDistrict(rep.getSchoolOfRecord(), accessToken);
                if (distObj != null) {
                    rep.setSchoolOfRecordName(distObj.getDistrictName());
                }
            }
        });
    }

    public List<StudentCredentialDistribution> getAllStudentCertificateDistributionList() {
        return gradStudentCertificatesRepository.findByDocumentStatusCodeAndNullDistributionDate(COMPLETED);
    }

    public List<StudentCredentialDistribution> getAllStudentTranscriptDistributionList() {
        List<StudentCredentialDistribution> certificates = gradStudentCertificatesRepository.findByDocumentStatusCodeAndNullDistributionDate(COMPLETED);
        List<UUID> studentIds = new ArrayList<>();
        for (StudentCredentialDistribution c : certificates) {
            studentIds.add(c.getStudentID());
        }
        return gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentIds);
    }

    public List<StudentCredentialDistribution> getAllStudentTranscriptYearlyDistributionList(String accessToken) {
        List<StudentCredentialDistribution> scdList = gradStudentTranscriptsRepository.findByDocumentStatusCodeAndDistributionDateYearly(COMPLETED);
        List<UUID> studentList = webClient.get().uri(constants.getStudentsForYearlyDistribution())
                .headers(h -> {
                    h.setBearerAuth(accessToken);
                    h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
                }).retrieve().bodyToMono(new ParameterizedTypeReference<List<UUID>>() {
                }).block();
        if (studentList != null && !studentList.isEmpty()) {
            int partitionSize = 1000;
            List<List<UUID>> partitions = new LinkedList<>();
            for (int i = 0; i < studentList.size(); i += partitionSize) {
                partitions.add(studentList.subList(i, Math.min(i + partitionSize, studentList.size())));
            }
            for (List<UUID> subList : partitions) {
                List<StudentCredentialDistribution> scdSubList = gradStudentTranscriptsRepository.findByReportsForYearly(subList);
                if (!scdSubList.isEmpty()) {
                    scdList.addAll(scdSubList);
                }
            }
        }
        return scdList;

    }

    @Transactional
    public ResponseEntity<InputStreamResource> getStudentTranscriptByStudentID(UUID studentID) {
        List<GradStudentTranscripts> studentTranscript = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentID(studentID));
        if (studentTranscript != null && !studentTranscript.isEmpty() && studentTranscript.get(0).getTranscript() != null) {
            byte[] certificateByte = Base64.decodeBase64(studentTranscript.get(0).getTranscript().getBytes(StandardCharsets.US_ASCII));
            ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, "TRAN", TRAN));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return null;
    }

    @Transactional
    public ResponseEntity<InputStreamResource> getStudentTranscriptByType(UUID studentID, String transcriptType, String documentStatusCode) {
        GradStudentTranscripts studentTranscript = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID, transcriptType, documentStatusCode));
        if (studentTranscript != null && studentTranscript.getTranscript() != null) {
            byte[] certificateByte = Base64.decodeBase64(studentTranscript.getTranscript().getBytes(StandardCharsets.US_ASCII));
            ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, transcriptType, TRAN));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return null;
    }

    public boolean updateStudentCredential(UUID studentID, String credentialTypeCode, String paperType, String documentStatusCode, String activityCode) {
        if (paperType.equalsIgnoreCase("YED4")) {
            Optional<GradStudentTranscriptsEntity> optEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID, credentialTypeCode, documentStatusCode);
            if (optEntity.isPresent()) {
                GradStudentTranscriptsEntity ent = optEntity.get();
                ent.setUpdateDate(null);
                ent.setUpdateUser(null);
                ent.setDistributionDate(new Date());
                gradStudentTranscriptsRepository.save(ent);
                return true;
            }
        } else {
            Optional<GradStudentCertificatesEntity> optEntity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID, credentialTypeCode, documentStatusCode);
            if (optEntity.isPresent()) {
                GradStudentCertificatesEntity ent = optEntity.get();
                ent.setUpdateDate(null);
                ent.setUpdateUser(null);
                if (ent.getDistributionDate() == null && !"USERDISTRC".equalsIgnoreCase(activityCode)) {
                    ent.setDistributionDate(new Date());
                }
                gradStudentCertificatesRepository.save(ent);
                return true;
            }
        }
        return false;
    }

    public boolean updateStudentCredentialPosting(UUID studentID, String credentialTypeCode) {
        if (credentialTypeCode.equalsIgnoreCase("ACHV")) {
            Optional<GradStudentReportsEntity> optEntity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID, credentialTypeCode);
            if (optEntity.isPresent()) {
                GradStudentReportsEntity ent = optEntity.get();
                ent.setUpdateDate(null);
                ent.setUpdateUser(null);
                ent.setReportUpdateDate(new Date());
                gradStudentReportsRepository.save(ent);
                return true;
            }
        } else {
            Optional<GradStudentTranscriptsEntity> optEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCode(studentID, credentialTypeCode);
            if (optEntity.isPresent()) {
                GradStudentTranscriptsEntity ent = optEntity.get();
                ent.setUpdateDate(null);
                ent.setUpdateUser(null);
                ent.setTranscriptUpdateDate(new Date());
                gradStudentTranscriptsRepository.save(ent);
                return true;
            }
        }
        return false;
    }

    public List<StudentCredentialDistribution> getStudentCredentialsForUserRequestDisRun(String credentialType, StudentSearchRequest studentSearchRequest, String accessToken) {
        List<StudentCredentialDistribution> scdList = new ArrayList<>();
        List<UUID> studentList = getStudentsForSpecialGradRun(studentSearchRequest, accessToken);
        if (!studentList.isEmpty()) {
            int partitionSize = 1000;
            List<List<UUID>> partitions = new LinkedList<>();
            for (int i = 0; i < studentList.size(); i += partitionSize) {
                partitions.add(studentList.subList(i, Math.min(i + partitionSize, studentList.size())));
            }
            if (credentialType.equalsIgnoreCase("OC") || credentialType.equalsIgnoreCase("RC")) {
                processCertificate(partitions, scdList);
            } else if (credentialType.equalsIgnoreCase("OT") || credentialType.equalsIgnoreCase("RT")) {
                processTranscript(partitions, studentSearchRequest, scdList);
            }
        }
        return scdList;
    }

    private void processCertificate(List<List<UUID>> partitions, List<StudentCredentialDistribution> scdList) {
        for (List<UUID> subList : partitions) {
            List<StudentCredentialDistribution> scdSubList = gradStudentCertificatesRepository.findRecordsForUserRequest(subList);
            if (!scdSubList.isEmpty()) {
                scdList.addAll(scdSubList);
            }
        }
    }

    private void processTranscript(List<List<UUID>> partitions, StudentSearchRequest studentSearchRequest, List<StudentCredentialDistribution> scdList) {
        for (List<UUID> subList : partitions) {
            List<StudentCredentialDistribution> scdSubList;
            if (!studentSearchRequest.getPens().isEmpty()) {
                scdSubList = gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(subList);
            } else {
                scdSubList = gradStudentTranscriptsRepository.findRecordsForUserRequest(subList);
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

    @Transactional
    public SchoolReports saveSchoolReports(SchoolReports schoolReports) {
        SchoolReportsEntity toBeSaved = schoolReportsTransformer.transformToEntity(schoolReports);
        Optional<SchoolReportsEntity> existingEnity = schoolReportsRepository.findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(schoolReports.getSchoolOfRecord(), schoolReports.getReportTypeCode());
        if (existingEnity.isPresent()) {
            SchoolReportsEntity gradEntity = existingEnity.get();
            gradEntity.setUpdateDate(null);
            gradEntity.setUpdateUser(null);
            if (schoolReports.getReport() != null) {
                gradEntity.setReport(schoolReports.getReport());
            }
            return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(gradEntity));
        } else {
            return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(toBeSaved));
        }
    }

    public boolean updateSchoolReports(String minCode, String reportTypeCode) {
        Optional<SchoolReportsEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(minCode, reportTypeCode);
        if (optEntity.isPresent()) {
            SchoolReportsEntity ent = optEntity.get();
            ent.setUpdateDate(null);
            ent.setUpdateUser(null);
            schoolReportsRepository.save(ent);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteSchoolReports(String minCode, String reportTypeCode) {
        if(StringUtils.isNotBlank(minCode)) {
            Optional<SchoolReportsEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(minCode, reportTypeCode);
            if (optEntity.isPresent()) {
                schoolReportsRepository.delete(optEntity.get());
                return true;
            }
        } else {
            return !schoolReportsRepository.deleteAllByReportTypeCode(reportTypeCode).isEmpty();
        }
        return false;
    }

    private School getSchool(String minCode, String accessToken) {
        try {
            return webClient.get()
                    .uri(String.format(constants.getSchoolByMincodeUrl(), minCode))
                    .headers(h -> {
                        h.setBearerAuth(accessToken);
                        h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
                    })
                    .retrieve()
                    .bodyToMono(School.class)
                    .block();
        } catch (Exception e) {
            logger.warn("Trax School with mincode {} error {}", minCode, e.getMessage());
            return null;
        }
    }

    private District getDistrict(String districtCode, String accessToken) {
        try {
            return webClient.get()
                    .uri(String.format(constants.getDistrictByMincodeUrl(), districtCode))
                    .headers(h -> h.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToMono(District.class)
                    .block();
        } catch (Exception e) {
            logger.warn("Trax District with districtCode {} error {}", districtCode, e.getMessage());
            return null;
        }
    }

    public List<SchoolStudentCredentialDistribution> getAllStudentTranscriptAndReportsPosting() {
        List<SchoolStudentCredentialDistribution> postingList = new ArrayList<>();
        postingList.addAll(gradStudentReportsRepository.findByReportUpdateDate());
        postingList.addAll(gradStudentTranscriptsRepository.findByTranscriptUpdateDate());
        return postingList;
    }

    @Transactional
    public ResponseEntity<InputStreamResource> getStudentCredentialByType(UUID studentID, String type) {
        if (type.equalsIgnoreCase("TRAN")) {
            List<GradStudentTranscripts> studentTranscript = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentID(studentID));
            if (studentTranscript != null && !studentTranscript.isEmpty() && studentTranscript.get(0).getTranscript() != null) {
                byte[] credentialByte = Base64.decodeBase64(studentTranscript.get(0).getTranscript().getBytes(StandardCharsets.US_ASCII));
                ByteArrayInputStream bis = new ByteArrayInputStream(credentialByte);
                HttpHeaders headers = new HttpHeaders();
                headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, type, TRAN));
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis));
            }
        } else if (type.equalsIgnoreCase("ACHV")) {
            List<GradStudentReports> studentReport = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentID(studentID));
            if (studentReport != null && !studentReport.isEmpty() && studentReport.get(0).getReport() != null) {
                byte[] credentialByte = Base64.decodeBase64(studentReport.get(0).getReport().getBytes(StandardCharsets.US_ASCII));
                ByteArrayInputStream bis = new ByteArrayInputStream(credentialByte);
                HttpHeaders headers = new HttpHeaders();
                headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME, type, "achievement"));
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(bis));
            }
        }
        return null;
    }

    public List<ReportGradStudentData> getSchoolYearEndReportGradStudentData() {
        logger.debug("getSchoolYearEndReportGradStudentData>");
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> students = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(nextPage);
        return processReportGradStudentDataList(students, new ArrayList<>());
    }

    public List<ReportGradStudentData> getSchoolYearEndReportGradStudentData(List<String> schools) {
        logger.debug("getSchoolYearEndReportGradStudentData>");
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> students = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(nextPage);
        return processReportGradStudentDataList(students, schools);
    }

    public List<ReportGradStudentData> getSchoolReportGradStudentData() {
        PageRequest nextPage = PageRequest.of(0, PAGE_SIZE);
        Page<SchoolReportEntity> students = schoolReportMonthlyRepository.findStudentForSchoolReport(nextPage);
        return processReportGradStudentDataList(students, new ArrayList());
    }

    @SneakyThrows
    private List<ReportGradStudentData> processReportGradStudentDataList(Page<SchoolReportEntity> students, List<String> schools) {
        List<ReportGradStudentData> result = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        if(students.hasContent()) {
            PageRequest nextPage;
            result.addAll(getNextPageStudentsFromGradStudentApi(students, schools));
            final int totalNumberOfPages = students.getTotalPages();
            logger.debug("Total number of pages: {}, total rows count {}", totalNumberOfPages, students.getTotalElements());

            List<Callable<Object>> tasks = new ArrayList<>();

            for (int i = 1; i < totalNumberOfPages; i++) {
                nextPage = PageRequest.of(i, PAGE_SIZE);
                UUIDPageTask pageTask = new UUIDPageTask(nextPage, schools);
                tasks.add(pageTask);
            }

            processReportGradStudentDataTasksAsync(tasks, result, totalNumberOfPages);
        }
        logger.debug("Completed in {} sec, total objects acquired {}", (System.currentTimeMillis() - startTime) / 1000, result.size());
        return result;
    }

    private List<ReportGradStudentData> getNextPageStudentsFromGradStudentApi(Page<SchoolReportEntity> students, List<String> schools) {
        List<UUID> studentGuidsInBatch = students.getContent().stream().map(SchoolReportEntity::getGraduationStudentRecordId).collect(Collectors.toList());
        List<ReportGradStudentData> studentsInBatch = getReportGradStudentData(fetchAccessToken(), studentGuidsInBatch);
        if(!schools.isEmpty()) {
            boolean isDistrictSchool = schools.get(0).length() == 3;
            if(isDistrictSchool) {
                studentsInBatch.removeIf(st -> (!schools.contains(StringUtils.substring(st.getMincode(), 0, 3))));
            }
            boolean isSchoolSchool = schools.get(0).length() > 3;
            if(isSchoolSchool) {
                studentsInBatch.removeIf(st -> (!schools.contains(StringUtils.trimToEmpty(st.getMincode()))));
            }
        }
        for(SchoolReportEntity e: students.getContent()) {
            String paperType = e.getPaperType();
            String certificateTypeCode = e.getCertificateTypeCode();
            for(ReportGradStudentData s: studentsInBatch) {
                if(s.getGraduationStudentRecordId().equals(e.getGraduationStudentRecordId())) {
                    s.setPaperType(paperType);
                    s.setCertificateTypeCode(certificateTypeCode);
                }
            }
        }
        return studentsInBatch;
    }

    private List<ReportGradStudentData> getReportGradStudentData(String accessToken, List<UUID> studentGuids) {
        final ParameterizedTypeReference<List<ReportGradStudentData>> responseType = new ParameterizedTypeReference<>() {
        };
        return this.webClient.post()
                .uri(constants.getStudentsForSchoolDistribution())
                .headers(h -> {
                    h.setBearerAuth(accessToken);
                    h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
                })
                .body(BodyInserters.fromValue(studentGuids))
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    class UUIDPageTask implements Callable<Object> {

        private final PageRequest pageRequest;
        private final List<String> schools;

        public UUIDPageTask(PageRequest pageRequest, List<String> schools) {
            this.pageRequest = pageRequest;
            this.schools = schools;
        }

        @Override
        public Object call() throws Exception {
            Page<SchoolReportEntity> students = schoolReportYearEndRepository.findStudentForSchoolYearEndReport(pageRequest);
            return Pair.of(pageRequest, getNextPageStudentsFromGradStudentApi(students, schools));
        }
    }
}
