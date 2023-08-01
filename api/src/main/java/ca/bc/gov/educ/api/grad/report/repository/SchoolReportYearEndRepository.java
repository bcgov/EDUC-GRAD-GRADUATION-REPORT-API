package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportYearEndEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolReportYearEndRepository extends JpaRepository<SchoolReportYearEndEntity, UUID> {

    @Query(value="select e from SchoolReportYearEndEntity e")
    Page<SchoolReportEntity> findStudentForSchoolYearEndReport(Pageable page);

}
