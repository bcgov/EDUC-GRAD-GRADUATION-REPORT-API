package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.grad.report.model.entity.DocumentStatusCodeEntity;

@Repository
public interface DocumentStatusCodeRepository extends JpaRepository<DocumentStatusCodeEntity, String> {

    List<DocumentStatusCodeEntity> findAll();

}
