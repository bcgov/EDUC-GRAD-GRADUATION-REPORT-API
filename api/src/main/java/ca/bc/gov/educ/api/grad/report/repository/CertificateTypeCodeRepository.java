package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.CertificateTypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CertificateTypeCodeRepository extends JpaRepository<CertificateTypeCodeEntity, String> {

    @Query("select c from CertificateTypeCodeEntity c where c.code=:code")
    CertificateTypeCodeEntity findByCertificateCode(String code);

    @Query("select t from CertificateTypeCodeEntity t join StudentCertificateEntity c on t.code = c.gradCertificateTypeCode where c.documentStatusCode='COMPL' and c.studentID=:graduationStudentRecordId")
    List<CertificateTypeCodeEntity> getStudentCertificateTypes(UUID graduationStudentRecordId);

    @Query("select t from CertificateTypeCodeEntity t join StudentCertificateEntity c on t.code = c.gradCertificateTypeCode where c.documentStatusCode='COMPL' and c.studentID=:graduationStudentRecordId and c.gradCertificateTypeCode in (:certificateTypeCode)")
    List<CertificateTypeCodeEntity> getStudentCertificateTypes(UUID graduationStudentRecordId, List<String> certificateTypeCode);
}