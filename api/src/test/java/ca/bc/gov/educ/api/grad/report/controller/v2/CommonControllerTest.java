package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;
import ca.bc.gov.educ.api.grad.report.service.v2.CommonService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommonControllerTest {

    @Mock
    private CommonService commonService;

    @InjectMocks
    private CommonController commonController;

    @Test
    public void testGetAllStudentCredentialDistributionList() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String credentialType = "E";
        final StudentSearchRequest req = new StudentSearchRequest();
        req.setPens(List.of("2131231213"));

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL", new Date());
        list.add(cred);

        Mockito.when(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,false)).thenReturn(list);
        commonController.getStudentCredentialsForUserRequestDisRun(credentialType,req);
        Mockito.verify(commonService).getStudentCredentialsForUserRequestDisRun(credentialType,req,false);
    }

    @Test
    public void testGetAllStudentCredentialDistributionListWithNullDistributionDate() {
        // UUID
        final UUID studentID = UUID.randomUUID();
        final String credentialType = "E";
        final StudentSearchRequest req = new StudentSearchRequest();
        req.setDistrictIds(List.of(UUID.randomUUID()));

        // Student Certificate Types
        final List<StudentCredentialDistribution> list = new ArrayList<>();
        final StudentCredentialDistribution cred = new StudentCredentialDistribution(UUID.randomUUID(),"BC2018-IND",studentID,"YED4","COMPL", new Date());
        list.add(cred);

        Mockito.when(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,req,true)).thenReturn(list);
        commonController.getStudentCredentialsForUserRequestDisRunWithNullDistributionDate(credentialType,req);
        Mockito.verify(commonService).getStudentCredentialsForUserRequestDisRun(credentialType,req,true);
    }
}
