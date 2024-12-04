package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolReports;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsLightEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class SchoolReportsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public SchoolReports transformToDTO (SchoolReportsEntity schoolReportsEntity) {
    	return modelMapper.map(schoolReportsEntity, SchoolReports.class);
    }

    public SchoolReports transformToDTO ( Optional<SchoolReportsEntity> schoolReportsEntity ) {
    	SchoolReportsEntity cae = new SchoolReportsEntity();
        if (schoolReportsEntity.isPresent())
            cae = schoolReportsEntity.get();
        return modelMapper.map(cae, SchoolReports.class);
    }

	public List<SchoolReports> transformToDTO (Iterable<SchoolReportsEntity> schoolReportsEntities) {
		List<SchoolReports> schoolReportssList = new ArrayList<>();
        for (SchoolReportsEntity schoolReportsEntity : schoolReportsEntities) {
        	SchoolReports schoolReports = modelMapper.map(schoolReportsEntity, SchoolReports.class);
        	schoolReportssList.add(schoolReports);
        }
        return schoolReportssList;
    }

    public List<SchoolReports> transformToLightDTO (Iterable<SchoolReportsLightEntity> schoolReportsEntities) {
        List<SchoolReports> schoolReportssList = new ArrayList<>();
        for (SchoolReportsLightEntity schoolReportsEntity : schoolReportsEntities) {
            SchoolReports schoolReports = modelMapper.map(schoolReportsEntity, SchoolReports.class);
            schoolReportssList.add(schoolReports);
        }
        return schoolReportssList;
    }

    public SchoolReportsEntity transformToEntity(SchoolReports schoolReports) {
        return modelMapper.map(schoolReports, SchoolReportsEntity.class);
    }
}
