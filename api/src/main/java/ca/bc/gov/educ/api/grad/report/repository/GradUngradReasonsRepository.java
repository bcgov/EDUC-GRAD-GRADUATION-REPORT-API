package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradUngradReasonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradUngradReasonsRepository extends JpaRepository<GradUngradReasonsEntity, String> {

    List<GradUngradReasonsEntity> findAll();

}
