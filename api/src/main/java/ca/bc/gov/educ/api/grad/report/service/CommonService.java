package ca.bc.gov.educ.api.grad.report.service;


import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptsEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.ThreadLocalStateUtil;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;


@Service
public class CommonService {

    @Autowired GradStudentCertificatesTransformer gradStudentCertificatesTransformer;
    @Autowired GradStudentCertificatesRepository gradStudentCertificatesRepository;
    @Autowired GradStudentReportsTransformer gradStudentReportsTransformer;
    @Autowired GradStudentReportsRepository gradStudentReportsRepository;
	@Autowired GradStudentTranscriptsTransformer gradStudentTranscriptsTransformer;
	@Autowired GradStudentTranscriptsRepository gradStudentTranscriptsRepository;
    @Autowired GradCertificateTypesRepository gradCertificateTypesRepository;
	@Autowired GradCertificateTypesTransformer gradCertificateTypesTransformer;
	@Autowired GradReportTypesRepository gradReportTypesRepository;
	@Autowired GradReportTypesTransformer gradReportTypesTransformer;
	@Autowired DocumentStatusCodeRepository documentStatusCodeRepository;
	@Autowired DocumentStatusCodeTransformer documentStatusCodeTransformer;
	@Autowired TranscriptTypesRepository transcriptTypesRepository;
	@Autowired TranscriptTypesTransformer transcriptTypesTransformer;
	@Autowired SchoolReportsTransformer schoolReportsTransformer;
	@Autowired SchoolReportsRepository schoolReportsRepository;
	@Autowired WebClient webClient;
	@Autowired EducGradReportApiConstants constants;

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String PDF_FILE_NAME = "inline; filename=student_%s_%s.pdf";
	private static final String PDF_FILE_NAME_SCHOOL = "inline; filename=%s_%s00_%s.pdf";
	private static final String COMPLETED = "COMPL";
	private static final String TRAN = "transcript";
	private static final List<String> SCCP_CERT_TYPES = Arrays.asList("SC", "SCF", "SCI");

    @Transactional
	public GradStudentReports saveGradReports(GradStudentReports gradStudentReports,boolean isGraduated) {
		GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
		Optional<GradStudentReportsEntity> existingEntity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(gradStudentReports.getStudentID(), gradStudentReports.getGradReportTypeCode(),"ARCH");
		if(existingEntity.isPresent()) {
			GradStudentReportsEntity gradEntity = existingEntity.get();
			if(isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
				gradEntity.setDocumentStatusCode(COMPLETED);
				
			}
			if(gradStudentReports.getReport() != null && isClobDataChanged(gradEntity.getReport(), gradStudentReports.getReport())) {
				gradEntity.setReportUpdateDate(new Date());
				gradEntity.setReport(gradStudentReports.getReport());
			}
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
		}else {
			toBeSaved.setReportUpdateDate(new Date());
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
		}
	}

	@Transactional
	public GradStudentTranscripts saveGradTranscripts(GradStudentTranscripts gradStudentTranscripts, boolean isGraduated) {
    	if(gradStudentTranscripts.isOverwrite()) {
			gradStudentTranscriptsRepository.deleteByStudentID(gradStudentTranscripts.getStudentID());
		}
		GradStudentTranscriptsEntity toBeSaved = gradStudentTranscriptsTransformer.transformToEntity(gradStudentTranscripts);
		Optional<GradStudentTranscriptsEntity> existingEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(gradStudentTranscripts.getStudentID(), gradStudentTranscripts.getTranscriptTypeCode(),"ARCH");
		if(existingEntity.isPresent()) {
			GradStudentTranscriptsEntity gradEntity = existingEntity.get();
			if(isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
				gradEntity.setDocumentStatusCode(COMPLETED);

			}
			if(gradStudentTranscripts.getTranscript() != null && isClobDataChanged(gradEntity.getTranscript(), gradStudentTranscripts.getTranscript())) {
				gradEntity.setTranscriptUpdateDate(new Date());
				gradEntity.setTranscript(gradStudentTranscripts.getTranscript());
			}
			return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(gradEntity));
		}else {
			toBeSaved.setTranscriptUpdateDate(new Date());
			return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(toBeSaved));
		}
	}

	@Transactional
	public GradStudentReports getStudentReportObjectByType(UUID studentID, String reportType,String documentStatusCode) {
		return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(studentID,reportType,documentStatusCode));
	}

	@Transactional
	public ResponseEntity<InputStreamResource> getSchoolReportByMincodeAndReportType(String mincode, String reportType) {
		SchoolReports studentReport = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(mincode,reportType));
		if(studentReport != null && studentReport.getReport() != null) {
			byte[] reportByte = Base64.decodeBase64(studentReport.getReport().getBytes(StandardCharsets.US_ASCII));
			ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
			HttpHeaders headers = new HttpHeaders();
			headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME_SCHOOL,mincode, LocalDate.now().getYear(),reportType));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}
		return null;
	}

	@Transactional
	public ResponseEntity<InputStreamResource> getStudentReportByType(UUID studentID, String reportType,String documentStatusCode) {
		GradStudentReports studentReport = getStudentReportObjectByType(studentID,reportType,documentStatusCode);
		if(studentReport != null && studentReport.getReport() != null) {
				byte[] reportByte = Base64.decodeBase64(studentReport.getReport().getBytes(StandardCharsets.US_ASCII));
				ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
			    HttpHeaders headers = new HttpHeaders();
		        headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME,reportType,"report"));
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
		GradStudentCertificatesEntity toBeSaved = gradStudentCertificatesTransformer.transformToEntity(gradStudentCertificates);
		Optional<GradStudentCertificatesEntity> existingEntity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(gradStudentCertificates.getStudentID(), gradStudentCertificates.getGradCertificateTypeCode(),COMPLETED);
		if(existingEntity.isPresent()) {
			GradStudentCertificatesEntity gradEntity = existingEntity.get();
			if(gradStudentCertificates.getCertificate() != null)
				gradEntity.setCertificate(gradStudentCertificates.getCertificate());
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(gradEntity));
		}else {
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(toBeSaved));
		}
	}

	@Transactional
	public GradStudentCertificates getStudentCertificateObjectByType(UUID studentID, String certificateType,String documentStatusCode) {
		return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID,certificateType,documentStatusCode));
	}

	@Transactional
	public ResponseEntity<InputStreamResource> getStudentCertificateByType(UUID studentID, String certificateType,String documentStatusCode) {
		GradStudentCertificates studentCertificate = getStudentCertificateObjectByType(studentID,certificateType,documentStatusCode);
		if(studentCertificate != null && studentCertificate.getCertificate() != null) {
				byte[] certificateByte = Base64.decodeBase64(studentCertificate.getCertificate().getBytes(StandardCharsets.US_ASCII));
				ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
			    HttpHeaders headers = new HttpHeaders();
		        headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME,certificateType,"certificate"));
			    return ResponseEntity
		                .ok()
		                .headers(headers)
		                .contentType(MediaType.APPLICATION_PDF)
		                .body(new InputStreamResource(bis));
		}
		return null;
	}

	public List<GradStudentCertificates> getAllStudentCertificateList(UUID studentID) {
		List<GradStudentCertificates> certList =  gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentID(studentID));
		certList.forEach(cert -> {
			GradCertificateTypes types = gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.findById(cert.getGradCertificateTypeCode()));
			if(types != null)
				cert.setGradCertificateTypeLabel(types.getLabel());
			
			DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(cert.getDocumentStatusCode()));
			if(code != null)
				cert.setDocumentStatusLabel(code.getLabel());
		});
		return certList;
	}

	public List<GradStudentTranscripts> getAllStudentTranscriptList(UUID studentID) {
		List<GradStudentTranscripts> transcriptList =  gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentID(studentID));
		transcriptList.forEach(tran -> {
			TranscriptTypes types = transcriptTypesTransformer.transformToDTO(transcriptTypesRepository.findById(tran.getTranscriptTypeCode()));
			if(types != null)
				tran.setTranscriptTypeLabel(types.getLabel());

			DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(tran.getDocumentStatusCode()));
			if(code != null)
				tran.setDocumentStatusLabel(code.getLabel());
		});
		return transcriptList;
	}


	@Transactional
	public int archiveAllStudentAchievements(UUID studentID) {
		List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		boolean hasDocuments  = false;
		if(!repList.isEmpty()) {
			repList.forEach(rep-> gradStudentReportsRepository.delete(rep));
			hasDocuments = true;
		}
		List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		if(!certList.isEmpty()) {
			hasDocuments = true;
			certList.forEach(cert-> {
				cert.setDocumentStatusCode("ARCH");
				gradStudentCertificatesRepository.save(cert);
			});
		}
		List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		if(!tranList.isEmpty()) {
			hasDocuments = true;
			tranList.forEach(tran->	gradStudentTranscriptsRepository.delete(tran));
		}
		if(hasDocuments) {
			return 1;
		}else {
			return 0;
		}

	}

	@Transactional
	public int getAllStudentAchievement(UUID studentID) {
		List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		boolean hasDocuments  = false;
		if(!repList.isEmpty()) {
			repList.forEach(rep-> gradStudentReportsRepository.delete(rep));
			hasDocuments = true;
		}
		List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		if(!certList.isEmpty()) {
			hasDocuments = true;
			certList.forEach(cert->gradStudentCertificatesRepository.delete(cert));
		}
		List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		if(!tranList.isEmpty()) {
			hasDocuments = true;
			tranList.forEach(tran->gradStudentTranscriptsRepository.delete(tran));
		}
		if(hasDocuments) {
			return 1;
		}else {
			return 0;
		}
		
	}

	public List<GradStudentReports> getAllStudentReportList(UUID studentID) {
		List<GradStudentReports> reportList = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentID(studentID));
		reportList.forEach(rep -> {
			GradReportTypes types = gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findById(rep.getGradReportTypeCode()));
			if(types != null)
				rep.setGradReportTypeLabel(types.getLabel());

			DocumentStatusCode code = documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findById(rep.getDocumentStatusCode()));
			if(code != null)
				rep.setDocumentStatusLabel(code.getLabel());
		});
		return reportList;
	}

	public List<SchoolReports> getAllSchoolReportListByMincode(String mincode, String accessToken) {
		List<SchoolReports> reportList = new ArrayList<>();
		if(StringUtils.isNotBlank(mincode)) {
			if(StringUtils.contains(mincode,"*")) {
				reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecordContains(StringUtils.strip(mincode,"*")));
			} else {
				reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecord(mincode));
			}
		}
		populateSchoolRepors(reportList, accessToken);
		return reportList;
	}

	public List<SchoolReports> getAllSchoolReportListByReportType(String reportType, boolean skipBody, String accessToken) {
		List<SchoolReports> reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findByReportTypeCode(reportType), skipBody);
		populateSchoolRepors(reportList, accessToken);
		return reportList;
	}

	private void populateSchoolRepors(List<SchoolReports> reportList, String accessToken) {
		reportList.forEach(rep -> {
			GradReportTypes types = gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findById(rep.getReportTypeCode()));
			if(types != null)
				rep.setReportTypeLabel(types.getLabel());

			if(rep.getSchoolOfRecord() != null && rep.getSchoolOfRecord().length() > 3) {
				School schObj = getSchool(rep.getSchoolOfRecord(), accessToken);
				if (schObj != null) {
					rep.setSchoolOfRecordName(schObj.getSchoolName());
					rep.setSchoolCategory(schObj.getSchoolCategory());
				}
			} else if(rep.getSchoolOfRecord() != null) {
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
		for(StudentCredentialDistribution c: certificates) {
			studentIds.add(c.getStudentID());
		}
		return gradStudentTranscriptsRepository.findRecordsForUserRequestByStudentIdOnly(studentIds);
	}

	public List<StudentCredentialDistribution> getAllStudentTranscriptYearlyDistributionList(String accessToken) {
		List<StudentCredentialDistribution> scdList = gradStudentTranscriptsRepository.findByDocumentStatusCodeAndDistributionDateYearly(COMPLETED);
		List<UUID> studentList =  webClient.get().uri(constants.getStudentsForYearlyDistribution())
				.headers(h -> {
					h.setBearerAuth(accessToken);
					h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
				}).retrieve().bodyToMono(new ParameterizedTypeReference<List<UUID>>() {}).block();
		if(studentList != null && !studentList.isEmpty()) {
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
		if(studentTranscript != null && !studentTranscript.isEmpty() && studentTranscript.get(0).getTranscript() != null) {
			byte[] certificateByte = Base64.decodeBase64(studentTranscript.get(0).getTranscript().getBytes(StandardCharsets.US_ASCII));
			ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
			HttpHeaders headers = new HttpHeaders();
			headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME,"TRAN",TRAN));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}
		return null;
	}
	@Transactional
	public ResponseEntity<InputStreamResource> getStudentTranscriptByType(UUID studentID, String transcriptType,String documentStatusCode) {
		GradStudentTranscripts studentTranscript = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID,transcriptType,documentStatusCode));
		if(studentTranscript != null && studentTranscript.getTranscript() != null) {
			byte[] certificateByte = Base64.decodeBase64(studentTranscript.getTranscript().getBytes(StandardCharsets.US_ASCII));
			ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
			HttpHeaders headers = new HttpHeaders();
			headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME,transcriptType,TRAN));
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
			Optional<GradStudentTranscriptsEntity> optEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID,credentialTypeCode,documentStatusCode);
			if(optEntity.isPresent()) {
				GradStudentTranscriptsEntity ent = optEntity.get();
				ent.setUpdateDate(null);
				ent.setUpdateUser(null);
				ent.setDistributionDate(new Date());
				gradStudentTranscriptsRepository.save(ent);
				return true;
			}
		} else {
			Optional<GradStudentCertificatesEntity> optEntity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID,credentialTypeCode,documentStatusCode);
			if(optEntity.isPresent()) {
				GradStudentCertificatesEntity ent = optEntity.get();
				ent.setUpdateDate(null);
				ent.setUpdateUser(null);
				if("USERDISTOC".equalsIgnoreCase(activityCode) && ent.getDistributionDate() == null) {
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
			Optional<GradStudentReportsEntity> optEntity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID,credentialTypeCode);
			if(optEntity.isPresent()) {
				GradStudentReportsEntity ent = optEntity.get();
				ent.setUpdateDate(null);
				ent.setUpdateUser(null);
				ent.setReportUpdateDate(new Date());
				gradStudentReportsRepository.save(ent);
				return true;
			}
		} else {
			Optional<GradStudentTranscriptsEntity> optEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCode(studentID,credentialTypeCode);
			if(optEntity.isPresent()) {
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
		List<UUID> studentList =  getStudentsForSpecialGradRun(studentSearchRequest,accessToken);
		if(!studentList.isEmpty()) {
			int partitionSize = 1000;
			List<List<UUID>> partitions = new LinkedList<>();
			for (int i = 0; i < studentList.size(); i += partitionSize) {
				partitions.add(studentList.subList(i, Math.min(i + partitionSize, studentList.size())));
			}
			if (credentialType.equalsIgnoreCase("OC") || credentialType.equalsIgnoreCase("RC")) {
				processCertificate(partitions,scdList,credentialType);
			} else if (credentialType.equalsIgnoreCase("OT") || credentialType.equalsIgnoreCase("RT")) {
				processTranscript(partitions,studentSearchRequest,scdList);
			}
		}
		return scdList;
	}

	private void processCertificate(List<List<UUID>> partitions, List<StudentCredentialDistribution> scdList, String credentialType) {
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

	public  List<UUID> getStudentsForSpecialGradRun(StudentSearchRequest req, String accessToken) {
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
		if(res != null && !res.getStudentIDs().isEmpty())
			return res.getStudentIDs();
		return new ArrayList<>();
	}

	@Transactional
	public SchoolReports saveSchoolReports(SchoolReports schoolReports) {
		SchoolReportsEntity toBeSaved = schoolReportsTransformer.transformToEntity(schoolReports);
		Optional<SchoolReportsEntity> existingEnity = schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(schoolReports.getSchoolOfRecord(), schoolReports.getReportTypeCode());
		if(existingEnity.isPresent()) {
			SchoolReportsEntity gradEntity = existingEnity.get();
			gradEntity.setUpdateDate(null);
			gradEntity.setUpdateUser(null);
			if(schoolReports.getReport() != null) {
				gradEntity.setReport(schoolReports.getReport());
			}
			return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(gradEntity));
		}else {
			return schoolReportsTransformer.transformToDTO(schoolReportsRepository.save(toBeSaved));
		}
	}

	public boolean updateSchoolReports(String minCode, String reportTypeCode) {
		Optional<SchoolReportsEntity> optEntity = schoolReportsRepository.findBySchoolOfRecordAndReportTypeCode(minCode,reportTypeCode);
		if(optEntity.isPresent()) {
			SchoolReportsEntity ent = optEntity.get();
			ent.setUpdateDate(null);
			ent.setUpdateUser(null);
			schoolReportsRepository.save(ent);
			return true;
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
		if(type.equalsIgnoreCase("TRAN")) {
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
		}else if(type.equalsIgnoreCase("ACHV")) {
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

	@SneakyThrows
	public List<ReportGradStudentData> getSchoolYearEndReportGradStudentData(String accessToken) {
		List<String> studentGuids = gradStudentCertificatesRepository.findStudentIdForSchoolYearEndReport();
    	List<UUID> guids = new ArrayList<>();
		for(String studentGuid: studentGuids) {
			byte[] data = Hex.decodeHex(studentGuid.toCharArray());
			UUID guid = new UUID(ByteBuffer.wrap(data, 0, 8).getLong(), ByteBuffer.wrap(data, 8, 8).getLong());
			guids.add(guid);
		}
		final ParameterizedTypeReference<List<ReportGradStudentData>> responseType = new ParameterizedTypeReference<>() {
		};
		List<ReportGradStudentData> reportGradStudentDataList = this.webClient.post()
				.uri(constants.getStudentsForSchoolYearlyDistribution())
				.headers(h -> {
					h.setBearerAuth(accessToken);
					h.set(EducGradReportApiConstants.CORRELATION_ID, ThreadLocalStateUtil.getCorrelationID());
				})
				.body(BodyInserters.fromValue(guids))
				.retrieve()
				.bodyToMono(responseType)
				.block();
		if(reportGradStudentDataList != null) {
			reportGradStudentDataList.removeIf(d -> (d.getCertificateTypes() == null || d.getCertificateTypes().isEmpty()) && StringUtils.isBlank(d.getTranscriptTypeCode()));
		}
		return reportGradStudentDataList;
	}

	private boolean isClobDataChanged(String currentBase64, String newBase64) {
		if (currentBase64 == null || newBase64 == null) {
			return true;
		}
		if (currentBase64.length() != newBase64.length()) {
			return true;
		}
		return !currentBase64.equals(newBase64);
	}
}
