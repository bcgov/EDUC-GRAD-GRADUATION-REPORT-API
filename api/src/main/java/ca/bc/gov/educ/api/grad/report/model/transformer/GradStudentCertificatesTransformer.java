package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.entity.StudentCertificateEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentCertificatesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentCertificates transformToDTO (StudentCertificateEntity gradStudentReportEntity) {
    	return modelMapper.map(gradStudentReportEntity, GradStudentCertificates.class);
    }

    public GradStudentCertificates transformToDTO ( Optional<StudentCertificateEntity> gradStudentReportEntity ) {
    	StudentCertificateEntity cae = new StudentCertificateEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();
        return modelMapper.map(cae, GradStudentCertificates.class);
    }

	public List<GradStudentCertificates> transformToDTO (Iterable<StudentCertificateEntity> gradCertificateTypesEntities ) {
		List<GradStudentCertificates> gradCertificateTypesList = new ArrayList<>();
        for (StudentCertificateEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentCertificates gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentCertificates.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public StudentCertificateEntity transformToEntity(GradStudentCertificates gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, StudentCertificateEntity.class);
    }
}
