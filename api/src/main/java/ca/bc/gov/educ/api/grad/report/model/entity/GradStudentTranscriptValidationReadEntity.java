package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "STUDENT_TRANSCRIPT_VALIDATION_VW")
public class GradStudentTranscriptValidationReadEntity extends BaseEntity {

    @EmbeddedId
    private GradStudentTranscriptValidationKey studentTranscriptValidationKey;

    @Column(name = "BATCH_ID", nullable = false)
    private Long batchId;

    @Column(name = "TRANSCRIPT_TYPE_CODE", nullable = false)
    private String transcriptTypeCode;

    @Column(name="DOCUMENT_STATUS_CODE",nullable = false)
    private String documentStatusCode;

    @Column(name="VALIDATION_RESULT",nullable = false)
    private String validationResult;

}