package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolReports;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
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

	public List<SchoolReports> transformToDTO (Iterable<SchoolReportsEntity> schoolReportssEntities ) {
		List<SchoolReports> schoolReportssList = new ArrayList<>();
        for (SchoolReportsEntity schoolReportssEntity : schoolReportssEntities) {
        	SchoolReports schoolReportss = modelMapper.map(schoolReportssEntity, SchoolReports.class);
        	schoolReportssList.add(schoolReportss);
        }
        return schoolReportssList;
    }

    public SchoolReportsEntity transformToEntity(SchoolReports schoolReportss) {
        return modelMapper.map(schoolReportss, SchoolReportsEntity.class);
    }
}
