package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GradStudentReportsRepository extends JpaRepository<GradStudentReportsEntity, UUID> {

	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCodeAndDocumentStatusCode(UUID studentID,String gradReportTypeCode,String documentStatusCode);
   	
   	@Query("select c from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	List<GradStudentReportsEntity> existsByReportTypeCode(String reportType);
   	
   	long deleteByStudentID(UUID studentID);

	List<GradStudentReportsEntity> findByStudentID(UUID studentID);
	
	List<GradStudentReportsEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);

	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCode(UUID studentID,String gradReportTypeCode);
	
	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(UUID studentID,String gradReportTypeCode,String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution(c.id,c.gradReportTypeCode,c.studentID,c.documentStatusCode) from GradStudentReportsEntity c where c.studentID in (:subList) and (c.postingDate is null or c.postingDate < c.updateDate)")
	List<SchoolStudentCredentialDistribution>  findByPostingDate(List<UUID> subList);
}
