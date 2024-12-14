package ca.bc.gov.educ.api.grad.report.model.entity.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "DistrictReportsLightEntityV2")
@Table(name = "DISTRICT_REPORT")
public class DistrictReportLightEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "DISTRICT_REPORT_ID", nullable = false)
	private UUID districtReportId;
	
	@Column(name = "REPORT_TYPE_CODE", nullable = false)
	private String reportTypeCode;
	
	@Column(name = "DISTRICT_ID", nullable = false)
	private UUID districtId;
	
}