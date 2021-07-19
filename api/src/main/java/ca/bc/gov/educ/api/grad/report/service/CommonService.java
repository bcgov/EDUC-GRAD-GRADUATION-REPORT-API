package ca.bc.gov.educ.api.grad.report.service;


import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.model.transformer.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradCommonApiConstants;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class CommonService {

    @Autowired
    private GradStudentUngradReasonsTransformer gradStudentUngradReasonsTransformer;
    
    @Autowired
    private GradStudentUngradReasonsRepository gradStudentUngradReasonsRepository; 
    
    @Autowired
    private GradStudentCareerProgramRepository gradStudentCareerProgramRepository;

    @Autowired
    private GradStudentCareerProgramTransformer gradStudentCareerProgramTransformer;
    
    @Autowired
    private GradStudentCertificatesTransformer gradStudentCertificatesTransformer;
    
    @Autowired
    private GradStudentCertificatesRepository gradStudentCertificatesRepository; 
    
    @Autowired
    private GradAlgorithmRulesTransformer gradAlgorithmRulesTransformer;
    
    @Autowired
    private GradAlgorithmRulesRepository gradAlgorithmRulesRepository; 
    
    @Autowired
    private GradStudentReportsTransformer gradStudentReportsTransformer;
    
    @Autowired
    private GradStudentReportsRepository gradStudentReportsRepository; 
    
    @Autowired
    private StudentNoteTransformer  studentNoteTransformer;
    
    @Autowired
    private StudentNoteRepository studentNoteRepository;

    @Autowired
	private EducGradCommonApiConstants constants;
    
    @Autowired
    WebClient webClient;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
	GradValidation validation;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Transactional
    public List<GradStudentUngradReasons> getAllStudentUngradReasonsList(UUID studentID, String accessToken) {
	        List<GradStudentUngradReasons> gradStudentUngradReasonsList  = gradStudentUngradReasonsTransformer.transformToDTO(gradStudentUngradReasonsRepository.findByStudentID(studentID));  
        	gradStudentUngradReasonsList.forEach(sC -> {
        		GradUngradReasons ungradReasonObj = webClient.get().uri(String.format(constants.getUngradReasonByCodeUrl(),sC.getUngradReasonCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradUngradReasons.class).block();
        		if(ungradReasonObj != null) {
        			sC.setUngradReasonName(ungradReasonObj.getDescription());
        		}
        	});
	        return gradStudentUngradReasonsList;
	    }

    @Transactional
  	public List<GradStudentCareerProgram> getAllGradStudentCareerProgramList(String pen, String accessToken) {
  		 
		List<GradStudentCareerProgram> gradStudentCareerProgramList  = gradStudentCareerProgramTransformer.transformToDTO(gradStudentCareerProgramRepository.findByPen(pen)); 
      	gradStudentCareerProgramList.forEach(sC -> {
      		GradCareerProgram gradCareerProgram= webClient.get().uri(String.format(constants.getCareerProgramByCodeUrl(),sC.getCareerProgramCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradCareerProgram.class).block();
    		if(gradCareerProgram != null) {
    			sC.setCareerProgramName(gradCareerProgram.getDescription());
    		}
    	});
      	return gradStudentCareerProgramList;
  	}

    public boolean getStudentUngradReasons(String reasonCode) {
		List<GradStudentUngradReasonsEntity> gradList = gradStudentUngradReasonsRepository.existsByReasonCode(reasonCode);
		return !gradList.isEmpty();
	}

	public boolean getStudentCareerProgram(String cpCode) {
		List<GradStudentCareerProgramEntity> gradList = gradStudentCareerProgramRepository.existsByCareerProgramCode(cpCode);
		return !gradList.isEmpty();
	}

	public GradStudentReports saveGradReports(GradStudentReports gradStudentReports) {
		GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
		Optional<GradStudentReportsEntity> existingEnity = gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(gradStudentReports.getStudentID(), gradStudentReports.getGradReportTypeCode());
		if(existingEnity.isPresent()) {
			GradStudentReportsEntity gradEntity = existingEnity.get();
			if(gradStudentReports.getReport() != null) {
				gradEntity.setReport(gradStudentReports.getReport());
			}			
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
		}else {
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
		}
	}
	
	public ResponseEntity<InputStreamResource> getStudentReportByType(UUID studentID, String reportType) {
		GradStudentReports studentReport = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByStudentIDAndGradReportTypeCode(studentID,reportType));
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

	public GradStudentCertificates saveGradCertificates(GradStudentCertificates gradStudentCertificates) {
		GradStudentCertificatesEntity toBeSaved = gradStudentCertificatesTransformer.transformToEntity(gradStudentCertificates);
		Optional<GradStudentCertificatesEntity> existingEnity = gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCode(gradStudentCertificates.getStudentID(), gradStudentCertificates.getGradCertificateTypeCode());
		if(existingEnity.isPresent()) {
			GradStudentCertificatesEntity gradEntity = existingEnity.get();
			if(gradStudentCertificates.getCertificate() != null) {
				gradEntity.setCertificate(gradStudentCertificates.getCertificate());
			}			
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(gradEntity));
		}else {
			return gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.save(toBeSaved));
		}
	}

	public ResponseEntity<InputStreamResource> getStudentCertificateByType(UUID studentID, String certificateType) {
		GradStudentCertificates studentCertificate = gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentIDAndGradCertificateTypeCode(studentID,certificateType));
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

	public List<GradStudentCertificates> getAllStudentCertificateList(UUID studentID,String accessToken) {
		 
		List<GradStudentCertificates> gradStudentCertificatesList  = gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByStudentID(studentID)); 
		gradStudentCertificatesList.forEach(sC -> {
 			GradCertificateTypes gradCertificateTypes = webClient.get().uri(String.format(constants.getCertificateByCodeUrl(),sC.getGradCertificateTypeCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradCertificateTypes.class).block();
       		if(gradCertificateTypes != null) {
       			sC.setGradCertificateTypeDesc(gradCertificateTypes.getDescription());
       		}
       	});
        return gradStudentCertificatesList;
	}

	public List<GradAlgorithmRules> getAlgorithmRulesList(String programCode) {
		List<GradAlgorithmRules> responseList = gradAlgorithmRulesTransformer.transformToDTO(gradAlgorithmRulesRepository.getAlgorithmRulesByProgramCode(programCode));
		Collections.sort(responseList, Comparator.comparing(GradAlgorithmRules::getSortOrder));
		return responseList;
	}

	public List<GradAlgorithmRules> getAllAlgorithmRulesList() {
		List<GradAlgorithmRules> responseList = gradAlgorithmRulesTransformer.transformToDTO(gradAlgorithmRulesRepository.findAll());
		Collections.sort(responseList, Comparator.comparing(GradAlgorithmRules::getProgramCode)
				 .thenComparing(GradAlgorithmRules::getSortOrder));
		return responseList;
	}

	public List<StudentNote> getAllStudentNotes(String pen) {
		List<StudentNote> responseList = studentNoteTransformer.transformToDTO(studentNoteRepository.findByPen(pen));
		Collections.sort(responseList, Comparator.comparing(StudentNote::getUpdatedTimestamp).reversed());
		return responseList;
	}

	public StudentNote saveStudentNote(StudentNote studentNote) {
		StudentNoteEntity toBeSaved = studentNoteTransformer.transformToEntity(studentNote);
		if(studentNote.getId() != null) {
			Optional<StudentNoteEntity> existingEnity = studentNoteRepository.findById(studentNote.getId());
			if(existingEnity.isPresent()) {
				StudentNoteEntity gradEntity = existingEnity.get();
				if(studentNote.getNote() != null) {
					gradEntity.setNote(studentNote.getNote());
				}
				if(studentNote.getStudentID() != null) {
					gradEntity.setStudentID(UUID.fromString(studentNote.getStudentID()));
				}
				return studentNoteTransformer.transformToDTO(studentNoteRepository.save(gradEntity));
			}else {
				if(studentNote.getStudentID() != null) {
					toBeSaved.setStudentID(UUID.fromString(studentNote.getStudentID()));
				}
			}
		}else {
			if(studentNote.getStudentID() != null) {
				toBeSaved.setStudentID(UUID.fromString(studentNote.getStudentID()));
			}
			
		}
		return studentNoteTransformer.transformToDTO(studentNoteRepository.save(toBeSaved));
	}

	public int deleteNote(UUID noteID) {
		Optional<StudentNoteEntity> existingEnity = studentNoteRepository.findById(noteID);
		if(existingEnity.isPresent()) {
			studentNoteRepository.deleteById(noteID);
			return 1;
		}else {
			return 0;
		}
	}

	public GradStudentUngradReasons createGradStudentUngradReasons(@Valid GradStudentUngradReasons gradStudentUngradReasons,String accessToken) {
		GradStudentUngradReasonsEntity toBeSavedObject = gradStudentUngradReasonsTransformer.transformToEntity(gradStudentUngradReasons);
		if(gradStudentUngradReasons.getId() != null) {
			Optional<GradStudentUngradReasonsEntity> existingObjectCheck = gradStudentUngradReasonsRepository.findById(gradStudentUngradReasons.getId());
			if(existingObjectCheck.isPresent()) {
				validation.addErrorAndStop("Cannot update an existing student ungrad reason");
				return gradStudentUngradReasons;			
			}
			return null;
		}else {
			GradUngradReasons ungradReasonObj = webClient.get().uri(String.format(constants.getUngradReasonByCodeUrl(),gradStudentUngradReasons.getUngradReasonCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradUngradReasons.class).block();
    		if(ungradReasonObj != null) {
    			return gradStudentUngradReasonsTransformer.transformToDTO(gradStudentUngradReasonsRepository.save(toBeSavedObject));
    		}else {
    			validation.addErrorAndStop(String.format("Invalid Ungrad Reason Code [%s]",gradStudentUngradReasons.getUngradReasonCode()));
    			return gradStudentUngradReasons;
    		}
				
		}
	}
}
