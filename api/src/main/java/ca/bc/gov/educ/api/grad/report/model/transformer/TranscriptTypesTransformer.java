package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.TranscriptTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class TranscriptTypesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public TranscriptTypes transformToDTO (TranscriptTypesEntity transcriptEntity) {
        TranscriptTypes transcriptTypes = modelMapper.map(transcriptEntity, TranscriptTypes.class);
        return transcriptTypes;
    }

    public TranscriptTypes transformToDTO ( Optional<TranscriptTypesEntity> transcriptEntity ) {
        TranscriptTypesEntity cae = new TranscriptTypesEntity();
        if (transcriptEntity.isPresent())
            cae = transcriptEntity.get();

        TranscriptTypes transcriptTypes = modelMapper.map(cae, TranscriptTypes.class);
        return transcriptTypes;
    }

	public List<TranscriptTypes> transformToDTO (Iterable<TranscriptTypesEntity> transcriptTypesEntity ) {
		List<TranscriptTypes> trascriptTypesList = new ArrayList<>();
        for (TranscriptTypesEntity tTypesEntity : transcriptTypesEntity) {
            TranscriptTypes tTypes = new TranscriptTypes();
            tTypes = modelMapper.map(tTypesEntity, TranscriptTypes.class);
            trascriptTypesList.add(tTypes);
        }
        return trascriptTypesList;
    }

    public TranscriptTypesEntity transformToEntity(TranscriptTypes transcriptTypes) {
        return modelMapper.map(transcriptTypes, TranscriptTypesEntity.class);
    }
}
