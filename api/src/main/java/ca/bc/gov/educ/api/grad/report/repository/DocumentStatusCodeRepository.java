package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.DocumentStatusCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentStatusCodeRepository extends JpaRepository<DocumentStatusCodeEntity, String> {

    List<DocumentStatusCodeEntity> findAll();

    @Query("select c from DocumentStatusCodeEntity c where c.code=:code")
    DocumentStatusCodeEntity findByDocumentStatusCode(String code);

}
