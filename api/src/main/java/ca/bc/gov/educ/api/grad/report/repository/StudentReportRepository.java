package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.StudentReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface StudentReportRepository extends JpaRepository<StudentReportEntity, UUID> {

    @Query("select max(c.updateDate) as UpdatedTimestamp from StudentReportEntity c where c.graduationStudentRecordId=:graduationStudentRecordId")
    Optional<Date> getReportUpdatedTimestamp(UUID graduationStudentRecordId);

}