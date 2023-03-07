package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "SCHOOL_REPORT")
public class SchoolReportsLightEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "SCHOOL_REPORT_ID", nullable = false)
    private UUID id;
	
	@Column(name = "REPORT_TYPE_CODE", nullable = false)
    private String reportTypeCode;
	
	@Column(name = "SCHOOL_OF_RECORD", nullable = false)
    private String schoolOfRecord;
	
}