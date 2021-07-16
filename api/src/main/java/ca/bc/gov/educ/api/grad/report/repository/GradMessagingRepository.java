package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradMessagingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradMessagingRepository extends JpaRepository<GradMessagingEntity, String> {

    List<GradMessagingEntity> findAll();

	Optional<GradMessagingEntity> findByProgramCodeAndMessageType(String pgmCode, String msgType);

}
