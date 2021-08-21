package ca.bc.gov.educ.api.grad.report.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.grad.report.model.entity.ProgramCertificateEntity;

@Repository
public interface ProgramCertificateRepository extends JpaRepository<ProgramCertificateEntity, UUID> {

    List<ProgramCertificateEntity> findAll();
    
    @Query(value="select * from program_certificate where program_code = :programCode and school_funding_group_code = :schoolFundingCode "
    		+ "union all select * from program_certificate where program_code=:optionalProgramCode and school_funding_group_code = ' '",nativeQuery=true)
    List<ProgramCertificateEntity> findCertificates(String programCode,String schoolFundingCode, String optionalProgramCode);

}
