package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.StudentSearchRequest;

import ca.bc.gov.educ.api.grad.report.service.v2.CommonService;
import ca.bc.gov.educ.api.grad.report.util.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commonControllerV2")
@RequestMapping(EducGradReportApiConstants.GRAD_REPORT_API_V2_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for Common v2 endpoints.", description = "This API is for Reading Common endpoints.", version = "2"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_CERTIFICATE_DATA"})})
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    private static final String BEARER = "Bearer ";
    final CommonService commonService;
    
    final GradValidation validation;
    
    final ResponseHelper response;

    @Autowired
    public CommonController(CommonService commonService, GradValidation validation, ResponseHelper response) {
        this.commonService = commonService;
        this.validation = validation;
        this.response = response;
    }

    @PostMapping(EducGradReportApiConstants.USER_REQUEST_DIS_RUN)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All Student Transcripts/Certificates for User Req Distribution", description = "Read All Student Credentials for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getStudentCredentialsForUserRequestDisRun(
            @PathVariable String credentialType, @RequestBody StudentSearchRequest studentSearchRequest,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getStudentCredentialsForUserRequestDisRun : ");
        boolean isPenNumberSearch = studentSearchRequest.getPens() != null && !studentSearchRequest.getPens().isEmpty()
                && !studentSearchRequest.getPens().stream().filter(StringUtils::isNotBlank).toList().isEmpty();
        boolean onlyWithNullDistributionDate = !isPenNumberSearch && studentSearchRequest.getGradDateFrom() == null && studentSearchRequest.getGradDateTo() == null && !StringUtils.equalsAnyIgnoreCase(credentialType, "OT", "RT");
        return response.GET(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,studentSearchRequest,onlyWithNullDistributionDate,accessToken.replace(BEARER, "")));
    }

    @PostMapping(EducGradReportApiConstants.USER_REQUEST_DIS_RUN_WITH_NULL_DISTRIBUTION_DATE)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All Student Transcripts/Certificates with Null Distribution Date for User Req Distribution", description = "Read All Student Credentials with Null Distribution Date for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getStudentCredentialsForUserRequestDisRunWithNullDistributionDate(
            @PathVariable String credentialType, @RequestBody StudentSearchRequest studentSearchRequest,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getStudentCredentialsForUserRequestDisRunWithNullDistributionDate : ");
        boolean isPenNumberSearch = studentSearchRequest.getPens()!= null && !studentSearchRequest.getPens().isEmpty()
                && !studentSearchRequest.getPens().stream().filter(StringUtils::isNotBlank).toList().isEmpty();
        return response.GET(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,studentSearchRequest,!isPenNumberSearch,accessToken.replace(BEARER, "")));
    }
}
