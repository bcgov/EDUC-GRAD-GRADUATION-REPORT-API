package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public class SchoolReportEntity {

    @Id
    @Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    protected UUID graduationStudentRecordId;

    @Column(name = "PAPER_TYPE")
    private String paperType;

}
