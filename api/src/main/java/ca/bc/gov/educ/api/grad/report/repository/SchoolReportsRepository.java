package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolReportsRepository extends JpaRepository<SchoolReportsEntity, UUID> {

   	@Query("select c from SchoolReportsEntity c where c.reportTypeCode=:reportType")
	List<SchoolReportsEntity> existsByReportTypeCode(String reportType);
   	
   	long deleteBySchoolOfRecord(String schoolOfRecord);
	List<SchoolReportsEntity> deleteAllByReportTypeCode(String reportTypeCode);

	List<SchoolReportsEntity> findBySchoolOfRecordContainsOrderBySchoolOfRecord(String schoolOfRecord);
	List<SchoolReportsEntity> findBySchoolOfRecordOrderBySchoolOfRecord(String schoolOfRecord);

	Optional<SchoolReportsEntity> findBySchoolOfRecordAndReportTypeCodeOrderBySchoolOfRecord(String schoolOfRecord, String reportTypeCode);

	@Query("select count(*) from SchoolReportsLightEntity c where c.schoolOfRecord IN (:schoolOfRecords) and c.reportTypeCode=:reportType")
	Integer countBySchoolOfRecordsAndReportType(List<String> schoolOfRecords, String reportType);

	@Query("select count(*) from SchoolReportsLightEntity c where c.reportTypeCode=:reportType")
	Integer countByReportType(String reportType);

	@Modifying
	@Query(value="update SCHOOL_REPORT set REPORT_TYPE_CODE = :reportTypeTo, update_date = SYSDATE, update_user = 'Batch ' || :batchId || ' Archive Process' where school_of_record in (:schoolOfRecords) and REPORT_TYPE_CODE = :reportTypeFrom", nativeQuery=true)
	Integer archiveSchoolReports(List<String> schoolOfRecords, String reportTypeFrom, String reportTypeTo, long batchId);

	@Modifying
	@Query(value="delete from SCHOOL_REPORT where school_of_record in (:schoolOfRecords) and REPORT_TYPE_CODE = :reportType and UPDATE_DATE <= SYSDATE - 1", nativeQuery=true)
	Integer deleteSchoolReports(List<String> schoolOfRecords, String reportType);

}
