package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Student Transcript Validation repository.
 */
@Repository
public interface GradStudentTranscriptValidationRepository extends CrudRepository<GradStudentTranscriptValidationEntity, GradStudentTranscriptValidationKey> {

}
