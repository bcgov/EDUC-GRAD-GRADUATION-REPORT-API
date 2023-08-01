package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "REPORT_SCHOOL_DISTRICT_VW")
public class SchoolReportMonthlyEntity extends SchoolReportEntity {

}
