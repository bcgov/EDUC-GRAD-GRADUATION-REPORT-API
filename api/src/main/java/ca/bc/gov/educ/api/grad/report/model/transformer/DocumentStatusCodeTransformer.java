package ca.bc.gov.educ.api.grad.report.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.grad.report.model.dto.DocumentStatusCode;
import ca.bc.gov.educ.api.grad.report.model.entity.DocumentStatusCodeEntity;


@Component
public class DocumentStatusCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public DocumentStatusCode transformToDTO (DocumentStatusCodeEntity gradProgramEntity) {
    	return modelMapper.map(gradProgramEntity, DocumentStatusCode.class);
    }

    public DocumentStatusCode transformToDTO ( Optional<DocumentStatusCodeEntity> gradProgramEntity ) {
    	DocumentStatusCodeEntity cae = new DocumentStatusCodeEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();
        return modelMapper.map(cae, DocumentStatusCode.class);
    }

	public List<DocumentStatusCode> transformToDTO (Iterable<DocumentStatusCodeEntity> documentStatusCodeEntities ) {
		List<DocumentStatusCode> documentStatusCodeList = new ArrayList<>();
        for (DocumentStatusCodeEntity documentStatusCodeEntity : documentStatusCodeEntities) {
        	DocumentStatusCode documentStatusCode = modelMapper.map(documentStatusCodeEntity, DocumentStatusCode.class);            
        	documentStatusCodeList.add(documentStatusCode);
        }
        return documentStatusCodeList;
    }

    public DocumentStatusCodeEntity transformToEntity(DocumentStatusCode documentStatusCode) {
        return modelMapper.map(documentStatusCode, DocumentStatusCodeEntity.class);
    }
}
