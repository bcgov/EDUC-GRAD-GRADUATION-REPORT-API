package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Modifying
	@Query(value="DELETE from STUDENT_REPORT where GRADUATION_STUDENT_RECORD_ID in (:studentIDs) and REPORT_TYPE_CODE = :gradReportTypeCode", nativeQuery = true)
   	Integer deleteByStudentIDInAndGradReportTypeCode(List<UUID> studentIDs, String gradReportTypeCode);

	@Modifying
	@Query(value="DELETE from STUDENT_REPORT where REPORT_TYPE_CODE = :gradReportTypeCode", nativeQuery = true)
	Integer deleteByGradReportTypeCode(String gradReportTypeCode);

	@Modifying
	@Query(value="DELETE from STUDENT_REPORT where GRADUATION_STUDENT_RECORD_ID = :studentID and REPORT_TYPE_CODE = :gradReportTypeCode", nativeQuery = true)
	Integer deleteByStudentIDAndGradReportTypeCode(UUID studentID, String gradReportTypeCode);

	List<GradStudentReportsEntity> findByStudentID(UUID studentID);
	
	List<GradStudentReportsEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);

	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCode(UUID studentID,String gradReportTypeCode);
	
	Optional<GradStudentReportsEntity> findByStudentIDAndGradReportTypeCodeAndDocumentStatusCodeNot(UUID studentID,String gradReportTypeCode,String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution(c.id,c.gradReportTypeCode,c.studentID,c.documentStatusCode) from GradStudentReportsEntity c where c.reportUpdateDate is null or c.reportUpdateDate < c.updateDate")
	List<SchoolStudentCredentialDistribution> findByReportUpdateDate();

	@Query("select count(*) from GradStudentReportsEntity c where c.studentID IN (:studentGuids) and c.gradReportTypeCode=:reportType")
	Integer countByStudentGuidsAndReportType(List<UUID> studentGuids, String reportType);

	@Query("select c.studentID from GradStudentReportsEntity c where c.studentID IN (:studentGuids) and c.gradReportTypeCode=:reportType")
	Page<UUID> getReportStudentIDsByStudentIDsAndReportType(List<UUID> studentGuids, String reportType, Pageable page);

	@Query("select count(*) from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	Integer countByReportType(String reportType);

	@Query("select c.studentID from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	Page<UUID> findStudentIDByGradReportTypeCode(String reportType, Pageable page);
}
