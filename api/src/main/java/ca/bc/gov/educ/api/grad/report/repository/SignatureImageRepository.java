package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradReportSignatureImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SignatureImageRepository extends JpaRepository<GradReportSignatureImageEntity, UUID> {

    @Query("select c from GradReportSignatureImageEntity c where c.gradReportSignatureCode=:signatureCode")
    GradReportSignatureImageEntity findBySignatureCode(String signatureCode);

}
