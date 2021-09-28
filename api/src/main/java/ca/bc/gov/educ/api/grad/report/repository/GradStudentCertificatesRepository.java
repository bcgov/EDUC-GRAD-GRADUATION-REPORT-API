package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;

@Repository
public interface GradStudentCertificatesRepository extends JpaRepository<GradStudentCertificatesEntity, UUID> {

	List<GradStudentCertificatesEntity> findByStudentID(UUID studentID);
	List<GradStudentCertificatesEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);

	Optional<GradStudentCertificatesEntity> findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(UUID studentID,String certificateTypeCode,String documentStatusCode);
   	
   	@Query("select c from GradStudentCertificatesEntity c where c.gradCertificateTypeCode=:certificateType")
	List<GradStudentCertificatesEntity> existsByCertificateTypeCode(String certificateType);

	long deleteByStudentID(UUID studentID);
}
