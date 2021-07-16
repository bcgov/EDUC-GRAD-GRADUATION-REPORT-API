package ca.bc.gov.educ.api.grad.report.repository;

import ca.bc.gov.educ.api.grad.report.model.entity.StudentNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentNoteRepository extends JpaRepository<StudentNoteEntity, UUID> {

	List<StudentNoteEntity> findByPen(String pen);
}
