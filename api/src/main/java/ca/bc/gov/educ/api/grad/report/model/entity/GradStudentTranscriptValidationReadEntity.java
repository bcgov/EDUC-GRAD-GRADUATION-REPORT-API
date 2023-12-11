package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "STUDENT_TRANSCRIPT_VALIDATION_VW")
public class GradStudentTranscriptValidationReadEntity extends GradStudentTranscriptValidationEntity {

}