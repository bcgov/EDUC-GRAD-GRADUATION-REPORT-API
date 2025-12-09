package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.StudentCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentCertificateRepository extends JpaRepository<StudentCertificateEntity, UUID> {

	List<StudentCertificateEntity> findByStudentID(UUID studentID);
	List<StudentCertificateEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID, String documentStatusCode);
	List<StudentCertificateEntity> findByStudentIDAndGradCertificateTypeCodeIn(UUID studentID, List<String> gradCertificateTypeCodes);

	Optional<StudentCertificateEntity> findByStudentIDAndGradCertificateTypeCodeAndDocumentStatusCode(UUID studentID, String certificateTypeCode, String documentStatusCode);
   	
   	@Query("select c from StudentCertificateEntity c where c.gradCertificateTypeCode=:certificateType")
	List<StudentCertificateEntity> existsByCertificateTypeCode(String certificateType);

	long deleteByStudentID(UUID studentID);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode where c.documentStatusCode=:documentStatusCode and c.distributionDate is null")
	List<StudentCredentialDistribution> findByDocumentStatusCodeAndNullDistributionDate(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode where c.documentStatusCode=:documentStatusCode")
	List<StudentCredentialDistribution> findByDocumentStatusCode(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode  where c.studentID in (:subList)")
	List<StudentCredentialDistribution> findRecordsForUserRequest(List<UUID> subList);
	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode  where c.studentID in (:subList) and c.distributionDate is null")
	List<StudentCredentialDistribution> findRecordsForUserRequestAndNullDistributionDate(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode  where c.studentID in (:subList) and c.distributionDate is null")
	List<StudentCredentialDistribution> findRecordsWithNullDistributionDateForUserRequest(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode  where c.studentID in (:subList) and c.distributionDate is null")
	List<StudentCredentialDistribution> findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.gradCertificateTypeCode,c.studentID,cert.paperType,c.documentStatusCode,c.distributionDate) from StudentCertificateEntity c inner join CertificateTypeCodeEntity cert on cert.certificateTypeCode = c.gradCertificateTypeCode  where c.studentID in (:subList)")
	List<StudentCredentialDistribution> findRecordsForUserRequestByStudentIdOnly(List<UUID> subList);

	@Modifying
	@Query(value="update StudentCertificateEntity t set t.updateUser = :updateUser, t.updateDate = :currentDate, t.distributionDate = CASE WHEN t.distributionDate = null and :activityCode != 'USERDISTRC' THEN :currentDate ELSE t.distributionDate END where t.documentStatusCode= :documentStatusCode and t.gradCertificateTypeCode= :certificateTypeCode and t.studentID in :studentIDs")
	Integer updateStudentDistributionData(Date currentDate, String updateUser, String documentStatusCode, String certificateTypeCode, String activityCode, List<UUID> studentIDs);

	@Query("select max(c.distributionDate) as distributionDate from StudentCertificateEntity c where c.studentID=:graduationStudentRecordId")
	Optional<Date> getCertificateDistributionDate(UUID graduationStudentRecordId);
	
}
