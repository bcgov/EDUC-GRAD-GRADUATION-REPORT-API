package ca.bc.gov.educ.api.grad.report.service;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.grad.report.model.dto.DocumentStatusCode;
import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.DocumentStatusCodeTransformer;
import ca.bc.gov.educ.api.grad.report.model.transformer.GradCertificateTypesTransformer;
import ca.bc.gov.educ.api.grad.report.model.transformer.GradReportTypesTransformer;
import ca.bc.gov.educ.api.grad.report.model.transformer.GradStudentCertificatesTransformer;
import ca.bc.gov.educ.api.grad.report.model.transformer.GradStudentReportsTransformer;
import ca.bc.gov.educ.api.grad.report.repository.DocumentStatusCodeRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradCertificateTypesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradReportTypesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentCertificatesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentReportsRepository;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;


@Service
public class CommonService {

    @Autowired
    private GradStudentCertificatesTransformer gradStudentCertificatesTransformer;
    
    @Autowired
    private GradStudentCertificatesRepository gradStudentCertificatesRepository;
    
    @Autowired
    private GradStudentReportsTransformer gradStudentReportsTransformer;
    
    @Autowired
    private GradStudentReportsRepository gradStudentReportsRepository;
    
    @Autowired
	private GradCertificateTypesRepository gradCertificateTypesRepository;

	@Autowired
	private GradCertificateTypesTransformer gradCertificateTypesTransformer;
	
	@Autowired
	private GradReportTypesRepository gradReportTypesRepository;

	@Autowired
	private GradReportTypesTransformer gradReportTypesTransformer;
	
	@Autowired
	private DocumentStatusCodeRepository documentStatusCodeRepository;

	@Autowired
	private DocumentStatusCodeTransformer documentStatusCodeTransformer;
    
    @Autowired
	GradValidation validation;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Transactional
	public GradStudentReports saveGradReports(GradStudentReports gradStudentReports,boolean isGraduated) {
		GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
		Optional<GradStudentReportsEntity> existingEnity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(gradStudentReports.getStudentID(), gradStudentReports.getGradReportTypeCode(),"ARCH");
		if(existingEnity.isPresent()) {
			GradStudentReportsEntity gradEntity = existingEnity.get();
			if(isGraduated && gradEntity.getDocumentStatusCode().equals("IP")) {
				gradEntity.setDocumentStatusCode("COMPL");
				
			}
			if(gradStudentReports.getReport() != null) {
				gradEntity.setReport(gradStudentReports.getReport());
			}
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
		}else {
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
		}
	}
	
	public ResponseEntity<InputStreamResource> getStudentReportByType(UUID studentID, String reportType,String documentStatusCode) {
		GradStudentReports studentReport = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(studentID,reportType,documentStatusCode));
		if(studentReport != null && studentReport.getReport() != null) {
				byte[] reportByte = Base64.decodeBase64(studentReport.getReport().getBytes(StandardCharsets.US_ASCII));
				ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
			    HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Disposition", "inline; filename=student_"+reportType+"_report.pdf");
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
		Optional<GradStudentCertificatesEntity> existingEnity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(gradStudentCertificates.getStudentID(), gradStudentCertificates.getGradCertificateTypeCode(),"COMPL");
		if(existingEnity.isPresent() && gradStudentCertificates.getCertificate() != null) {
			GradStudentCertificatesEntity gradEntity = existingEnity.get();
			gradEntity.setCertificate(gradStudentCertificates.getCertificate());			
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(gradEntity));
		}else {
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(toBeSaved));
		}
	}

	@Transactional
	public ResponseEntity<InputStreamResource> getStudentCertificateByType(UUID studentID, String certificateType,String documentStatusCode) {
		GradStudentCertificates studentCertificate = gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(studentID,certificateType,documentStatusCode));
		if(studentCertificate != null && studentCertificate.getCertificate() != null) {
				byte[] certificateByte = Base64.decodeBase64(studentCertificate.getCertificate().getBytes(StandardCharsets.US_ASCII));
				ByteArrayInputStream bis = new ByteArrayInputStream(certificateByte);
			    HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Disposition", "inline; filename=student_"+certificateType+"_certificate.pdf");
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

	@Transactional
	public int getAllStudentAchievement(UUID studentID) {
		List<GradStudentReportsEntity> repList = gradStudentReportsRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		boolean hasDocuments  = false;
		int numberOfReportRecords = 0;
		if(!repList.isEmpty()) {
			numberOfReportRecords =repList.size(); 
			repList.forEach(rep-> {
				rep.setDocumentStatusCode("ARCH");
				gradStudentReportsRepository.save(rep);
			});
			hasDocuments = true;
		}
		List<GradStudentCertificatesEntity> certList = gradStudentCertificatesRepository.findByStudentIDAndDocumentStatusCodeNot(studentID,"ARCH");
		int numberOfCertificateRecords = 0;
		if(!certList.isEmpty()) {
			numberOfCertificateRecords =certList.size();
			hasDocuments = true;
			certList.forEach(cert-> {
				cert.setDocumentStatusCode("ARCH");
				gradStudentCertificatesRepository.save(cert);
			});
		}
		if(hasDocuments) {
			long total = numberOfReportRecords + numberOfCertificateRecords;
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
}
