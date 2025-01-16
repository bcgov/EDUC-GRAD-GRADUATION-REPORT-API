package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
}
