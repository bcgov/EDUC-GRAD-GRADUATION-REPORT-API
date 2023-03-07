package ca.bc.gov.educ.api.grad.report.model.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public class UUIDEntity {

    @Id
    protected UUID graduationStudentRecordId;

}
