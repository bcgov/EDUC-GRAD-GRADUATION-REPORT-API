package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscripts;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
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

    public GradStudentTranscripts transformToDTO (GradStudentTranscriptsEntity gradStudentReportEntity) {
    	return modelMapper.map(gradStudentReportEntity, GradStudentTranscripts.class);
    }

    public GradStudentTranscripts transformToDTO ( Optional<GradStudentTranscriptsEntity> gradStudentReportEntity ) {
    	GradStudentTranscriptsEntity cae = new GradStudentTranscriptsEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();
        return modelMapper.map(cae, GradStudentTranscripts.class);
    }

	public List<GradStudentTranscripts> transformToDTO (Iterable<GradStudentTranscriptsEntity> gradCertificateTypesEntities ) {
		List<GradStudentTranscripts> gradCertificateTypesList = new ArrayList<>();
        for (GradStudentTranscriptsEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentTranscripts gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentTranscripts.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradStudentTranscriptsEntity transformToEntity(GradStudentTranscripts gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, GradStudentTranscriptsEntity.class);
    }
}
