package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Immutable
@Entity
@Table(name = "TAB_PRGM")
public class GradProgramEntity {
   
	@Id
	@Column(name = "PRGM_CODE", nullable = false)
    private String programCode; 
	
	@Column(name = "PRGM_NAME", nullable = true)
    private String programName; 

	@Column(name = "START_DATE", nullable = true)
    private Date programStartDate; 
	
	@Column(name = "END_DATE", nullable = true)
    private Date programEndDate;	
	
}