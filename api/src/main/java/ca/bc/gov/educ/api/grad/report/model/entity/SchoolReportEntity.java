package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public class SchoolReportEntity {

    @EmbeddedId
    protected SchoolReportEntityId schoolReportEntityId;

    public UUID getGraduationStudentRecordId() {
        return schoolReportEntityId.graduationStudentRecordId;
    }

    public void setGraduationStudentRecordId(UUID graduationStudentRecordId) {
        this.schoolReportEntityId.graduationStudentRecordId = graduationStudentRecordId;
    }

    public String getPaperType() {
        return schoolReportEntityId.paperType;
    }

    public void setPaperType(String paperType) {
        this.schoolReportEntityId.paperType = paperType;
    }

    public String getCertificateTypeCode() {
        return schoolReportEntityId.certificateTypeCode;
    }

    public void setCertificateTypeCode(String certificateTypeCode) {
        this.schoolReportEntityId.certificateTypeCode = certificateTypeCode;
    }

    @Override
    public String toString() {
        return "SchoolReportEntity {" + schoolReportEntityId + "}";
    }
}
