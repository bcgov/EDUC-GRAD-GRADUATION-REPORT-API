package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradAlgorithmRules;
import ca.bc.gov.educ.api.grad.report.model.entity.GradAlgorithmRulesEntity;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class GradAlgorithmRulesTransformerTest {
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    GradAlgorithmRulesTransformer gradAlgorithmRulesTransformer;

    @Test
    public void testTransformToDTO() {
        GradAlgorithmRules gradAlgorithmRules = new GradAlgorithmRules();
        gradAlgorithmRules.setId(UUID.randomUUID());
        gradAlgorithmRules.setProgramCode("2018-en");
        gradAlgorithmRules.setRuleName("rule");
        gradAlgorithmRules.setRuleDescription("rule description");
        gradAlgorithmRules.setRuleImplementation("rule body implementation");
        gradAlgorithmRules.setIsActive("Y");

        GradAlgorithmRulesEntity gradAlgorithmRulesEntity = new GradAlgorithmRulesEntity();
        gradAlgorithmRulesEntity.setId(gradAlgorithmRules.getId());
        gradAlgorithmRulesEntity.setProgramCode(gradAlgorithmRules.getProgramCode());
        gradAlgorithmRulesEntity.setRuleName(gradAlgorithmRules.getRuleName());
        gradAlgorithmRulesEntity.setRuleDescription(gradAlgorithmRules.getRuleDescription());
        gradAlgorithmRulesEntity.setRuleImplementation(gradAlgorithmRules.getRuleImplementation());
        gradAlgorithmRulesEntity.setIsActive(gradAlgorithmRules.getIsActive());

        Mockito.when(modelMapper.map(gradAlgorithmRulesEntity, GradAlgorithmRules.class)).thenReturn(gradAlgorithmRules);
        var result = gradAlgorithmRulesTransformer.transformToDTO(gradAlgorithmRulesEntity);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(gradAlgorithmRulesEntity.getId());
        assertThat(result.getProgramCode()).isEqualTo(gradAlgorithmRulesEntity.getProgramCode());
        assertThat(result.getRuleName()).isEqualTo(gradAlgorithmRulesEntity.getRuleName());
        assertThat(result.getRuleDescription()).isEqualTo(gradAlgorithmRulesEntity.getRuleDescription());
        assertThat(result.getRuleImplementation()).isEqualTo(gradAlgorithmRulesEntity.getRuleImplementation());
    }

    @Test
    public void testTransformOptionalToDTO() {
        GradAlgorithmRules gradAlgorithmRules = new GradAlgorithmRules();
        gradAlgorithmRules.setId(UUID.randomUUID());
        gradAlgorithmRules.setProgramCode("2018-en");
        gradAlgorithmRules.setRuleName("rule");
        gradAlgorithmRules.setRuleDescription("rule description");
        gradAlgorithmRules.setRuleImplementation("rule body implementation");
        gradAlgorithmRules.setIsActive("Y");

        GradAlgorithmRulesEntity gradAlgorithmRulesEntity = new GradAlgorithmRulesEntity();
        gradAlgorithmRulesEntity.setId(gradAlgorithmRules.getId());
        gradAlgorithmRulesEntity.setProgramCode(gradAlgorithmRules.getProgramCode());
        gradAlgorithmRulesEntity.setRuleName(gradAlgorithmRules.getRuleName());
        gradAlgorithmRulesEntity.setRuleDescription(gradAlgorithmRules.getRuleDescription());
        gradAlgorithmRulesEntity.setRuleImplementation(gradAlgorithmRules.getRuleImplementation());
        gradAlgorithmRulesEntity.setIsActive(gradAlgorithmRules.getIsActive());

        Mockito.when(modelMapper.map(gradAlgorithmRulesEntity, GradAlgorithmRules.class)).thenReturn(gradAlgorithmRules);
        var result = gradAlgorithmRulesTransformer.transformToDTO(Optional.of(gradAlgorithmRulesEntity));
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(gradAlgorithmRulesEntity.getId());
        assertThat(result.getProgramCode()).isEqualTo(gradAlgorithmRulesEntity.getProgramCode());
        assertThat(result.getRuleName()).isEqualTo(gradAlgorithmRulesEntity.getRuleName());
        assertThat(result.getRuleDescription()).isEqualTo(gradAlgorithmRulesEntity.getRuleDescription());
        assertThat(result.getRuleImplementation()).isEqualTo(gradAlgorithmRulesEntity.getRuleImplementation());
    }

    @Test
    public void testTransformToEntity() {
        GradAlgorithmRules gradAlgorithmRules = new GradAlgorithmRules();
        gradAlgorithmRules.setId(UUID.randomUUID());
        gradAlgorithmRules.setProgramCode("2018-en");
        gradAlgorithmRules.setRuleName("rule");
        gradAlgorithmRules.setRuleDescription("rule description");
        gradAlgorithmRules.setRuleImplementation("rule body implementation");
        gradAlgorithmRules.setIsActive("Y");

        GradAlgorithmRulesEntity gradAlgorithmRulesEntity = new GradAlgorithmRulesEntity();
        gradAlgorithmRulesEntity.setId(gradAlgorithmRules.getId());
        gradAlgorithmRulesEntity.setProgramCode(gradAlgorithmRules.getProgramCode());
        gradAlgorithmRulesEntity.setRuleName(gradAlgorithmRules.getRuleName());
        gradAlgorithmRulesEntity.setRuleDescription(gradAlgorithmRules.getRuleDescription());
        gradAlgorithmRulesEntity.setRuleImplementation(gradAlgorithmRules.getRuleImplementation());
        gradAlgorithmRulesEntity.setIsActive(gradAlgorithmRules.getIsActive());

        Mockito.when(modelMapper.map(gradAlgorithmRules, GradAlgorithmRulesEntity.class)).thenReturn(gradAlgorithmRulesEntity);
        var result = gradAlgorithmRulesTransformer.transformToEntity(gradAlgorithmRules);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(gradAlgorithmRules.getId());
        assertThat(result.getProgramCode()).isEqualTo(gradAlgorithmRules.getProgramCode());
        assertThat(result.getRuleName()).isEqualTo(gradAlgorithmRules.getRuleName());
        assertThat(result.getRuleDescription()).isEqualTo(gradAlgorithmRules.getRuleDescription());
        assertThat(result.getRuleImplementation()).isEqualTo(gradAlgorithmRules.getRuleImplementation());
    }
}
