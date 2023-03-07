package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportYearEndUUIDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolReportYearEndRepository extends JpaRepository<SchoolReportYearEndUUIDEntity, UUID> {

    @Query(value="select e.graduationStudentRecordId from SchoolReportYearEndUUIDEntity e")
    List<UUID> findStudentIdForSchoolYearEndReport();

}
