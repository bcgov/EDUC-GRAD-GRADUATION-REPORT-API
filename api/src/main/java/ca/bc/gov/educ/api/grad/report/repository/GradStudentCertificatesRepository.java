package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType) from GradStudentCertificatesEntity c inner join GradCertificateTypesEntity cert on cert.code = c.gradCertificateTypeCode where c.documentStatusCode=:documentStatusCode and c.distributionDate is null")
	List<StudentCredentialDistribution> findByDocumentStatusCodeAndDistributionDate(@Param("documentStatusCode") String documentStatusCode);

	@Query(value="update STUDENT_CERTIFICATE set DISTRIBUTION_DATE=getdate() WHERE GRADUATION_STUDENT_RECORD_ID=:studentID and CERTIFICATE_TYPE_CODE=:credentialTypeCode",nativeQuery = true)
    void updateStudentCredential(UUID studentID, String credentialTypeCode);
}
