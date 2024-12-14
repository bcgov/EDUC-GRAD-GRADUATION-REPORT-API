package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.DistrictReportLightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository()
public interface DistrictReportLightRepository extends JpaRepository<DistrictReportLightEntity, UUID>, JpaSpecificationExecutor<DistrictReportLightEntity> {

}
