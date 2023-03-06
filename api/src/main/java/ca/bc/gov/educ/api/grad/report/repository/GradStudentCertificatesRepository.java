package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GradStudentCertificatesRepository extends JpaRepository<GradStudentCertificatesEntity, UUID> {

	List<GradStudentCertificatesEntity> findByStudentID(UUID studentID);
	List<GradStudentCertificatesEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);
	List<GradStudentCertificatesEntity> findByStudentIDAndGradCertificateTypeCodeIn(UUID studentID, List<String> gradCertificateTypeCodes);

	Optional<GradStudentCertificatesEntity> findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(UUID studentID,String certificateTypeCode,String documentStatusCode);
   	
   	@Query("select c from GradStudentCertificatesEntity c where c.gradCertificateTypeCode=:certificateType")
	List<GradStudentCertificatesEntity> existsByCertificateTypeCode(String certificateType);

	long deleteByStudentID(UUID studentID);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from GradStudentCertificatesEntity c inner join GradCertificateTypesEntity cert on cert.code = c.gradCertificateTypeCode where c.documentStatusCode=:documentStatusCode and c.distributionDate is null")
	List<StudentCredentialDistribution> findByDocumentStatusCodeAndNullDistributionDate(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from GradStudentCertificatesEntity c inner join GradCertificateTypesEntity cert on cert.code = c.gradCertificateTypeCode where c.documentStatusCode=:documentStatusCode")
	List<StudentCredentialDistribution> findByDocumentStatusCode(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from GradStudentCertificatesEntity c inner join GradCertificateTypesEntity cert on cert.code = c.gradCertificateTypeCode  where c.studentID in (:subList) and c.documentStatusCode='COMPL'")
	List<StudentCredentialDistribution> findRecordsForUserRequest(List<UUID> subList);

	@Query(
		value="select\n" +
				"    CAST(c.GRADUATION_STUDENT_RECORD_ID AS VARCHAR2(100)) as GRADUATION_STUDENT_RECORD_ID from student_certificate c where c.document_status_code='COMPL' and c.distribution_date is null\n" +
				"union\n" +
				"select\n" +
				"    CAST(t.GRADUATION_STUDENT_RECORD_ID AS VARCHAR2(100)) as GRADUATION_STUDENT_RECORD_ID from student_transcript t where exists (\n" +
				"        select 'x' from student_certificate where GRADUATION_STUDENT_RECORD_ID = t.GRADUATION_STUDENT_RECORD_ID and document_status_code='COMPL' and distribution_date is null )\n" +
				"union\n" +
				"select\n" +
				"    CAST(t.GRADUATION_STUDENT_RECORD_ID AS VARCHAR2(100)) as GRADUATION_STUDENT_RECORD_ID from student_transcript t where (t.document_status_code='COMPL' and t.distribution_date < t.update_date)\n", nativeQuery = true
	)
	List<String> findStudentIdForSchoolYearEndReport();

	@Query(
			value="select\n" +
					"    CAST(c.GRADUATION_STUDENT_RECORD_ID AS VARCHAR2(100)) as GRADUATION_STUDENT_RECORD_ID from student_certificate c where c.document_status_code='COMPL' and c.distribution_date is null\n" +
					"union\n" +
					"select\n" +
					"    CAST(t.GRADUATION_STUDENT_RECORD_ID AS VARCHAR2(100)) as GRADUATION_STUDENT_RECORD_ID from student_transcript t where exists (\n" +
					"        select 'x' from student_certificate where GRADUATION_STUDENT_RECORD_ID = t.GRADUATION_STUDENT_RECORD_ID and document_status_code='COMPL' and distribution_date is null)", nativeQuery = true
	)
	List<String> findStudentIdForSchoolReport();
}
