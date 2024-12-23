package ca.bc.gov.educ.api.grad.report.model.entity.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "SchoolReportsEntityV2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCHOOL_REPORT")
public class SchoolReportEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "SCHOOL_REPORT_ID", nullable = false)
	private UUID id;
	
	@Lob
	@Column(name = "REPORT", columnDefinition="CLOB")
	private String report;
	
	@Column(name = "REPORT_TYPE_CODE", nullable = false)
	private String reportTypeCode;

	@Column(name = "SCHOOL_OF_RECORD_ID", nullable = false)
	private UUID schoolOfRecordId;
	
}