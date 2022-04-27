package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "STUDENT_REPORT")
public class GradStudentReportsEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "STUDENT_REPORT_ID", nullable = false)
    private UUID id; 
	
	@Transient
    private String pen; 
	
	@Lob
    @Column(name = "REPORT", columnDefinition="CLOB")
	private String report;
	
	@Column(name = "REPORT_TYPE_CODE", nullable = false)
    private String gradReportTypeCode;
	
	@Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    private UUID studentID;
	
	@Column(name = "DISTRIBUTION_DATE",nullable = true)
	private Date distributionDate;
	
	@Column(name="DOCUMENT_STATUS_CODE",nullable=false)
	private String documentStatusCode;
	
}