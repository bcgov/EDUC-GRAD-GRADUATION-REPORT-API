package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsLightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolReportsLightRepository extends JpaRepository<SchoolReportsLightEntity, UUID>, JpaSpecificationExecutor<SchoolReportsLightEntity> {

	List<SchoolReportsLightEntity> findByReportTypeCode(String reportTypeCode);
	List<SchoolReportsLightEntity> findByReportTypeCodeAndSchoolOfRecord(String reportTypeCode, String schoolOfRecord);

}
