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
@Table(name = "STUDENT_TRANSCRIPT")
public class GradStudentTranscriptsEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "STUDENT_TRANSCRIPT_ID", nullable = false)
    private UUID id;
	
	@Lob
    @Column(name = "TRANSCRIPT", columnDefinition="CLOB")
	private String transcript;
	
	@Column(name = "TRANSCRIPT_TYPE_CODE", nullable = false)
    private String transcriptTypeCode;
	
	@Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    private UUID studentID;
	
	@Column(name = "DISTRIBUTION_DATE")
	private Date distributionDate;
	
	@Column(name="DOCUMENT_STATUS_CODE",nullable=false)
	private String documentStatusCode;

	@Column(name = "POSTING_DATE")
	private Date postingDate;
}