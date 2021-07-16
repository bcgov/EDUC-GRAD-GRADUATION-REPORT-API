package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradAlgorithmRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradAlgorithmRulesRepository extends JpaRepository<GradAlgorithmRulesEntity, UUID> {
   	
   	@Query("select c from GradAlgorithmRulesEntity c where c.programCode=:programCode and c.isActive='Y'")
	List<GradAlgorithmRulesEntity> getAlgorithmRulesByProgramCode(String programCode);
}
