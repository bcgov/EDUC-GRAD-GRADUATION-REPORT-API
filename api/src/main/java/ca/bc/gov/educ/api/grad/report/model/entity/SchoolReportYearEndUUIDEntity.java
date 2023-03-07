package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "REPORT_SCHOOL_DISTRICT_YE_VW")
public class SchoolReportYearEndUUIDEntity extends UUIDEntity {

    @Id
    @Column(name = "GRADUATION_STUDENT_RECORD_ID", nullable = false)
    private UUID graduationStudentRecordId;
}
