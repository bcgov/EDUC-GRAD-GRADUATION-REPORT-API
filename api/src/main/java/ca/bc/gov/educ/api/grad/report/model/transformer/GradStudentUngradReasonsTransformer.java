package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentUngradReasonsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentUngradReasonsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentUngradReasons transformToDTO (GradStudentUngradReasonsEntity gradProgramEntity) {
    	GradStudentUngradReasons gradCountry = modelMapper.map(gradProgramEntity, GradStudentUngradReasons.class);
        return gradCountry;
    }

    public GradStudentUngradReasons transformToDTO ( Optional<GradStudentUngradReasonsEntity> gradProgramEntity ) {
    	GradStudentUngradReasonsEntity cae = new GradStudentUngradReasonsEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradStudentUngradReasons gradCountry = modelMapper.map(cae, GradStudentUngradReasons.class);
        return gradCountry;
    }

	public List<GradStudentUngradReasons> transformToDTO (List<GradStudentUngradReasonsEntity> gradCountryEntities ) {
		List<GradStudentUngradReasons> gradCountryList = new ArrayList<GradStudentUngradReasons>();
        for (GradStudentUngradReasonsEntity gradCountryEntity : gradCountryEntities) {
        	GradStudentUngradReasons gradCountry = new GradStudentUngradReasons();
        	gradCountry = modelMapper.map(gradCountryEntity, GradStudentUngradReasons.class);            
        	gradCountryList.add(gradCountry);
        }
        return gradCountryList;
    }

    public GradStudentUngradReasonsEntity transformToEntity(GradStudentUngradReasons gradCountry) {
        GradStudentUngradReasonsEntity gradCountryEntity = modelMapper.map(gradCountry, GradStudentUngradReasonsEntity.class);
        return gradCountryEntity;
    }
}
