package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentStatus;
import ca.bc.gov.educ.api.grad.report.model.entity.StudentStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class StudentStatusTransformer {

    @Autowired
    ModelMapper modelMapper;

    public StudentStatus transformToDTO (StudentStatusEntity studentStatusEntity) {
    	StudentStatus StudentStatus = modelMapper.map(studentStatusEntity, StudentStatus.class);
        return StudentStatus;
    }

    public StudentStatus transformToDTO ( Optional<StudentStatusEntity> studentStatusEntity ) {
    	StudentStatusEntity cae = new StudentStatusEntity();
        if (studentStatusEntity.isPresent())
            cae = studentStatusEntity.get();

        StudentStatus StudentStatus = modelMapper.map(cae, StudentStatus.class);
        return StudentStatus;
    }

	public List<StudentStatus> transformToDTO (Iterable<StudentStatusEntity> studentStatusEntities ) {
		List<StudentStatus> studentStatusList = new ArrayList<StudentStatus>();
        for (StudentStatusEntity StudentStatusEntity : studentStatusEntities) {
        	StudentStatus StudentStatus = new StudentStatus();
        	StudentStatus = modelMapper.map(StudentStatusEntity, StudentStatus.class);            
        	studentStatusList.add(StudentStatus);
        }
        return studentStatusList;
    }

    public StudentStatusEntity transformToEntity(StudentStatus studentStatus) {
        StudentStatusEntity studentStatusEntity = modelMapper.map(studentStatus, StudentStatusEntity.class);
        return studentStatusEntity;
    }
}
