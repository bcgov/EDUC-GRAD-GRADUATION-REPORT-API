package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
