package ca.bc.gov.educ.api.grad.report.model.transformer.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SchoolReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component()
public class SchoolReportTransformer {

  ModelMapper modelMapper;

  public SchoolReportTransformer (ModelMapper modelMapper){
    this.modelMapper = modelMapper;
  }

    public SchoolReport transformToDTO (SchoolReportEntity schoolReportsEntity) {
    	return modelMapper.map(schoolReportsEntity, SchoolReport.class);
    }

    public SchoolReport transformToDTO (Optional<SchoolReportEntity> schoolReportEntity ) {
    	SchoolReportEntity cae = new SchoolReportEntity();
        if (schoolReportEntity.isPresent())
            cae = schoolReportEntity.get();
        return modelMapper.map(cae, SchoolReport.class);
    }

	public List<SchoolReport> transformToDTO (Iterable<SchoolReportEntity> schoolReportsEntities) {
		List<SchoolReport> schoolReportList = new ArrayList<>();
        for (SchoolReportEntity schoolReportEntity : schoolReportsEntities) {
        	SchoolReport schoolReports = modelMapper.map(schoolReportEntity, SchoolReport.class);
        	schoolReportList.add(schoolReports);
        }
        return schoolReportList;
    }

    public List<SchoolReport> transformToLightDTO (Iterable<SchoolReportLightEntity> schoolReportEntities) {
        List<SchoolReport> schoolReportssList = new ArrayList<>();
        for (SchoolReportLightEntity schoolReportsEntity : schoolReportEntities) {
            SchoolReport schoolReports = modelMapper.map(schoolReportsEntity, SchoolReport.class);
            schoolReportssList.add(schoolReports);
        }
        return schoolReportssList;
    }

    public SchoolReportEntity transformToEntity(SchoolReport schoolReport) {
        return modelMapper.map(schoolReport, SchoolReportEntity.class);
    }
}
