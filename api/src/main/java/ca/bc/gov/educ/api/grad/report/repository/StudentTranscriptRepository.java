package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.StudentTranscriptEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.TranscriptTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentTranscriptRepository extends JpaRepository<StudentTranscriptEntity, UUID> {

    @Query("select c from StudentTranscriptEntity c where c.graduationStudentRecordId=:graduationStudentRecordId")
    StudentTranscriptEntity findByGraduationStudentRecordId(UUID graduationStudentRecordId);

    @Query("select t from TranscriptTypesEntity t join StudentTranscriptEntity c on t.code = c.transcriptTypeCode where c.documentStatusCode='COMPL' and c.graduationStudentRecordId=:graduationStudentRecordId")
    List<TranscriptTypesEntity> getStudentTranscriptTypes(UUID graduationStudentRecordId);

    @Query("select max(c.transcriptUpdateDate) as updateDate from StudentTranscriptEntity c where c.graduationStudentRecordId=:graduationStudentRecordId")
    Optional<Date> getTranscriptLastUpdateDate(UUID graduationStudentRecordId);
}