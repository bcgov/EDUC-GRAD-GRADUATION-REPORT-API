package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;

@Repository
public interface GradStudentReportsRepository extends JpaRepository<GradStudentReportsEntity, UUID> {

	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCode(UUID studentID,String gradReportTypeCode);
   	
   	@Query("select c from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	List<GradStudentReportsEntity> existsByReportTypeCode(String reportType);
   	
   	long deleteByStudentID(UUID studentID);

	List<GradStudentReportsEntity> findByStudentID(UUID studentID);
}
