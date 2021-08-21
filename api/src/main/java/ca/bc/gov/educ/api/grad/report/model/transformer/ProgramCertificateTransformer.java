package ca.bc.gov.educ.api.grad.report.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.grad.report.model.dto.ProgramCertificate;
import ca.bc.gov.educ.api.grad.report.model.entity.ProgramCertificateEntity;


@Component
public class ProgramCertificateTransformer {

    @Autowired
    ModelMapper modelMapper;

    public ProgramCertificate transformToDTO (ProgramCertificateEntity programCertificateEntity) {
    	return modelMapper.map(programCertificateEntity, ProgramCertificate.class);
    }

    public ProgramCertificate transformToDTO ( Optional<ProgramCertificateEntity> programCertificateEntity ) {
    	ProgramCertificateEntity cae = new ProgramCertificateEntity();
        if (programCertificateEntity.isPresent())
            cae = programCertificateEntity.get();

        return modelMapper.map(cae, ProgramCertificate.class);
    }

	public List<ProgramCertificate> transformToDTO (Iterable<ProgramCertificateEntity> programCertificateEntities ) {
		List<ProgramCertificate> programCertificateList = new ArrayList<>();
        for (ProgramCertificateEntity programCertificateEntity : programCertificateEntities) {
        	ProgramCertificate programCertificate = modelMapper.map(programCertificateEntity, ProgramCertificate.class);            
        	programCertificateList.add(programCertificate);
        }
        return programCertificateList;
    }

    public ProgramCertificateEntity transformToEntity(ProgramCertificate programCertificate) {
        return modelMapper.map(programCertificate, ProgramCertificateEntity.class);
    }
}
