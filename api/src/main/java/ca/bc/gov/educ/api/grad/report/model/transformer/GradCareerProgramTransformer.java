package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCareerProgram;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCareerProgramEntity;
import ca.bc.gov.educ.api.grad.report.util.EducGradCodeApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradCareerProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradCareerProgram transformToDTO (GradCareerProgramEntity gradCareerProgramEntity) {
    	GradCareerProgram gradCareerProgram = modelMapper.map(gradCareerProgramEntity, GradCareerProgram.class);
        return gradCareerProgram;
    }

    public GradCareerProgram transformToDTO ( Optional<GradCareerProgramEntity> gradCareerProgramEntity ) {
    	GradCareerProgramEntity cae = new GradCareerProgramEntity();
        if (gradCareerProgramEntity.isPresent())
            cae = gradCareerProgramEntity.get();

        GradCareerProgram gradCareerProgram = modelMapper.map(cae, GradCareerProgram.class);
        return gradCareerProgram;
    }

	public List<GradCareerProgram> transformToDTO (Iterable<GradCareerProgramEntity> courseEntities ) {
		List<GradCareerProgram> programList = new ArrayList<GradCareerProgram>();
        for (GradCareerProgramEntity courseEntity : courseEntities) {
        	GradCareerProgram program = new GradCareerProgram();
        	program = modelMapper.map(courseEntity, GradCareerProgram.class);   
        	program.setStartDate(EducGradCodeApiUtils.parseTraxDate(program.getStartDate() != null ? program.getStartDate().toString():null));
        	program.setEndDate(EducGradCodeApiUtils.parseTraxDate(program.getEndDate() != null ? program.getEndDate().toString():null));
            programList.add(program);
        }
        return programList;
    }

    public GradCareerProgramEntity transformToEntity(GradCareerProgram gradCareerProgram) {
        GradCareerProgramEntity gradCareerProgramEntity = modelMapper.map(gradCareerProgram, GradCareerProgramEntity.class);
        return gradCareerProgramEntity;
    }
}
