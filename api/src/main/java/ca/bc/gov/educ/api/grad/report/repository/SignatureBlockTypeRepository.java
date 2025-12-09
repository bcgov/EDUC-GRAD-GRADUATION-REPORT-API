package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.SignatureBlockTypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignatureBlockTypeRepository extends JpaRepository<SignatureBlockTypeCodeEntity, String> {

    @Query("select c from SignatureBlockTypeCodeEntity c where c.signatureBlockType=:code")
    SignatureBlockTypeCodeEntity findBySignatureBlockTypeCode(String code);
}