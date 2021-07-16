package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_STUDENT_CERTIFICATES")
public class GradStudentCertificatesEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "FK_GRAD_STUDENT_PEN", nullable = false)
    private String pen; 
	
	@Lob
    @Column(name = "CERTIFICATE", columnDefinition="CLOB")
	private String certificate;
	
	@Column(name = "FK_GRAD_CERTIFICATE_TYPES_CODE", nullable = false)
    private String gradCertificateTypeCode;	
	
	@Column(name = "FK_GRAD_STUDENT_STUDENT_ID", nullable = false)
    private UUID studentID;
}