package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository()
public interface DistrictReportRepository extends JpaRepository<DistrictReportEntity, UUID>, JpaSpecificationExecutor<DistrictReportEntity> {
	List<DistrictReportEntity> deleteAllByReportTypeCode(String reportTypeCode);
	Optional<DistrictReportEntity> findByDistrictIdAndReportTypeCode(UUID districtId, String reportTypeCode);
}
