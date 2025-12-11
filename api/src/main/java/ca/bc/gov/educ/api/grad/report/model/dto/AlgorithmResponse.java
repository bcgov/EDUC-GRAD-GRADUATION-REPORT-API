package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class AlgorithmResponse {

    private GraduationStudentRecord graduationStudentRecord;
    private List<StudentOptionalProgram> studentOptionalProgram;
    private ExceptionMessage exception;
}
