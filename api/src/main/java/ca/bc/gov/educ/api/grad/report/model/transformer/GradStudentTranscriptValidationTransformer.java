package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscriptValidation;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationReadEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentTranscriptValidationTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentTranscriptValidation transformToDTO (GradStudentTranscriptValidationEntity gradStudentTranscriptValidationEntity) {
    	return modelMapper.map(gradStudentTranscriptValidationEntity, GradStudentTranscriptValidation.class);
    }

    public GradStudentTranscriptValidation transformToDTO ( Optional<GradStudentTranscriptValidationEntity> gradStudentTranscriptValidationEntity ) {
        GradStudentTranscriptValidationEntity cae = new GradStudentTranscriptValidationEntity();
        if (gradStudentTranscriptValidationEntity.isPresent())
            cae = gradStudentTranscriptValidationEntity.get();
        return modelMapper.map(cae, GradStudentTranscriptValidation.class);
    }

	public List<GradStudentTranscriptValidation> transformToDTO (List<GradStudentTranscriptValidationEntity> gradStudentTranscriptValidationEntities ) {
		List<GradStudentTranscriptValidation> gradStudentTranscriptValidations = new ArrayList<>();
        for (GradStudentTranscriptValidationEntity gradStudentTranscriptValidationEntity : gradStudentTranscriptValidationEntities) {
            GradStudentTranscriptValidation gradStudentTranscriptValidation = modelMapper.map(gradStudentTranscriptValidationEntity, GradStudentTranscriptValidation.class);
        	gradStudentTranscriptValidations.add(gradStudentTranscriptValidation);
        }
        return gradStudentTranscriptValidations;
    }

    public GradStudentTranscriptValidationEntity transformToEntity(GradStudentTranscriptValidation gradStudentTranscriptValidation) {
        return modelMapper.map(gradStudentTranscriptValidation, GradStudentTranscriptValidationEntity.class);
    }

    public List<GradStudentTranscriptValidation> transformToReadDTO(List<GradStudentTranscriptValidationReadEntity> gradStudentTranscriptValidationReadEntities) {
        List<GradStudentTranscriptValidation> gradStudentTranscriptValidations = new ArrayList<>();
        for (GradStudentTranscriptValidationReadEntity gradStudentTranscriptValidationEntity : gradStudentTranscriptValidationReadEntities) {
            GradStudentTranscriptValidation gradStudentTranscriptValidation = modelMapper.map(gradStudentTranscriptValidationEntity, GradStudentTranscriptValidation.class);
            gradStudentTranscriptValidations.add(gradStudentTranscriptValidation);
        }
        return gradStudentTranscriptValidations;
    }
}
