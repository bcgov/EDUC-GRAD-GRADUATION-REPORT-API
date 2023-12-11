package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class GradStudentTranscriptValidationKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PEN", unique = true, updatable = false)
    private String pen;

    @Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    private UUID studentID;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GradStudentTranscriptValidationKey {");
        sb.append("pen=").append(pen).append(", ");
        sb.append("studentID=").append(studentID);
        sb.append('}');
        return sb.toString();
    }
}
