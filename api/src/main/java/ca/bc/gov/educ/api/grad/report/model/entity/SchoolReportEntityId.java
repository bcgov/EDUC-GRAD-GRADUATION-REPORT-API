package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolReportEntityId implements Serializable {

    @Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    protected UUID graduationStudentRecordId;
    @Column(name = "PAPER_TYPE")
    protected String paperType;
    @Column(name = "CERTIFICATE_TYPE_CODE")
    protected String certificateTypeCode;
    @Column(name = "REPORTING_SCHOOL_TYPE_CODE")
    protected String reportingSchoolTypeCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolReportEntityId that = (SchoolReportEntityId) o;
        return Objects.equals(graduationStudentRecordId, that.graduationStudentRecordId) && Objects.equals(paperType, that.paperType) && Objects.equals(certificateTypeCode, that.certificateTypeCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graduationStudentRecordId, paperType, certificateTypeCode);
    }

    @Override
    public String toString() {
        return "SchoolReportEntityId {" +
                "graduationStudentRecordId=" + graduationStudentRecordId +
                ", paperType='" + paperType + '\'' +
                ", certificateTypeCode='" + certificateTypeCode + '\'' +
                '}';
    }
}
