package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationKey;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationReadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Student Transcript Validation repository.
 */
@Repository
public interface GradStudentTranscriptValidationReadRepository extends PagingAndSortingRepository<GradStudentTranscriptValidationReadEntity, GradStudentTranscriptValidationKey> {

    Page<GradStudentTranscriptValidationReadEntity> findAllByBatchIdIsNull(Pageable pageable);
    Integer countGradStudentTranscriptValidationReadEntitiesByBatchIdIsNull();

}
