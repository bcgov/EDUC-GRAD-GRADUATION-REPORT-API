package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradUngradReasons;
import ca.bc.gov.educ.api.grad.report.model.entity.GradUngradReasonsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradUngradReasonsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradUngradReasons transformToDTO (GradUngradReasonsEntity gradProgramEntity) {
    	GradUngradReasons gradUngradReasons = modelMapper.map(gradProgramEntity, GradUngradReasons.class);
        return gradUngradReasons;
    }

    public GradUngradReasons transformToDTO ( Optional<GradUngradReasonsEntity> gradProgramEntity ) {
    	GradUngradReasonsEntity cae = new GradUngradReasonsEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradUngradReasons gradUngradReasons = modelMapper.map(cae, GradUngradReasons.class);
        return gradUngradReasons;
    }

	public List<GradUngradReasons> transformToDTO (Iterable<GradUngradReasonsEntity> gradUngradReasonsEntities ) {
		List<GradUngradReasons> gradUngradReasonsList = new ArrayList<GradUngradReasons>();
        for (GradUngradReasonsEntity gradUngradReasonsEntity : gradUngradReasonsEntities) {
        	GradUngradReasons gradUngradReasons = new GradUngradReasons();
        	gradUngradReasons = modelMapper.map(gradUngradReasonsEntity, GradUngradReasons.class);            
        	gradUngradReasonsList.add(gradUngradReasons);
        }
        return gradUngradReasonsList;
    }

    public GradUngradReasonsEntity transformToEntity(GradUngradReasons gradUngradReasons) {
        GradUngradReasonsEntity gradUngradReasonsEntity = modelMapper.map(gradUngradReasons, GradUngradReasonsEntity.class);
        return gradUngradReasonsEntity;
    }
}
