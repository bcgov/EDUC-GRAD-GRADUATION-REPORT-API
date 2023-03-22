package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportMonthlyUUIDEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolReportMonthlyRepository extends JpaRepository<SchoolReportMonthlyUUIDEntity, UUID> {

    @Query(value="select e.graduationStudentRecordId from SchoolReportMonthlyUUIDEntity e")
    Page<UUID> findStudentIdForSchoolReport(Pageable page);

}
