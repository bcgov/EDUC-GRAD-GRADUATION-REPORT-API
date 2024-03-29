package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscripts;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentTranscriptsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentTranscripts transformToDTO (GradStudentTranscriptsEntity gradStudentTranscriptsEntity) {
    	return modelMapper.map(gradStudentTranscriptsEntity, GradStudentTranscripts.class);
    }

    public GradStudentTranscripts transformToDTO ( Optional<GradStudentTranscriptsEntity> gradStudentTranscriptsEntity ) {
    	GradStudentTranscriptsEntity cae = new GradStudentTranscriptsEntity();
        if (gradStudentTranscriptsEntity.isPresent())
            cae = gradStudentTranscriptsEntity.get();
        return modelMapper.map(cae, GradStudentTranscripts.class);
    }

	public List<GradStudentTranscripts> transformToDTO (Iterable<GradStudentTranscriptsEntity> gradStudentTranscriptsEntities ) {
		List<GradStudentTranscripts> gradCertificateTypesList = new ArrayList<>();
        for (GradStudentTranscriptsEntity gradTranscriptsEntity : gradStudentTranscriptsEntities) {
        	GradStudentTranscripts gradCertificateTypes = modelMapper.map(gradTranscriptsEntity, GradStudentTranscripts.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradStudentTranscriptsEntity transformToEntity(GradStudentTranscripts gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, GradStudentTranscriptsEntity.class);
    }
}
