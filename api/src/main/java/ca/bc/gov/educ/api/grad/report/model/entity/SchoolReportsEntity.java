package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCHOOL_REPORT")
public class SchoolReportsEntity extends BaseEntity {
   
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
	
	@Column(name = "SCHOOL_OF_RECORD")
    private String schoolOfRecord;
	
}