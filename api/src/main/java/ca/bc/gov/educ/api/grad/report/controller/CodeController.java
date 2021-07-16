package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
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

    @GetMapping(EducGradCodeApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find All Programs", description = "Get All Programs", tags = {"Programs"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradProgram>> getAllPrograms() {
        logger.debug("getAllPrograms : ");
        return response.GET(codeService.getAllProgramList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_PROGRAM_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find a Program by Program Code", description = "Get a Program by Program Code", tags = {"Programs"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GradProgram> getSpecificProgramCode(@PathVariable String programCode) {
        logger.debug("getSpecificProgramCode : ");
        GradProgram gradResponse = codeService.getSpecificProgramCode(programCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_COUNTRY_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COUNTRY)
    @Operation(summary = "Find All Countries", description = "Get All Countries", tags = {"Country"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradCountry>> getAllCountryCodeList() {
        logger.debug("getAllCountryCodeList : ");
        return response.GET(codeService.getAllCountryCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_COUNTRY_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COUNTRY)
    @Operation(summary = "Find a Country by Code", description = "Get a Country by Country Code", tags = {"Country"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GradCountry> getSpecificCountryCode(@PathVariable String countryCode) {
        logger.debug("getSpecificCountryCode : ");
        GradCountry gradResponse = codeService.getSpecificCountryCode(countryCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_PROVINCE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROVINCE)
    @Operation(summary = "Find All Provinces", description = "Get All Provinces", tags = {"Province"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradProvince>> getAllProvinceCodeList() {
        logger.debug("getAllProvinceCodeList : ");
        return response.GET(codeService.getAllProvinceCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_PROVINCE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROVINCE)
    @Operation(summary = "Find a Province by Province Code", description = "Get a Province by Province Code", tags = {"Province"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GradProvince> getSpecificProvinceCode(@PathVariable String provinceCode) {
        logger.debug("getSpecificProvinceCode : ");
        GradProvince gradResponse = codeService.getSpecificProvinceCode(provinceCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_UNGRAD)
    @Operation(summary = "Find all Ungrad Reasons", description = "Get all Ungrad Reasons", tags = {"Ungrad Reasons"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradUngradReasons>> getAllUngradReasonCodeList() {
        logger.debug("getAllUngradReasonCodeList : ");
        return response.GET(codeService.getAllUngradReasonCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_UNGRAD_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_UNGRAD)
    @Operation(summary = "Find an Ungrad Reason by Ungrad Reasons Code",
            description = "Get an Ungrad Reason by Ungrad Reasons Code", tags = {"Ungrad Reasons"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GradUngradReasons> getSpecificUngradReasonCode(@PathVariable String reasonCode) {
        logger.debug("getSpecificUngradReasonCode : ");
        GradUngradReasons gradResponse = codeService.getSpecificUngradReasonCode(reasonCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }

    }

    @PostMapping(EducGradCodeApiConstants.GET_ALL_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_UNGRAD_REASON)
    @Operation(summary = "Create an Ungrad Reason", description = "Create an Ungrad Reason", tags = {"Ungrad Reasons"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradUngradReasons>> createGradUngradReasons(@Valid @RequestBody GradUngradReasons gradUngradReasons) {
        logger.debug("createGradUngradReason : ");
        validation.requiredField(gradUngradReasons.getCode(), REASON_CODE);
        validation.requiredField(gradUngradReasons.getDescription(), "Reason Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradUngradReasons(gradUngradReasons));
    }

    @PutMapping(EducGradCodeApiConstants.GET_ALL_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_UNGRAD_REASON)
    @Operation(summary = "Update an Ungrad Reason", description = "Update an Ungrad Reason", tags = {"Ungrad Reasons"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradUngradReasons>> updateGradUngradReasons(@Valid @RequestBody GradUngradReasons gradUngradReasons) {
        logger.info("updateGradUngradReasons : ");
        validation.requiredField(gradUngradReasons.getCode(), REASON_CODE);
        validation.requiredField(gradUngradReasons.getDescription(), "Reason Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradUngradReasons(gradUngradReasons));
    }

    @DeleteMapping(EducGradCodeApiConstants.GET_ALL_UNGRAD_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_UNGRAD_REASON)
    @Operation(summary = "Delete an Ungrad Reason", description = "Delete an Ungrad Reason", tags = {"Ungrad Reasons"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradUngradReasons(@Valid @PathVariable String reasonCode) {
        logger.debug("deleteGradUngradReason : ");
        validation.requiredField(reasonCode, REASON_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OAuth2AuthenticationDetails auth =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String accessToken = auth.getTokenValue();
        return response.DELETE(codeService.deleteGradUngradReasons(reasonCode, accessToken));
    }

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

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_MESSAGING_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_MESSAGING)
    @Operation(summary = "Find all Grad Messaging", description = "Get all Grad Messaging", tags = {"Graduation Messages"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradMessaging>> getAllGradMessagingList() {
        logger.debug("getAllGradMessagingList : ");
        return response.GET(codeService.getAllGradMessagingList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_MESSAGING_BY_PRG_CODE_AND_MESSAGE_TYPE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_MESSAGING)
    @Operation(summary = "Find a Grad Messaging", description = "Get a Grad Messaging", tags = {"Graduation Messages"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradMessaging> getSpecificGradMessagingCode(@PathVariable String pgmCode, @PathVariable String msgType) {
        logger.debug("getSpecificGradMessagingCode : ");
        GradMessaging gradResponse = codeService.getSpecificGradMessagingCode(pgmCode, msgType);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_CAREER_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_CAREER_PROGRAM)
    @Operation(summary = "Find all Career Program", description = "Get all Career Program", tags = {"Career Program"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradCareerProgram>> getAllCareerPrograms() {
        logger.debug("getAllPrograms : ");
        return response.GET(codeService.getAllCareerProgramCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_CAREER_PROGRAM_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_CAREER_PROGRAM)
    @Operation(summary = "Find a Career Program", description = "Get a Career Program", tags = {"Career Program"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradCareerProgram> getSpecificCareerProgramCode(@PathVariable String cpCode) {
        logger.debug("getSpecificCareerProgramCode : ");
        GradCareerProgram gradResponse = codeService.getSpecificCareerProgramCode(cpCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

   

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_REQUIREMENT_TYPE_CODE)
    @Operation(summary = "Find all Requirement Types", description = "Get all Requirement Types", tags = {"Requirement Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradRequirementTypes>> getAllRequirementTypeCodeList() {
        logger.debug("getAllRequirementTypeCodeList : ");
        return response.GET(codeService.getAllRequirementTypeCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_REQUIREMENT_TYPE_CODE)
    @Operation(summary = "Find a Requirement Type", description = "Get a Requirement Type", tags = {"Requirement Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradRequirementTypes> getSpecificRequirementTypeCode(@PathVariable String typeCode) {
        logger.debug("getSpecificRequirementTypeCode : ");
        GradRequirementTypes gradResponse = codeService.getSpecificRequirementTypeCode(typeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }
    }

    @PostMapping(EducGradCodeApiConstants.GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_REQUIREMENT_TYPE)
    @Operation(summary = "Create a Requirement Type", description = "Create a Requirement Type", tags = {"Requirement Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradRequirementTypes>> createGradRequirementTypes(
            @Valid @RequestBody GradRequirementTypes gradRequirementTypes) {
        logger.debug("creatGradRequirementTypess : ");
        validation.requiredField(gradRequirementTypes.getCode(), REQUIREMENT_TYPE_CODE);
        validation.requiredField(gradRequirementTypes.getDescription(), "Requirement Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradRequirementTypes(gradRequirementTypes));
    }

    @PutMapping(EducGradCodeApiConstants.GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_REQUIREMENT_TYPE)
    @Operation(summary = "Update a Requirement Type", description = "Update a Requirement Type", tags = {"Requirement Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradRequirementTypes>> updateGradRequirementTypes(
            @Valid @RequestBody GradRequirementTypes gradRequirementTypes) {
        logger.info("updateGradRequirementTypes : ");
        validation.requiredField(gradRequirementTypes.getCode(), REQUIREMENT_TYPE_CODE);
        validation.requiredField(gradRequirementTypes.getDescription(), "Requirement Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradRequirementTypes(gradRequirementTypes));
    }

    @DeleteMapping(EducGradCodeApiConstants.GET_ALL_GRAD_REQUIREMENT_TYPE_CODE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_REQUIREMENT_TYPE)
    @Operation(summary = "Delete a Requirement Type", description = "Delete a Requirement Type", tags = {"Requirement Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradRequirementTypes(@Valid @PathVariable String typeCode) {
        logger.debug("deleteGradRequirementTypes : ");
        validation.requiredField(typeCode, REQUIREMENT_TYPE_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OAuth2AuthenticationDetails auth =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String accessToken = auth.getTokenValue();
        return response.DELETE(codeService.deleteGradRequirementTypes(typeCode, accessToken));
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

    @GetMapping(EducGradCodeApiConstants.GET_ALL_STUDENT_STATUS_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_STATUS)
    @Operation(summary = "Find all Student Status", description = "Get all Student Status", tags = {"Student Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<StudentStatus>> getAllStudentStatusCodeList() {
        logger.debug("getAllUngradReasonCodeList : ");
        return response.GET(codeService.getAllStudentStatusCodeList());
    }

    @GetMapping(EducGradCodeApiConstants.GET_ALL_STUDENT_STATUS_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_STATUS)
    @Operation(summary = "Find a Student Status by Student Status Code",
            description = "Get a Student Status by Student Status Code", tags = {"Student Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<StudentStatus> getSpecificStudentStatusCode(@PathVariable String statusCode) {
        logger.debug("getSpecificUngradReasonCode : ");
        StudentStatus gradResponse = codeService.getSpecificStudentStatusCode(statusCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NO_CONTENT();
        }

    }

    @PostMapping(EducGradCodeApiConstants.GET_ALL_STUDENT_STATUS_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_STUDENT_STATUS)
    @Operation(summary = "Create a Student Status", description = "Create a Student Status", tags = {"Student Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<StudentStatus>> createStudentStatus(@Valid @RequestBody StudentStatus studentStatus) {
        logger.debug("createStudentStatus : ");
        validation.requiredField(studentStatus.getCode(), STATUS_CODE);
        validation.requiredField(studentStatus.getDescription(), "Status Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createStudentStatus(studentStatus));
    }

    @PutMapping(EducGradCodeApiConstants.GET_ALL_STUDENT_STATUS_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_STUDENT_STATUS)
    @Operation(summary = "Update an Student Status", description = "Update an Student Status", tags = {"Student Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<StudentStatus>> updateStudentStatusCode(@Valid @RequestBody StudentStatus studentStatus) {
        logger.info("updateStudentStatusCode : ");
        validation.requiredField(studentStatus.getCode(), STATUS_CODE);
        validation.requiredField(studentStatus.getDescription(), "Status Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateStudentStatus(studentStatus));
    }

    @DeleteMapping(EducGradCodeApiConstants.GET_ALL_STUDENT_STATUS_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_STUDENT_STATUS)
    @Operation(summary = "Delete an Student Status", description = "Delete an Student Status", tags = {"Student Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteStudentStatusCodes(@Valid @PathVariable String statusCode) {
        logger.debug("deleteStudentStatusCodes : ");
        validation.requiredField(statusCode, STATUS_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OAuth2AuthenticationDetails auth =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String accessToken = auth.getTokenValue();
        return response.DELETE(codeService.deleteStudentStatus(statusCode, accessToken));
    }
}
