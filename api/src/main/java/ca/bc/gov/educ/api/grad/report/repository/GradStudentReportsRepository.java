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
   	
   	Integer deleteByStudentIDInAndGradReportTypeCode(List<UUID> studentIDs, String gradReportTypeCode);

	Integer deleteByGradReportTypeCode(String gradReportTypeCode);

	Integer deleteByStudentIDAndGradReportTypeCode(UUID studentID, String gradReportTypeCode);

	List<GradStudentReportsEntity> findByStudentID(UUID studentID);
	
	List<GradStudentReportsEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);

	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCode(UUID studentID,String gradReportTypeCode);
	
	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(UUID studentID,String gradReportTypeCode,String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution(c.id,c.gradReportTypeCode,c.studentID,c.documentStatusCode) from GradStudentReportsEntity c where c.reportUpdateDate is null or c.reportUpdateDate < c.updateDate")
	List<SchoolStudentCredentialDistribution> findByReportUpdateDate();

	@Query("select count(*) from GradStudentReportsEntity c where c.studentID IN (:studentGuids) and c.gradReportTypeCode=:reportType")
	Integer countByStudentGuidsAndReportType(List<UUID> studentGuids, String reportType);

	@Query("select count(*) from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	Integer countByReportType(String reportType);
}
