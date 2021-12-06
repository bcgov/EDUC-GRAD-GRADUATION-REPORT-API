package ca.bc.gov.educ.api.grad.report.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.DocumentStatusCodeEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportTypesEntity;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;

@Service
public class CodeService {

	@Autowired
	private GradCertificateTypesRepository gradCertificateTypesRepository;

	@Autowired
	private GradCertificateTypesTransformer gradCertificateTypesTransformer;
	
	@Autowired
	private GradReportTypesRepository gradReportTypesRepository;

	@Autowired
	private GradReportTypesTransformer gradReportTypesTransformer;
	
	@Autowired
	private ProgramCertificateTranscriptRepository programCertificateTranscriptRepository;

	@Autowired
	private ProgramCertificateTranscriptTransformer programCertificateTranscriptTransformer;

	@Autowired
	private TranscriptTypesRepository transcriptTypesRepository;

	@Autowired
	private TranscriptTypesTransformer transcriptTypesTransformer;

	@Autowired
	private DocumentStatusCodeRepository documentStatusCodeRepository;

	@Autowired
	private DocumentStatusCodeTransformer documentStatusCodeTransformer;
	
	@Autowired
	GradValidation validation;

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CodeService.class);
	private static final String CREATED_BY="createdBy";
	private static final String CREATED_TIMESTAMP="createdTimestamp";

	/**
	 * Get all Programs in Grad Program DTO
	 *
	 * @return GradProgram
	 * @throws Exception
	 */

	@Transactional
	public List<GradCertificateTypes> getAllCertificateTypeCodeList() {
		return gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.findAll());
	}

	@Transactional
	public GradCertificateTypes getSpecificCertificateTypeCode(String provCode) {
		Optional<GradCertificateTypesEntity> entity = gradCertificateTypesRepository.findById(StringUtils.toRootUpperCase(provCode));
		if (entity.isPresent()) {
			return gradCertificateTypesTransformer.transformToDTO(entity);
		} else {
			return null;
		}
	}
	
	public GradCertificateTypes createGradCertificateTypes(@Valid GradCertificateTypes gradCertificateTypes) {
		GradCertificateTypesEntity toBeSavedObject = gradCertificateTypesTransformer.transformToEntity(gradCertificateTypes);
		Optional<GradCertificateTypesEntity> existingObjectCheck = gradCertificateTypesRepository.findById(gradCertificateTypes.getCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Certificate Type [%s] already exists",gradCertificateTypes.getCode()));
			return gradCertificateTypes;			
		}else {
			return gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.save(toBeSavedObject));
		}	
	}

	public GradCertificateTypes updateGradCertificateTypes(@Valid GradCertificateTypes gradCertificateTypes) {
		Optional<GradCertificateTypesEntity> gradCertificateTypesOptional = gradCertificateTypesRepository.findById(gradCertificateTypes.getCode());
		GradCertificateTypesEntity sourceObject = gradCertificateTypesTransformer.transformToEntity(gradCertificateTypes);
		if(gradCertificateTypesOptional.isPresent()) {
			GradCertificateTypesEntity gradEnity = gradCertificateTypesOptional.get();			
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATED_BY,CREATED_TIMESTAMP);
    		return gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Certificate Type [%s] does not exists",gradCertificateTypes.getCode()));
			return gradCertificateTypes;
		}
	}

	public int deleteGradCertificateTypes(@Valid String certificateType) {
		Optional<GradCertificateTypesEntity> entity = gradCertificateTypesRepository.findById(certificateType);
		if(entity.isPresent()) {
			gradCertificateTypesRepository.deleteById(certificateType);
			return 1;
		}
		return 0;
	}
	
	@Transactional
	public List<GradReportTypes> getAllReportTypeCodeList() {
		return gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.findAll());
	}

	@Transactional
	public GradReportTypes getSpecificReportTypeCode(String provCode) {
		Optional<GradReportTypesEntity> entity = gradReportTypesRepository.findById(StringUtils.toRootUpperCase(provCode));
		if (entity.isPresent()) {
			return gradReportTypesTransformer.transformToDTO(entity);
		} else {
			return null;
		}
	}
	
	public GradReportTypes createGradReportTypes(@Valid GradReportTypes gradReportTypes) {
		GradReportTypesEntity toBeSavedObject = gradReportTypesTransformer.transformToEntity(gradReportTypes);
		Optional<GradReportTypesEntity> existingObjectCheck = gradReportTypesRepository.findById(gradReportTypes.getCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Report Type [%s] already exists",gradReportTypes.getCode()));
			return gradReportTypes;			
		}else {
			return gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.save(toBeSavedObject));
		}	
	}

	public GradReportTypes updateGradReportTypes(@Valid GradReportTypes gradReportTypes) {
		Optional<GradReportTypesEntity> gradReportTypesOptional = gradReportTypesRepository.findById(gradReportTypes.getCode());
		GradReportTypesEntity sourceObject = gradReportTypesTransformer.transformToEntity(gradReportTypes);
		if(gradReportTypesOptional.isPresent()) {
			GradReportTypesEntity gradEnity = gradReportTypesOptional.get();			
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATED_BY,CREATED_TIMESTAMP);
    		return gradReportTypesTransformer.transformToDTO(gradReportTypesRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Report Type [%s] does not exists",gradReportTypes.getCode()));
			return gradReportTypes;
		}
	}

	public int deleteGradReportTypes(@Valid String reportType) {
		Optional<GradReportTypesEntity> entity = gradReportTypesRepository.findById(reportType);
		if(entity.isPresent()) {
			gradReportTypesRepository.deleteById(reportType);
			return 1;
		}
		return 0;
	}

	public List<ProgramCertificateTranscript> getProgramCertificateList(ProgramCertificateReq programCertificateReq) {
		return programCertificateTranscriptTransformer.transformToDTO(programCertificateTranscriptRepository.findCertificates(programCertificateReq.getProgramCode(),programCertificateReq.getSchoolCategoryCode(),programCertificateReq.getOptionalProgram()));
	}

	@Transactional
	public List<TranscriptTypes> getAllTranscriptTypeCodeList() {
		return transcriptTypesTransformer.transformToDTO(transcriptTypesRepository.findAll());
	}

	@Transactional
	public TranscriptTypes getSpecificTranscriptTypeCode(String tranTypeCode) {
		Optional<TranscriptTypesEntity> entity = transcriptTypesRepository.findById(StringUtils.toRootUpperCase(tranTypeCode));
		if (entity.isPresent()) {
			return transcriptTypesTransformer.transformToDTO(entity);
		} else {
			return null;
		}
	}

	@Transactional
	public List<ProgramCertificateTranscript> getAllProgramCertificateTranscriptList() {
		return programCertificateTranscriptTransformer.transformToDTO(programCertificateTranscriptRepository.findAll());
	}

	@Transactional
	public List<DocumentStatusCode> getAllDocumentStatusCodeList() {
		return documentStatusCodeTransformer.transformToDTO(documentStatusCodeRepository.findAll());
	}

	@Transactional
	public DocumentStatusCode getSpecificDocumentStatusCode(String provCode) {
		Optional<DocumentStatusCodeEntity> entity = documentStatusCodeRepository.findById(StringUtils.toRootUpperCase(provCode));
		if (entity.isPresent()) {
			return documentStatusCodeTransformer.transformToDTO(entity);
		} else {
			return null;
		}
	}

}
