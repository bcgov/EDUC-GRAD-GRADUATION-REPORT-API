package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface ReportExport extends BusinessService{
    
    ReportDocument export(Report report) ;
}
