package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradAlgorithmRules;
import ca.bc.gov.educ.api.grad.report.model.entity.GradAlgorithmRulesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradAlgorithmRulesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradAlgorithmRules transformToDTO (GradAlgorithmRulesEntity gradAlgorithmRulesEntity) {
    	GradAlgorithmRules gradAlgorithmRules = modelMapper.map(gradAlgorithmRulesEntity, GradAlgorithmRules.class);
        return gradAlgorithmRules;
    }

    public GradAlgorithmRules transformToDTO ( Optional<GradAlgorithmRulesEntity> gradAlgorithmRulesEntity ) {
    	GradAlgorithmRulesEntity cae = new GradAlgorithmRulesEntity();
        if (gradAlgorithmRulesEntity.isPresent())
            cae = gradAlgorithmRulesEntity.get();

        GradAlgorithmRules gradAlgorithmRules = modelMapper.map(cae, GradAlgorithmRules.class);
        return gradAlgorithmRules;
    }

	public List<GradAlgorithmRules> transformToDTO (Iterable<GradAlgorithmRulesEntity> gradAlgorithmRulesEntities ) {
		List<GradAlgorithmRules> gradAlgorithmRulesList = new ArrayList<GradAlgorithmRules>();
        for (GradAlgorithmRulesEntity gradAlgorithmRulesEntity : gradAlgorithmRulesEntities) {
        	GradAlgorithmRules gradAlgorithmRules = new GradAlgorithmRules();
        	gradAlgorithmRules = modelMapper.map(gradAlgorithmRulesEntity, GradAlgorithmRules.class);            
        	gradAlgorithmRulesList.add(gradAlgorithmRules);
        }
        return gradAlgorithmRulesList;
    }

    public GradAlgorithmRulesEntity transformToEntity(GradAlgorithmRules gradAlgorithmRules) {
        GradAlgorithmRulesEntity gradAlgorithmRulesEntity = modelMapper.map(gradAlgorithmRules, GradAlgorithmRulesEntity.class);
        return gradAlgorithmRulesEntity;
    }
}
