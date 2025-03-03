package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("schoolReportsRepositoryV2")
public interface SchoolReportRepository extends JpaRepository<SchoolReportEntity, UUID>, JpaSpecificationExecutor<SchoolReportEntity> {

	List<SchoolReportEntity> deleteAllByReportTypeCode(String reportTypeCode);

	Optional<SchoolReportEntity> findBySchoolOfRecordIdAndReportTypeCode(UUID schoolOfRecordId, String reportTypeCode);

	Integer countBySchoolOfRecordIdInAndReportTypeCode(List<UUID> schoolOfRecordIds, String reportType);

	Integer countByReportTypeCode(String reportType);

	@Modifying
	@Query(value="update SCHOOL_REPORT set REPORT_TYPE_CODE = :reportTypeTo, update_date = SYSDATE, update_user = 'Batch ' || :batchId || ' Archive Process' where school_of_record_id in (:schoolOfRecordIds) and REPORT_TYPE_CODE = :reportTypeFrom", nativeQuery=true)
	Integer archiveSchoolReports(List<UUID> schoolOfRecordIds, String reportTypeFrom, String reportTypeTo, long batchId);

	@Modifying
	@Query(value="delete from SCHOOL_REPORT where SCHOOL_REPORT_ID not in (:schoolReportGuids) and school_of_record_id in (:schoolOfRecordIds) and REPORT_TYPE_CODE = :archivedReportType", nativeQuery=true)
	Integer deleteSchoolOfRecordsNotMatchingSchoolReports(List<UUID> schoolReportGuids, List<UUID> schoolOfRecordIds, String archivedReportType);

}
