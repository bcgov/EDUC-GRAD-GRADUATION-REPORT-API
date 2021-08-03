package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradCertificateTypesRepository extends JpaRepository<GradCertificateTypesEntity, String> {

    List<GradCertificateTypesEntity> findAll();

}
