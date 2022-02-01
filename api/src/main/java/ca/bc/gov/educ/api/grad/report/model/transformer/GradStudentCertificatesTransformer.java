package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentCertificatesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentCertificates transformToDTO (GradStudentCertificatesEntity gradStudentReportEntity) {
    	return modelMapper.map(gradStudentReportEntity, GradStudentCertificates.class);
    }

    public GradStudentCertificates transformToDTO ( Optional<GradStudentCertificatesEntity> gradStudentReportEntity ) {
    	GradStudentCertificatesEntity cae = new GradStudentCertificatesEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();
        return modelMapper.map(cae, GradStudentCertificates.class);
    }

	public List<GradStudentCertificates> transformToDTO (Iterable<GradStudentCertificatesEntity> gradCertificateTypesEntities ) {
		List<GradStudentCertificates> gradCertificateTypesList = new ArrayList<>();
        for (GradStudentCertificatesEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentCertificates gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentCertificates.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public List<StudentCredentialDistribution> transformToDTOWithOutCert (Iterable<GradStudentCertificatesEntity> gradCertificateTypesEntities ) {
        return modelMapper.map(gradCertificateTypesEntities, new TypeToken<List<StudentCredentialDistribution>>() {}.getType());
    }

    public GradStudentCertificatesEntity transformToEntity(GradStudentCertificates gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, GradStudentCertificatesEntity.class);
    }
}
