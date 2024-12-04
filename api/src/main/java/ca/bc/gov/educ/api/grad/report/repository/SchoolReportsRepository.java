package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolReportsRepository extends JpaRepository<SchoolReportsEntity, UUID>, JpaSpecificationExecutor<SchoolReportsEntity> {

	List<SchoolReportsEntity> deleteAllByReportTypeCode(String reportTypeCode);

	List<SchoolReportsEntity> findBySchoolOfRecordContainsOrderBySchoolOfRecord(String schoolOfRecord);
	List<SchoolReportsEntity> findBySchoolOfRecordOrderBySchoolOfRecord(String schoolOfRecord);

	Optional<SchoolReportsEntity> findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(String schoolOfRecord, String reportTypeCode);

	Optional<SchoolReportsEntity> findBySchoolOfRecordIdAndReportTypeCode(UUID schoolOfRecordId, String reportTypeCode);

	@Query("select count(*) from SchoolReportsLightEntity c where c.schoolOfRecord IN (:schoolOfRecords) and c.reportTypeCode=:reportType")
	Integer countBySchoolOfRecordsAndReportType(List<String> schoolOfRecords, String reportType);

	@Query("select count(*) from SchoolReportsLightEntity c where c.reportTypeCode=:reportType")
	Integer countByReportType(String reportType);

	@Query("select c.id from SchoolReportsLightEntity c where c.schoolOfRecord IN (:schoolOfRecords) and c.reportTypeCode=:reportType")
	List<UUID> getReportGuidsBySchoolOfRecordsAndReportType(List<String> schoolOfRecords, String reportType);

	@Query("select c.schoolOfRecord from SchoolReportsLightEntity c where c.reportTypeCode=:reportType")
	List<String> getReportSchoolOfRecordsByReportType(String reportType);

	@Modifying
	@Query(value="update SCHOOL_REPORT set REPORT_TYPE_CODE = :reportTypeTo, update_date = SYSDATE, update_user = 'Batch ' || :batchId || ' Archive Process' where school_of_record in (:schoolOfRecords) and REPORT_TYPE_CODE = :reportTypeFrom", nativeQuery=true)
	Integer archiveSchoolReports(List<String> schoolOfRecords, String reportTypeFrom, String reportTypeTo, long batchId);

	@Modifying
	@Query(value="update SCHOOL_REPORT set REPORT_TYPE_CODE = :reportTypeTo, update_date = SYSDATE, update_user = 'Batch ' || :batchId || ' Archive Process' where REPORT_TYPE_CODE = :reportTypeFrom", nativeQuery=true)
	Integer archiveSchoolReports(String reportTypeFrom, String reportTypeTo, long batchId);

	@Modifying
	@Query(value="delete from SCHOOL_REPORT where SCHOOL_REPORT_ID not in (:schoolReportGuids) and school_of_record in (:schoolOfRecords) and REPORT_TYPE_CODE = :archivedReportType", nativeQuery=true)
	Integer deleteSchoolOfRecordsNotMatchingSchoolReports(List<UUID> schoolReportGuids, List<String> schoolOfRecords, String archivedReportType);

	@Modifying
	@Query(value="delete from SCHOOL_REPORT where SCHOOL_REPORT_ID not in (:schoolReportGuids) and REPORT_TYPE_CODE = :archivedReportType", nativeQuery=true)
	Integer deleteAllNotMatchingSchoolReports(List<UUID> schoolReportGuids, String archivedReportType);

}
