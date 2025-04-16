package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptsEntity;
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
public interface GradStudentTranscriptsRepository extends JpaRepository<GradStudentTranscriptsEntity, UUID> {

	List<GradStudentTranscriptsEntity> findByStudentID(UUID studentID);
	List<GradStudentTranscriptsEntity> findByStudentIDAndDocumentStatusCode(UUID studentID,String documentStatusCode);

	List<GradStudentTranscriptsEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);
	Optional<GradStudentTranscriptsEntity> findByStudentIDAndTranscriptTypeCode(UUID studentID,String transcriptTypeCode);

	Optional<GradStudentTranscriptsEntity> findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(UUID studentID,String transcriptTypeCode,String documentStatusCode);
	Optional<GradStudentTranscriptsEntity> findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(UUID studentID, String transcriptTypeCode, String documentStatusCode);
   	@Query("select c from GradStudentTranscriptsEntity c where c.transcriptTypeCode=:transcriptType")
	List<GradStudentTranscriptsEntity> existsByTranscriptTypeCode(@Param("transcriptType") String transcriptType);

	long deleteByStudentID(UUID studentID);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.documentStatusCode=:documentStatusCode and c.distributionDate is null")
	List<StudentCredentialDistribution>  findByDocumentStatusCodeAndDistributionDate(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where (c.documentStatusCode=:documentStatusCode and c.distributionDate is null) or (c.documentStatusCode=:documentStatusCode and c.distributionDate < c.updateDate)")
	List<StudentCredentialDistribution>  findByDocumentStatusCodeAndDistributionDateYearly(@Param("documentStatusCode") String documentStatusCode);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.studentID in (:subList) and c.distributionDate is null")
	List<StudentCredentialDistribution> findByReportsForYearly(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.studentID in (:subList) and c.documentStatusCode='COMPL'")
	List<StudentCredentialDistribution> findRecordsForUserRequest(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.studentID in (:subList)")
	List<StudentCredentialDistribution> findRecordsForUserRequestByStudentIdOnly(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.studentID in (:subList) and c.documentStatusCode='COMPL' and c.distributionDate is null")
	List<StudentCredentialDistribution> findRecordsWithNullDistributionDateForUserRequest(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,tran.paperType,c.documentStatusCode,c.distributionDate) from GradStudentTranscriptsEntity c inner join TranscriptTypesEntity tran on tran.code = c.transcriptTypeCode  where c.studentID in (:subList) and c.distributionDate is null")
	List<StudentCredentialDistribution> findRecordsWithNullDistributionDateForUserRequestByStudentIdOnly(List<UUID> subList);

	@Query("select new ca.bc.gov.educ.api.grad.report.model.dto.SchoolStudentCredentialDistribution(c.id,c.transcriptTypeCode,c.studentID,c.documentStatusCode) from GradStudentTranscriptsEntity c where c.transcriptUpdateDate is null or c.transcriptUpdateDate < c.updateDate")
	List<SchoolStudentCredentialDistribution> findByTranscriptUpdateDate();

	@Modifying
	@Query(value="update GradStudentTranscriptsEntity t set t.updateUser = :updateUser, t.updateDate = :currentDate, t.distributionDate = :currentDate where t.documentStatusCode= :documentStatusCode and t.studentID in :studentIDs")
	Integer updateStudentDistributionData(Date currentDate, String updateUser, String documentStatusCode, List<UUID> studentIDs);

}
