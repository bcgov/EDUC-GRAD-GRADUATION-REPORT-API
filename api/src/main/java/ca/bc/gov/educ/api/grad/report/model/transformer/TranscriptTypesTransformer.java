package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.TranscriptTypes;
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
        return modelMapper.map(transcriptEntity, TranscriptTypes.class);
    }

    public TranscriptTypes transformToDTO ( Optional<TranscriptTypesEntity> transcriptEntity ) {
        TranscriptTypesEntity cae = new TranscriptTypesEntity();
        if (transcriptEntity.isPresent())
            cae = transcriptEntity.get();
        return modelMapper.map(cae, TranscriptTypes.class);
    }

	public List<TranscriptTypes> transformToDTO (Iterable<TranscriptTypesEntity> transcriptTypesEntity ) {
		List<TranscriptTypes> transcriptTypesList = new ArrayList<>();
        for (TranscriptTypesEntity tTypesEntity : transcriptTypesEntity) {
            TranscriptTypes tTypes = modelMapper.map(tTypesEntity, TranscriptTypes.class);
            transcriptTypesList.add(tTypes);
        }
        return transcriptTypesList;
    }

    public TranscriptTypesEntity transformToEntity(TranscriptTypes transcriptTypes) {
        return modelMapper.map(transcriptTypes, TranscriptTypesEntity.class);
    }
}
