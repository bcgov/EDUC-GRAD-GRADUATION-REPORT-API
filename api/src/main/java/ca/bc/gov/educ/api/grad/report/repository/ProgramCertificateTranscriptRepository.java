package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;
import java.util.UUID;

import ca.bc.gov.educ.api.grad.report.model.entity.ProgramCertificateTranscriptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ProgramCertificateTranscriptRepository extends JpaRepository<ProgramCertificateTranscriptEntity, UUID> {

    List<ProgramCertificateTranscriptEntity> findAll();
    
    @Query(value="select * from program_certificate_transcript where graduation_program_code = :programCode and school_category_code = :schoolFundingCode and certificate_type_code is not null"
    		+ "union all select * from program_certificate_transcript where graduation_program_code=:optionalProgramCode and school_category_code is null",nativeQuery=true)
    List<ProgramCertificateTranscriptEntity> findCertificates(@Param("programCode") String programCode,@Param("schoolFundingCode") String schoolFundingCode, @Param("optionalProgramCode") String optionalProgramCode);

    @Query(value="select * from program_certificate_transcript where graduation_program_code = :programCode and school_category_code = :schoolFundingCode",nativeQuery = true)
    ProgramCertificateTranscriptEntity findTranscript(@Param("programCode") String programCode,@Param("schoolFundingCode") String schoolFundingCode);
}
