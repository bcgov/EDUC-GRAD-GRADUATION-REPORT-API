package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportMonthlyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolReportMonthlyRepository extends JpaRepository<SchoolReportMonthlyEntity, UUID> {

    @Query(value="select e from SchoolReportMonthlyEntity e")
    Page<SchoolReportEntity> findStudentForSchoolReport(Pageable page);

}
