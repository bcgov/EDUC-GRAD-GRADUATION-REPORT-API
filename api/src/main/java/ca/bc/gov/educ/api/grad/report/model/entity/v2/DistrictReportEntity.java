package ca.bc.gov.educ.api.grad.report.model.entity.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity()
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DISTRICT_REPORT")
public class DistrictReportEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "DISTRICT_REPORT_ID", nullable = false)
	private UUID id;
	
	@Lob
	@Column(name = "REPORT", columnDefinition="CLOB")
	private String report;
	
	@Column(name = "REPORT_TYPE_CODE", nullable = false)
	private String reportTypeCode;

	@Column(name = "DISTRICT_ID", nullable = false)
	private UUID districtId;
	
}