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
import org.apache.commons.codec.binary.Base64;
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

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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

    @Transactional
	public GradStudentReports saveGradReports(GradStudentReports gradStudentReports,boolean isGraduated) {
		GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
		Optional<GradStudentReportsEntity> existingEnity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(gradStudentReports.getStudentID(), gradStudentReports.getGradReportTypeCode(),"ARCH");
		if(existingEnity.isPresent()) {
			GradStudentReportsEntity gradEntity = existingEnity.get();
			if(isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
				gradEntity.setDocumentStatusCode(COMPLETED);
				
			}
			if(gradStudentReports.getReport() != null) {
				gradEntity.setReport(gradStudentReports.getReport());
			}
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
		}else {
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
		}
	}

	@Transactional
	public GradStudentTranscripts saveGradTranscripts(GradStudentTranscripts gradStudentTranscripts, boolean isGraduated) {
		GradStudentTranscriptsEntity toBeSaved = gradStudentTranscriptsTransformer.transformToEntity(gradStudentTranscripts);
		Optional<GradStudentTranscriptsEntity> existingEnity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(gradStudentTranscripts.getStudentID(), gradStudentTranscripts.getTranscriptTypeCode(),"ARCH");
		if(existingEnity.isPresent()) {
			GradStudentTranscriptsEntity gradEntity = existingEnity.get();
			if(isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
				gradEntity.setDocumentStatusCode(COMPLETED);

			}
			if(gradStudentTranscripts.getTranscript() != null) {
				gradEntity.setTranscript(gradStudentTranscripts.getTranscript());
			}
			return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(gradEntity));
		}else {
			return gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.save(toBeSaved));
		}
	}

	@Transactional
	public GradStudentReports getStudentReportObjectByType(UUID studentID, String reportType,String documentStatusCode) {
		return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(studentID,reportType,documentStatusCode));
	}



	@Transactional
	public ResponseEntity<InputStreamResource> getSchoolReportByType(String mincode, String reportType) {
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
		int numberOfReportRecords = 0;
		if(!repList.isEmpty()) {
			numberOfReportRecords =repList.size();
			repList.forEach(rep-> gradStudentReportsRepository.delete(rep));
			hasDocuments = true;
		}
		List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		long numberOfCertificateRecords = 0L;
		if(!certList.isEmpty()) {
			numberOfCertificateRecords =certList.size();
			hasDocuments = true;
			certList.forEach(cert-> {
				cert.setDocumentStatusCode("ARCH");
				gradStudentCertificatesRepository.save(cert);
			});
		}
		List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		long numberOfTranscriptRecords = 0L;
		if(!tranList.isEmpty()) {
			numberOfTranscriptRecords =tranList.size();
			hasDocuments = true;
			tranList.forEach(tran->	gradStudentTranscriptsRepository.delete(tran));
		}
		if(hasDocuments) {
			long total = numberOfReportRecords + numberOfCertificateRecords + numberOfTranscriptRecords;
			if(total > 0) {
				return 1;
			}else {
				return 0;
			}
		}else {
			return 1;
		}

	}

	@Transactional
	public int getAllStudentAchievement(UUID studentID) {
		List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		boolean hasDocuments  = false;
		int numberOfReportRecords = 0;
		if(!repList.isEmpty()) {
			numberOfReportRecords =repList.size(); 
			repList.forEach(rep-> gradStudentReportsRepository.delete(rep));
			hasDocuments = true;
		}
		List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		long numberOfCertificateRecords = 0L;
		if(!certList.isEmpty()) {
			numberOfCertificateRecords =certList.size();
			hasDocuments = true;
			certList.forEach(cert->gradStudentCertificatesRepository.delete(cert));
		}
		List<GradStudentTranscriptsEntity> tranList = gradStudentTranscriptsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		long numberOfTranscriptRecords = 0L;
		if(!tranList.isEmpty()) {
			numberOfTranscriptRecords =tranList.size();
			hasDocuments = true;
			tranList.forEach(tran->gradStudentTranscriptsRepository.delete(tran));
		}
		if(hasDocuments) {
			long total = numberOfReportRecords + numberOfCertificateRecords + numberOfTranscriptRecords;
			if(total > 0) {
				return 1;
			}else {
				return 0;
			}
		}else {
			return 1;
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

	public List<SchoolReports> getAllSchoolReportList(String mincode) {
		List<SchoolReports> reportList = schoolReportsTransformer.transformToDTO(schoolReportsRepository.findBySchoolOfRecord(mincode));
		reportList.forEach(rep -> {
			GradReportTypes types = gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findById(rep.getReportTypeCode()));
			if(types != null)
				rep.setReportTypeLabel(types.getLabel());
		});
		return reportList;
	}


    public List<StudentCredentialDistribution> getAllStudentCertificateDistributionList() {
		return gradStudentCertificatesRepository.findByDocumentStatusCodeAndDistributionDate(COMPLETED);
    }
	public List<StudentCredentialDistribution> getAllStudentTranscriptDistributionList() {
		return gradStudentTranscriptsRepository.findByDocumentStatusCodeAndDistributionDate(COMPLETED);
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
	public ResponseEntity<InputStreamResource> getStudentTranscriptByType(UUID studentID, String transcriptType,String documentStatusCode) {
		GradStudentTranscripts studentTranscript = gradStudentTranscriptsTransformer.transformToDTO(gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID,transcriptType,documentStatusCode));
		if(studentTranscript != null && studentTranscript.getTranscript() != null) {
			byte[] certificateByte = Base64.decodeBase64(studentTranscript.getTranscript().getBytes(StandardCharsets.US_ASCII));
			ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
			HttpHeaders headers = new HttpHeaders();
			headers.add(CONTENT_DISPOSITION, String.format(PDF_FILE_NAME,transcriptType,"transcript"));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}
		return null;
	}

	public boolean updateStudentCredential(UUID studentID, String credentialTypeCode, String paperType,String documentStatusCode) {
		try {
			if (paperType.equalsIgnoreCase("YED4")) {
				Optional<GradStudentTranscriptsEntity> optEntity = gradStudentTranscriptsRepository.findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(studentID,credentialTypeCode,documentStatusCode);
				if(optEntity.isPresent()) {
					GradStudentTranscriptsEntity ent = optEntity.get();
					ent.setDistributionDate(new Date());
					gradStudentTranscriptsRepository.save(ent);
				}
			} else {
				Optional<GradStudentCertificatesEntity> optEntity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID,credentialTypeCode,documentStatusCode);
				if(optEntity.isPresent()) {
					GradStudentCertificatesEntity ent = optEntity.get();
					ent.setDistributionDate(new Date());
					gradStudentCertificatesRepository.save(ent);
				}
			}
		}catch (Exception e) {
			return false;
		}
		return true;
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
				for (List<UUID> subList : partitions) {
					List<StudentCredentialDistribution> scdSubList = gradStudentCertificatesRepository.findRecordsForUserRequest(subList);
					if (!scdSubList.isEmpty()) {
						scdList.addAll(scdSubList);
					}
				}
			} else if (credentialType.equalsIgnoreCase("OT") || credentialType.equalsIgnoreCase("RT")) {
				for (List<UUID> subList : partitions) {
					List<StudentCredentialDistribution> scdSubList;
					if (!studentSearchRequest.getPens().isEmpty()) {
						scdSubList = gradStudentTranscriptsRepository.findRecordsForUserRequestPenOnly(subList);
					} else {
						scdSubList = gradStudentTranscriptsRepository.findRecordsForUserRequest(subList);
					}
					if (!scdSubList.isEmpty()) {
						scdList.addAll(scdSubList);
					}
				}
			}
		}
		return scdList;
	}

	private List<UUID> getStudentsForSpecialGradRun(StudentSearchRequest req, String accessToken) {
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
		if(res != null && !res.getGraduationStudentRecords().isEmpty())
			return res.getGraduationStudentRecords().stream().map(GraduationStudentRecord::getStudentID).collect(Collectors.toList());
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
}
