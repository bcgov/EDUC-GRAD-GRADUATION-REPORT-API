package ca.bc.gov.educ.api.grad.report.model.entity.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "SchoolReportsLightEntityV2")
@Table(name = "SCHOOL_REPORT")
public class SchoolReportLightEntity extends BaseEntity {
   
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
	
	@Column(name = "SCHOOL_OF_RECORD_ID", nullable = false)
	private UUID schoolOfRecordId;
	
}