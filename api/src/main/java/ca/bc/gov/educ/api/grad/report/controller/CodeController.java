package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.service.CodeService;
import ca.bc.gov.educ.api.grad.report.util.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EducGradCodeApiConstants.GRAD_CODE_API_ROOT_MAPPING)
@EnableResourceServer
@CrossOrigin
@OpenAPIDefinition(info = @Info(title = "API for Code Tables Data.",
        description = "This API is for Reading Code Tables data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2",
                scopes = {"READ_GRAD_COUNTRY_CODE_DATA",
                        "READ_GRAD_PROVINCE_CODE_DATA",
                        "READ_GRAD_PROGRAM_CODE_DATA",
                        "READ_GRAD_UNGRAD_CODE_DATA",
                        "READ_GRAD_CERTIFICATE_CODE_DATA",
                        "READ_GRAD_MESSEGING_CODE_DATA",
                        "READ_GRAD_CAREER_PROGRAM_CODE_DATA"})})
public class CodeController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);
    
    private static final String REASON_CODE="Reason Code";
    private static final String STATUS_CODE="Status Code";
    private static final String REPORT_TYPE_CODE="Report Type Code";
    private static final String REQUIREMENT_TYPE_CODE="Requirement Type Code";
    private static final String CERTIFICATE_TYPE_CODE="Certificate Type Code";

    @Autowired
    CodeService codeService;

    @Autowired
    GradValidation validation;

    @Autowired
    ResponseHelper response;

    @GetMapping(EducGradCodeApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_CERTIFICATE)
    @Operation(summary = "Find all Certificate Types", description = "Get all Certificate Types", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradCertificateTypes>> getAllCertificateTypeCodeList() {
        logger.debug("getAllCertificateTypeCodeList : ");
        return response.GET(codeService.getAllCertificateTypeCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_CERTIFICATE)
    @Operation(summary = "Find a Certificate Type by code", description = "Get a Certificate Type by Code", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradCertificateTypes> getSpecificCertificateTypeCode(@PathVariable String certTypeCode) {
        logger.debug("getSpecificCertificateTypeCode : ");
        GradCertificateTypes gradResponse = codeService.getSpecificCertificateTypeCode(certTypeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }

    @PostMapping(EducGradCodeApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_CERTIFICATE_TYPE)
    @Operation(summary = "Create a Certificate Type", description = "Create a Certificate Type", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradCertificateTypes>> createGradCertificateTypes(
            @Valid @RequestBody GradCertificateTypes gradCertificateTypes) {
        logger.debug("createGradCertificateTypes : ");
        validation.requiredField(gradCertificateTypes.getCode(), CERTIFICATE_TYPE_CODE);
        validation.requiredField(gradCertificateTypes.getDescription(), "Certificate Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradCertificateTypes(gradCertificateTypes));
    }

    @PutMapping(EducGradCodeApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_CERTIFICATE_TYPE)
    @Operation(summary = "Update a Certificate Type", description = "Update a Certificate Type", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradCertificateTypes>> updateGradCertificateTypes(
            @Valid @RequestBody GradCertificateTypes gradCertificateTypes) {
        logger.info("updateGradCertificateTypes : ");
        validation.requiredField(gradCertificateTypes.getCode(), CERTIFICATE_TYPE_CODE);
        validation.requiredField(gradCertificateTypes.getDescription(), "Certificate Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradCertificateTypes(gradCertificateTypes));
    }

    @DeleteMapping(EducGradCodeApiConstants.GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_CERTIFICATE_TYPE)
    @Operation(summary = "Delete a Certificate Type", description = "Delete a Certificate", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradCertificateTypes(@Valid @PathVariable String certTypeCode) {
        logger.debug("deleteGradCertificateTypes : ");
        validation.requiredField(certTypeCode, CERTIFICATE_TYPE_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OAuth2AuthenticationDetails auth =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String accessToken = auth.getTokenValue();
        return response.DELETE(codeService.deleteGradCertificateTypes(certTypeCode, accessToken));
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_REPORT)
    @Operation(summary = "Find all Report Types", description = "Get all Report Types", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradReportTypes>> getAllReportTypeCodeList() {
        logger.debug("getAllReportTypeCodeList : ");
        return response.GET(codeService.getAllReportTypeCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_REPORT_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_REPORT)
    @Operation(summary = "Find a Report Type", description = "Get a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradReportTypes> getSpecificReportTypeCode(@PathVariable String reportTypeCode) {
        logger.debug("getSpecificReportTypeCode : ");
        GradReportTypes gradResponse = codeService.getSpecificReportTypeCode(reportTypeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }

    @PostMapping(EducGradCodeApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_REPORT_TYPE)
    @Operation(summary = "Create a Report Type", description = "Create a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradReportTypes>> createGradReportTypes(
            @Valid @RequestBody GradReportTypes gradReportTypes) {
        logger.debug("createGradReportTypes : ");
        validation.requiredField(gradReportTypes.getCode(), REPORT_TYPE_CODE);
        validation.requiredField(gradReportTypes.getDescription(), "Report Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradReportTypes(gradReportTypes));
    }

    @PutMapping(EducGradCodeApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_REPORT_TYPE)
    @Operation(summary = "Update a Report Type", description = "Update a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradReportTypes>> updateGradReportTypes(
            @Valid @RequestBody GradReportTypes gradReportTypes) {
        logger.info("updateGradReportTypes : ");
        validation.requiredField(gradReportTypes.getCode(), REPORT_TYPE_CODE);
        validation.requiredField(gradReportTypes.getDescription(), "Report Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradReportTypes(gradReportTypes));
    }

    @DeleteMapping(EducGradCodeApiConstants.GET_ALL_REPORT_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_REPORT_TYPE)
    @Operation(summary = "Delete a Report Type", description = "Delete a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradReportTypes(@Valid @PathVariable String reportTypeCode) {
        logger.debug("deleteGradReportTypes : ");
        validation.requiredField(reportTypeCode, REPORT_TYPE_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OAuth2AuthenticationDetails auth =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String accessToken = auth.getTokenValue();
        return response.DELETE(codeService.deleteGradReportTypes(reportTypeCode, accessToken));
    }
}
