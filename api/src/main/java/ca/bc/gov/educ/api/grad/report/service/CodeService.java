package ca.bc.gov.educ.api.grad.report.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.DocumentStatusCodeEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CodeService {

	private final GradCertificateTypesRepository gradCertificateTypesRepository;
	private final GradCertificateTypesTransformer gradCertificateTypesTransformer;
	private final GradReportTypesRepository gradReportTypesRepository;
	private final GradReportTypesTransformer gradReportTypesTransformer;
	private final ProgramCertificateTranscriptRepository programCertificateTranscriptRepository;
	private final ProgramCertificateTranscriptTransformer programCertificateTranscriptTransformer;
	private final TranscriptTypesRepository transcriptTypesRepository;
	private final TranscriptTypesTransformer transcriptTypesTransformer;
	private final DocumentStatusCodeRepository documentStatusCodeRepository;
	private final DocumentStatusCodeTransformer documentStatusCodeTransformer;
	
	GradValidation validation;

	private static final String CREATED_BY="createdBy";
	private static final String CREATED_TIMESTAMP="createdTimestamp";

	@Autowired
	public CodeService(GradCertificateTypesRepository gradCertificateTypesRepository, GradCertificateTypesTransformer gradCertificateTypesTransformer,
					   GradReportTypesRepository gradReportTypesRepository, GradReportTypesTransformer gradReportTypesTransformer,
					   ProgramCertificateTranscriptRepository programCertificateTranscriptRepository,
					   ProgramCertificateTranscriptTransformer programCertificateTranscriptTransformer,
					   TranscriptTypesRepository transcriptTypesRepository, TranscriptTypesTransformer transcriptTypesTransformer,
					   DocumentStatusCodeRepository documentStatusCodeRepository, DocumentStatusCodeTransformer documentStatusCodeTransformer,
					   GradValidation validation) {
		this.gradCertificateTypesRepository = gradCertificateTypesRepository;
		this.gradCertificateTypesTransformer = gradCertificateTypesTransformer;
		this.gradReportTypesRepository = gradReportTypesRepository;
		this.gradReportTypesTransformer = gradReportTypesTransformer;
		this.programCertificateTranscriptRepository = programCertificateTranscriptRepository;
		this.programCertificateTranscriptTransformer = programCertificateTranscriptTransformer;
		this.transcriptTypesRepository = transcriptTypesRepository;
		this.transcriptTypesTransformer = transcriptTypesTransformer;
		this.documentStatusCodeRepository = documentStatusCodeRepository;
		this.documentStatusCodeTransformer = documentStatusCodeTransformer;
		this.validation = validation;
	}

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
		List<ProgramCertificateTranscript> pcList = programCertificateTranscriptTransformer.transformToDTO(programCertificateTranscriptRepository.findCertificates(programCertificateReq.getProgramCode(),programCertificateReq.getSchoolCategoryCode(),programCertificateReq.getOptionalProgram()));
		pcList.forEach(pc-> {
			GradCertificateTypes gcType = gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.findById(pc.getCertificateTypeCode()));
			if(gcType != null) {
				pc.setCertificatePaperType(gcType.getPaperType());
				pc.setCertificateTypeLabel(gcType.getLabel());
			}
		});
		return pcList;
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

	public ProgramCertificateTranscript getProgramTranscript(ProgramCertificateReq programCertificateReq) {
		ProgramCertificateTranscript pcObj = programCertificateTranscriptTransformer.transformToDTO(programCertificateTranscriptRepository.findTranscript(programCertificateReq.getProgramCode(),programCertificateReq.getSchoolCategoryCode()));
		if(pcObj.getTranscriptTypeCode() != null) {
			TranscriptTypes tTypes = transcriptTypesTransformer.transformToDTO(transcriptTypesRepository.findById(pcObj.getTranscriptTypeCode()));
			if(tTypes != null) {
				pcObj.setTranscriptPaperType(tTypes.getPaperType());
				pcObj.setTranscriptTypeLabel(tTypes.getLabel());
			}
		}
		if(pcObj.getCertificateTypeCode() != null) {
			GradCertificateTypes cTypes = gradCertificateTypesTransformer.transformToDTO(gradCertificateTypesRepository.findById(pcObj.getCertificateTypeCode()));
			if(cTypes != null) {
				pcObj.setCertificatePaperType(cTypes.getPaperType());
				pcObj.setCertificateTypeLabel(cTypes.getLabel());
			}
		}
		return pcObj;
	}
}
