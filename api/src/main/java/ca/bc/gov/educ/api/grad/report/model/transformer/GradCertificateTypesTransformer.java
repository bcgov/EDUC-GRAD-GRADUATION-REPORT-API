package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradCertificateTypesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradCertificateTypes transformToDTO (GradCertificateTypesEntity gradProgramEntity) {
    	return modelMapper.map(gradProgramEntity, GradCertificateTypes.class);
    }

    public GradCertificateTypes transformToDTO ( Optional<GradCertificateTypesEntity> gradProgramEntity ) {
    	GradCertificateTypesEntity cae = new GradCertificateTypesEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();
        return modelMapper.map(cae, GradCertificateTypes.class);
    }

	public List<GradCertificateTypes> transformToDTO (Iterable<GradCertificateTypesEntity> gradCertificateTypesEntities ) {
		List<GradCertificateTypes> gradCertificateTypesList = new ArrayList<>();
        for (GradCertificateTypesEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
            GradCertificateTypes gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradCertificateTypes.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradCertificateTypesEntity transformToEntity(GradCertificateTypes gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, GradCertificateTypesEntity.class);
    }
}
