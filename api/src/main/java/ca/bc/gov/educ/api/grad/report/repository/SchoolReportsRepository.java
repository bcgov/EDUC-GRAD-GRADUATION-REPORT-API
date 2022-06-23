package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolReportDistribution;
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

	List<SchoolReportsEntity> findBySchoolOfRecord(String schoolOfRecord);

	Optional<SchoolReportsEntity> findBySchoolOfRecordAndReportTypeCode(String schoolOfRecord,String reportTypeCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.SchoolReportDistribution(c.id,c.reportTypeCode,c.schoolOfRecord) from SchoolReportsEntity c where c.distributionDate is null or c.distributionDate < c.updateDate")
	List<SchoolReportDistribution>  findSchoolReportsForPosting();
}
