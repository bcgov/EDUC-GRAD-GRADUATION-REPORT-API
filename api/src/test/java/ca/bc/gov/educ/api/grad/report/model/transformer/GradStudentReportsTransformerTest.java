package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class GradStudentReportsTransformerTest {
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    GradStudentReportsTransformer gradStudentReportsTransformer;

    @Test
    public void testTransformToDTOWithList() {
        // List
        GradStudentReports gradStudentReports = new GradStudentReports();
        gradStudentReports.setId(UUID.randomUUID());
        gradStudentReports.setStudentID(UUID.randomUUID());
        gradStudentReports.setPen("123456789");
        gradStudentReports.setGradReportTypeCode("rptCode");
        gradStudentReports.setReport("report body");

        GradStudentReportsEntity gradStudentReportsEntity = new GradStudentReportsEntity();
        gradStudentReportsEntity.setId(gradStudentReports.getId());
        gradStudentReportsEntity.setStudentID(gradStudentReports.getStudentID());
        gradStudentReportsEntity.setPen(gradStudentReports.getPen());
        gradStudentReportsEntity.setGradReportTypeCode(gradStudentReports.getGradReportTypeCode());
        gradStudentReportsEntity.setReport(gradStudentReports.getReport());

        Mockito.when(modelMapper.map(gradStudentReportsEntity, GradStudentReports.class)).thenReturn(gradStudentReports);
        var result = gradStudentReportsTransformer.transformToDTO(Arrays.asList(gradStudentReportsEntity));
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        GradStudentReports responseGradStudentReports = result.get(0);
        assertThat(responseGradStudentReports.getId()).isEqualTo(gradStudentReportsEntity.getId());
        assertThat(responseGradStudentReports.getStudentID()).isEqualTo(gradStudentReportsEntity.getStudentID());
        assertThat(responseGradStudentReports.getPen()).isEqualTo(gradStudentReportsEntity.getPen());
        assertThat(responseGradStudentReports.getGradReportTypeCode()).isEqualTo(gradStudentReportsEntity.getGradReportTypeCode());
        assertThat(responseGradStudentReports.getReport()).isEqualTo(gradStudentReportsEntity.getReport());
    }
}
