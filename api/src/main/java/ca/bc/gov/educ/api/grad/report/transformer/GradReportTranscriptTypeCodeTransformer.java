package ca.bc.gov.educ.api.grad.report.transformer;

import ca.bc.gov.educ.api.grad.report.constants.TranscriptTypeCode;
import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GradReportTranscriptTypeCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public List<TranscriptTypeCode> transformToDTO (List<TranscriptTypesEntity> entities ) {
        List<TranscriptTypeCode> codes = new ArrayList<TranscriptTypeCode>();
        for (TranscriptTypesEntity entity : entities) {
            TranscriptTypeCode code = modelMapper.map(entity, TranscriptTypeCode.class);
            codes.add(code);
        }
        return codes;
    }

    public TranscriptTypeCode transformToDTO (TranscriptTypesEntity entity ) {
        TranscriptTypeCode code = modelMapper.map(entity, TranscriptTypeCode.class);
        return code;
    }
}
