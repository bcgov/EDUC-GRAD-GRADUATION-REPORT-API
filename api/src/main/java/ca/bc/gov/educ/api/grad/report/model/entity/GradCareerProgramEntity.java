package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "TAB_PRGM")
public class GradCareerProgramEntity  {
   
	@Id
	@Column(name = "PRGM_CODE", nullable = false)
    private String code; 
	
	@Column(name = "PRGM_NAME", nullable = true)
    private String description; 

	@Column(name = "START_DATE", nullable = true)
    private Date startDate; 
	
	@Column(name = "END_DATE", nullable = true)
    private Date endDate;	
}