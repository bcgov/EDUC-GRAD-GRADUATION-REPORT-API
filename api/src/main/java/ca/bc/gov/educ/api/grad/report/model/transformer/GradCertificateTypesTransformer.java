package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.CertificateTypeCodeEntity;
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

    public GradCertificateTypes transformToDTO (CertificateTypeCodeEntity gradProgramEntity) {
    	return modelMapper.map(gradProgramEntity, GradCertificateTypes.class);
    }

    public GradCertificateTypes transformToDTO ( Optional<CertificateTypeCodeEntity> gradProgramEntity ) {
    	CertificateTypeCodeEntity cae = new CertificateTypeCodeEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();
        return modelMapper.map(cae, GradCertificateTypes.class);
    }

	public List<GradCertificateTypes> transformToDTO (Iterable<CertificateTypeCodeEntity> gradCertificateTypesEntities ) {
		List<GradCertificateTypes> gradCertificateTypesList = new ArrayList<>();
        for (CertificateTypeCodeEntity certificateTypeCodeEntity : gradCertificateTypesEntities) {
            GradCertificateTypes gradCertificateTypes = modelMapper.map(certificateTypeCodeEntity, GradCertificateTypes.class);
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public CertificateTypeCodeEntity transformToEntity(GradCertificateTypes gradCertificateTypes) {
        return modelMapper.map(gradCertificateTypes, CertificateTypeCodeEntity.class);
    }
}
