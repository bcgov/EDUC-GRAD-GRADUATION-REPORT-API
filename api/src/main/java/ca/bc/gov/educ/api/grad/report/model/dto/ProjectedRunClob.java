package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectedRunClob {
    private List<GradRequirement> nonGradReasons;
    private boolean graduated;
}
