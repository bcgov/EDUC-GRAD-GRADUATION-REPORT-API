package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("schoolReportsLightRepositoryV2")
public interface SchoolReportLightRepository extends JpaRepository<SchoolReportLightEntity, UUID>, JpaSpecificationExecutor<SchoolReportLightEntity> {
}
