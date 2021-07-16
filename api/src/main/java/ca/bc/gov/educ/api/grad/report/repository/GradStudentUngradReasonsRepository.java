package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentUngradReasonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradStudentUngradReasonsRepository extends JpaRepository<GradStudentUngradReasonsEntity, UUID> {

	List<GradStudentUngradReasonsEntity> findByPen(String pen);

	@Query("select c from GradStudentUngradReasonsEntity c where c.ungradReasonCode=:reasonCode")
	List<GradStudentUngradReasonsEntity> existsByReasonCode(String reasonCode);

	List<GradStudentUngradReasonsEntity> findByStudentID(UUID studentID);
}
