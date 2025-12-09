package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TranscriptTypesRepository extends JpaRepository<TranscriptTypesEntity, String> {

    List<TranscriptTypesEntity> findAll();

    @Query("select c from TranscriptTypesEntity c where c.code=:code")
    TranscriptTypesEntity findByTranscriptCode(String code);

    @Query("select t from TranscriptTypesEntity t join StudentTranscriptEntity c on t.code = c.transcriptTypeCode where c.documentStatusCode='COMPL' and c.graduationStudentRecordId=:graduationStudentRecordId")
    List<TranscriptTypesEntity> getStudentTranscriptTypes(UUID graduationStudentRecordId);
}
