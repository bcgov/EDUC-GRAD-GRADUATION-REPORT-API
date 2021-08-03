package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
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

    public GradStudentCertificates transformToDTO (GradStudentCertificatesEntity gradStudentReportEntity) {
    	GradStudentCertificates gradCertificateTypes = modelMapper.map(gradStudentReportEntity, GradStudentCertificates.class);
        return gradCertificateTypes;
    }

    public GradStudentCertificates transformToDTO ( Optional<GradStudentCertificatesEntity> gradStudentReportEntity ) {
    	GradStudentCertificatesEntity cae = new GradStudentCertificatesEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();

        GradStudentCertificates gradCertificateTypes = modelMapper.map(cae, GradStudentCertificates.class);
        return gradCertificateTypes;
    }

	public List<GradStudentCertificates> transformToDTO (Iterable<GradStudentCertificatesEntity> gradCertificateTypesEntities ) {
		List<GradStudentCertificates> gradCertificateTypesList = new ArrayList<GradStudentCertificates>();
        for (GradStudentCertificatesEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentCertificates gradCertificateTypes = new GradStudentCertificates();
        	gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentCertificates.class);            
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradStudentCertificatesEntity transformToEntity(GradStudentCertificates gradCertificateTypes) {
        GradStudentCertificatesEntity gradCertificateTypesEntity = modelMapper.map(gradCertificateTypes, GradStudentCertificatesEntity.class);
        return gradCertificateTypesEntity;
    }
}
