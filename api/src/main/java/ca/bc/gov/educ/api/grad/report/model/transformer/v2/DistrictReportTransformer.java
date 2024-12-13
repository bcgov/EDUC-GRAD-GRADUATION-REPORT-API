package ca.bc.gov.educ.api.grad.report.model.transformer.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.DistrictReport;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportLightEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component()
public class DistrictReportTransformer {

    @Autowired
    ModelMapper modelMapper;

    DistrictReportTransformer (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public DistrictReport transformToDTO (DistrictReportEntity districtReportEntity) {
    	return modelMapper.map(districtReportEntity, DistrictReport.class);
    }

    public DistrictReport transformToDTO (Optional<DistrictReportEntity> districtReportEntity ) {
    	DistrictReportEntity cae = new DistrictReportEntity();
        if (districtReportEntity.isPresent())
            cae = districtReportEntity.get();
        return modelMapper.map(cae, DistrictReport.class);
    }

	public List<DistrictReport> transformToDTO (Iterable<DistrictReportEntity> districtReportEntities) {
		List<DistrictReport> districtReports = new ArrayList<>();
        for (DistrictReportEntity districtReportEntity : districtReportEntities) {
        	DistrictReport districtReport = modelMapper.map(districtReportEntity, DistrictReport.class);
        	districtReports.add(districtReport);
        }
        return districtReports;
    }

    public List<DistrictReport> transformToLightDTO (Iterable<DistrictReportLightEntity> districtReportLightEntities) {
        List<DistrictReport> districtReports = new ArrayList<>();
        for (DistrictReportLightEntity districtReportLightEntity : districtReportLightEntities) {
            DistrictReport districtReport = modelMapper.map(districtReportLightEntity, DistrictReport.class);
            districtReports.add(districtReport);
        }
        return districtReports;
    }

    public DistrictReportEntity transformToEntity(DistrictReport districtReport) {
        return modelMapper.map(districtReport, DistrictReportEntity.class);
    }
}
