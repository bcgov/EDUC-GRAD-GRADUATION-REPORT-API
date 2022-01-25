package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentCertificatesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GradStudentTranscriptsRepository extends JpaRepository<GradStudentTranscriptsEntity, UUID> {

	List<GradStudentTranscriptsEntity> findByStudentID(UUID studentID);
	List<GradStudentTranscriptsEntity> findByStudentIDAndDocumentStatusCodeNot(UUID studentID,String documentStatusCode);

	Optional<GradStudentTranscriptsEntity> findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCode(UUID studentID,String transcriptTypeCode,String documentStatusCode);
	Optional<GradStudentTranscriptsEntity> findByStudentIDAndTranscriptTypeCodeAndDocumentStatusCodeNot(UUID studentID, String transcriptTypeCode, String documentStatusCode);
   	@Query("select c from GradStudentTranscriptsEntity c where c.transcriptTypeCode=:transcriptType")
	List<GradStudentTranscriptsEntity> existsByTranscriptTypeCode(String transcriptType);

	long deleteByStudentID(UUID studentID);
}
