package ca.bc.gov.educ.api.grad.report.service;


import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscriptValidation;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationReadEntity;
import ca.bc.gov.educ.api.grad.report.model.transformer.GradStudentTranscriptValidationTransformer;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentTranscriptValidationReadRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradStudentTranscriptValidationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class StudentTranscriptValidationService extends BaseService {

    @Autowired
    GradStudentTranscriptValidationTransformer gradStudentTranscriptValidationTransformer;
    @Autowired
    GradStudentTranscriptValidationRepository gradStudentTranscriptValidationRepository;
    @Autowired
    GradStudentTranscriptValidationReadRepository gradStudentTranscriptValidationReadRepository;

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(StudentTranscriptValidationService.class);

    @Transactional
    public GradStudentTranscriptValidation saveGradStudentTranscriptValidation(GradStudentTranscriptValidation gradStudentTranscriptValidation) {
        GradStudentTranscriptValidationEntity toBeSaved = gradStudentTranscriptValidationTransformer.transformToEntity(gradStudentTranscriptValidation);
        Optional<GradStudentTranscriptValidationEntity> existingTranscriptValidationEntity = gradStudentTranscriptValidationRepository.findById(toBeSaved.getStudentTranscriptValidationKey());
        if (existingTranscriptValidationEntity.isEmpty()) { // Create
            toBeSaved.setCreateUser("Validation Process");
            toBeSaved.setCreateDate(new Date());
            toBeSaved.setUpdateUser("Validation Process");
            toBeSaved.setUpdateDate(new Date());
            return gradStudentTranscriptValidationTransformer.transformToDTO(gradStudentTranscriptValidationRepository.save(toBeSaved));
        } else { // Update
            toBeSaved = existingTranscriptValidationEntity.get();
            toBeSaved.setUpdateUser("Validation Process");
            toBeSaved.setUpdateDate(new Date());
            return gradStudentTranscriptValidationTransformer.transformToDTO(gradStudentTranscriptValidationRepository.save(toBeSaved));
        }
    }

    @Transactional
    public List<GradStudentTranscriptValidation> getGradStudentTranscriptValidation(Pageable pageable) {
        List<GradStudentTranscriptValidationReadEntity> gradStudentTranscriptValidationReadEntities = gradStudentTranscriptValidationReadRepository.findAllByBatchIdIsNull(pageable).toList();
        return gradStudentTranscriptValidationTransformer.transformToReadDTO(gradStudentTranscriptValidationReadEntities);
    }
}
