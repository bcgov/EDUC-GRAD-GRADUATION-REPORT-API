package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.CertificateTypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradCertificateTypesRepository extends JpaRepository<CertificateTypeCodeEntity, String> {

    List<CertificateTypeCodeEntity> findAll();

}
