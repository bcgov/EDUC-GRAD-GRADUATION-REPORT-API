package ca.bc.gov.educ.api.grad.report.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.bc.gov.educ.api.grad.report.model.dto.ProgramCertificateTranscript;
import ca.bc.gov.educ.api.grad.report.model.entity.ProgramCertificateTranscriptEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProgramCertificateTranscriptTransformer {

    @Autowired
    ModelMapper modelMapper;

    public ProgramCertificateTranscript transformToDTO (ProgramCertificateTranscriptEntity programCertificateEntity) {
    	return modelMapper.map(programCertificateEntity, ProgramCertificateTranscript.class);
    }

    public ProgramCertificateTranscript transformToDTO (Optional<ProgramCertificateTranscriptEntity> programCertificateEntity ) {
        ProgramCertificateTranscriptEntity cae = new ProgramCertificateTranscriptEntity();
        if (programCertificateEntity.isPresent())
            cae = programCertificateEntity.get();

        return modelMapper.map(cae, ProgramCertificateTranscript.class);
    }

	public List<ProgramCertificateTranscript> transformToDTO (Iterable<ProgramCertificateTranscriptEntity> programCertificateEntities ) {
		List<ProgramCertificateTranscript> programCertificateList = new ArrayList<>();
        for (ProgramCertificateTranscriptEntity programCertificateEntity : programCertificateEntities) {
            ProgramCertificateTranscript programCertificate = modelMapper.map(programCertificateEntity, ProgramCertificateTranscript.class);
        	programCertificateList.add(programCertificate);
        }
        return programCertificateList;
    }

    public ProgramCertificateTranscriptEntity transformToEntity(ProgramCertificateTranscript programCertificate) {
        return modelMapper.map(programCertificate, ProgramCertificateTranscriptEntity.class);
    }
}
