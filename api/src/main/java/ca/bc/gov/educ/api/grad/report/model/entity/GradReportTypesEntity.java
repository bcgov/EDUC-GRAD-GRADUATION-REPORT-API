package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_REPORT_TYPES")
public class GradReportTypesEntity extends BaseEntity {
   
	@Id
	@Column(name = "REPORT_CODE", nullable = false)
    private String code; 
	
	@Column(name = "REPORT_DESCRIPTION", nullable = true)
    private String description;	
	
}