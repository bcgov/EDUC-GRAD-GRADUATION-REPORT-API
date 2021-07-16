package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.exception.GradBusinessRuleException;
import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes"})
public class CodeServiceTest {

	@Autowired
	private CodeService codeService;
	
	@MockBean
	private GradProgramRepository gradProgramRepository;	

	@MockBean
	private GradCountryRepository gradCountryRepository;

	@MockBean
	private GradProvinceRepository gradProvinceRepository;
	
	@MockBean
	private GradUngradReasonsRepository gradUngradReasonsRepository;

	@MockBean
	private GradCertificateTypesRepository gradCertificateTypesRepository;

	@MockBean
	private GradReportTypesRepository gradReportTypesRepository;

	@MockBean
	private GradMessagingRepository gradMessagingRepository;

	@MockBean
	private GradCareerProgramRepository gradCareerProgramRepository;

	@MockBean
	private GradRequirementTypesRepository gradRequirementTypesRepository;

	@MockBean
	private StudentStatusRepository studentStatusRepository;

	@Autowired
	GradValidation validation;
	
	@Test
	public void testGetAllProgramList() {
		List<GradProgramEntity> gradProgramList = new ArrayList<>();
		GradProgramEntity obj = new GradProgramEntity();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		gradProgramList.add(obj);
		obj = new GradProgramEntity();
		obj.setProgramCode("AC");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		gradProgramList.add(obj);
		Mockito.when(gradProgramRepository.findAll()).thenReturn(gradProgramList);
		codeService.getAllProgramList();
	}
	
	@Test
	public void testGetSpecificProgramCode() {
		String programCode = "AB";
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		obj.setProgramStartDate(new Date(System.currentTimeMillis()));
		obj.toString();
		GradProgramEntity objEntity = new GradProgramEntity();
		objEntity.setProgramCode("AB");
		objEntity.setProgramName("Autobody");
		objEntity.setProgramStartDate(new Date(System.currentTimeMillis()));
		Optional<GradProgramEntity> ent = Optional.of(objEntity);
		Mockito.when(gradProgramRepository.findById(programCode)).thenReturn(ent);
		codeService.getSpecificProgramCode(programCode);
	}
	
	@Test
	public void testGetSpecificProgramCodeReturnsNull() {
		String programCode = "AB";
		Mockito.when(gradProgramRepository.findById(programCode)).thenReturn(Optional.empty());
		codeService.getSpecificProgramCode(programCode);
	}
	
	@Test
	public void testGetAllProvinceList() {
		List<GradProvinceEntity> gradProvinceList = new ArrayList<>();
		GradProvinceEntity obj = new GradProvinceEntity();
		obj.setProvCode("BC");
		obj.setProvName("British Columbia");
		gradProvinceList.add(obj);
		obj = new GradProvinceEntity();
		obj.setProvCode("AB");
		obj.setProvName("Alberta");
		gradProvinceList.add(obj);
		Mockito.when(gradProvinceRepository.findAll()).thenReturn(gradProvinceList);
		codeService.getAllProvinceCodeList();
		
	}
	
	@Test
	public void testGetSpecificProvinceCode() {
		String provCode = "BC";
		GradProvince obj = new GradProvince();
		obj.setProvCode("BC");
		obj.setProvName("British Columbia");
		obj.toString();
		GradProvinceEntity objEntity = new GradProvinceEntity();
		objEntity.setProvCode("BC");
		objEntity.setProvName("British Columbia");
		Optional<GradProvinceEntity> ent = Optional.of(objEntity);
		Mockito.when(gradProvinceRepository.findById(provCode)).thenReturn(ent);
		codeService.getSpecificProvinceCode(provCode);
	}
	
	@Test
	public void testGetSpecificProvinceCodeReturnsNull() {
		String provCode = "BC";
		Mockito.when(gradProvinceRepository.findById(provCode)).thenReturn(Optional.empty());
		codeService.getSpecificProvinceCode(provCode);
	}
	
	@Test
	public void testGetAllCountryList() {
		List<GradCountryEntity> gradCountryList = new ArrayList<>();
		GradCountryEntity obj = new GradCountryEntity();
		obj.setCountryCode("CA");
		obj.setCountryName("Canada");
		gradCountryList.add(obj);
		obj = new GradCountryEntity();
		obj.setCountryCode("USA");
		obj.setCountryName("America");
		gradCountryList.add(obj);
		Mockito.when(gradCountryRepository.findAll()).thenReturn(gradCountryList);
		codeService.getAllCountryCodeList();
	}
	
	@Test
	public void testGetSpecificCountryCode() {
		String countryCode = "AB";
		GradCountry obj = new GradCountry();
		obj.setCountryCode("CA");
		obj.setCountryName("Canada");
		obj.toString();
		GradCountryEntity objEntity = new GradCountryEntity();
		objEntity.setCountryCode("CA");
		objEntity.setCountryName("Canada");
		Optional<GradCountryEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCountryRepository.findById(countryCode)).thenReturn(ent);
		codeService.getSpecificCountryCode(countryCode);
	}
	
	@Test
	public void testGetSpecificCountryCodeReturnsNull() {
		String countryCode = "CA";
		Mockito.when(gradCountryRepository.findById(countryCode)).thenReturn(Optional.empty());
		codeService.getSpecificCountryCode(countryCode);
	}
	
	@Test
	public void testGetAllUngradReasonCodeList() {
		List<GradUngradReasonsEntity> gradUngradReasonList = new ArrayList<>();
		GradUngradReasonsEntity obj = new GradUngradReasonsEntity();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradUngradReasonList.add(obj);
		obj = new GradUngradReasonsEntity();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradUngradReasonList.add(obj);
		Mockito.when(gradUngradReasonsRepository.findAll()).thenReturn(gradUngradReasonList);
		codeService.getAllUngradReasonCodeList();
	}
	
	@Test
	public void testGetSpecificUngradReasonCode() {
		String reasonCode = "DC";
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradUngradReasonsEntity> ent = Optional.of(objEntity);
		Mockito.when(gradUngradReasonsRepository.findById(reasonCode)).thenReturn(ent);
		codeService.getSpecificUngradReasonCode(reasonCode);
	}
	
	@Test
	public void testGetSpecificUngradReasonCodeReturnsNull() {
		String reasonCode = "DC";
		Mockito.when(gradUngradReasonsRepository.findById(reasonCode)).thenReturn(Optional.empty());
		codeService.getSpecificUngradReasonCode(reasonCode);
	}
	
	@Test
	public void testGetAllCertificateTypesCodeList() {
		List<GradCertificateTypesEntity> gradCertificateTypeList = new ArrayList<>();
		GradCertificateTypesEntity obj = new GradCertificateTypesEntity();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradCertificateTypeList.add(obj);
		obj = new GradCertificateTypesEntity();
		obj.setCode("F");
		obj.setDescription("French Dogwood");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradCertificateTypeList.add(obj);
		Mockito.when(gradCertificateTypesRepository.findAll()).thenReturn(gradCertificateTypeList);
		codeService.getAllCertificateTypeCodeList();
	}
	
	@Test
	public void testGetSpecificCertificateTypeCode() {
		String certCode = "E";
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("E");
		objEntity.setDescription("English Dogwood");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(certCode)).thenReturn(ent);
		codeService.getSpecificCertificateTypeCode(certCode);
	}
	
	@Test
	public void testGetSpecificCertificateTypeCodeReturnsNull() {
		String certCode = "E";
		Mockito.when(gradCertificateTypesRepository.findById(certCode)).thenReturn(Optional.empty());
		codeService.getSpecificCertificateTypeCode(certCode);
	}
	
	@Test
	public void testGetAllReportTypesCodeList() {
		List<GradReportTypesEntity> gradReportTypeList = new ArrayList<>();
		GradReportTypesEntity obj = new GradReportTypesEntity();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradReportTypeList.add(obj);
		obj = new GradReportTypesEntity();
		obj.setCode("ACHV");
		obj.setDescription("Achievement");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradReportTypeList.add(obj);
		Mockito.when(gradReportTypesRepository.findAll()).thenReturn(gradReportTypeList);
		codeService.getAllReportTypeCodeList();
	}
	
	@Test
	public void testGetSpecificReportTypeCode() {
		String reportCode = "TRAN";
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("TRAN");
		objEntity.setDescription("Transcript");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(reportCode)).thenReturn(ent);
		codeService.getSpecificReportTypeCode(reportCode);
	}
	
	@Test
	public void testGetSpecificReportTypeCodeReturnsNull() {
		String reportCode = "TRAN";
		Mockito.when(gradReportTypesRepository.findById(reportCode)).thenReturn(Optional.empty());
		codeService.getSpecificReportTypeCode(reportCode);
	}
	
	@Test
	public void testGetAllMessagingCodeList() {
		List<GradMessagingEntity> gradMessageList = new ArrayList<>();
		GradMessagingEntity obj = new GradMessagingEntity();
		obj.setProgramCode("2018-EN");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		
		gradMessageList.add(obj);
		obj = new GradMessagingEntity();
		obj.setProgramCode("2018-PF");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradMessageList.add(obj);
		Mockito.when(gradMessagingRepository.findAll()).thenReturn(gradMessageList);
		codeService.getAllGradMessagingList();
	}
	
	@Test
	public void testGetSpecificGradMessagingCode() {
		String programCode = "2018-EN";
		String msgType = "GRADUATED";
		GradMessaging obj = new GradMessaging();
		obj.setProgramCode("2018-EN");
		obj.setMessageType("GRADUATED");
		obj.setAdIBPrograms("A");
		obj.setCareerPrograms("CP");
		obj.setGradDate("GD");
		obj.setHonours("Y");
		obj.setMainMessage("abcd");
		obj.setProgramCadre("PR");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradMessagingEntity objEntity = new GradMessagingEntity();
		objEntity.setProgramCode("2018-EN");
		objEntity.setMessageType("GRADUATED");
		objEntity.setAdIBPrograms("A");
		objEntity.setCareerPrograms("CP");
		objEntity.setGradDate("GD");
		objEntity.setHonours("Y");
		objEntity.setMainMessage("abcd");
		objEntity.setProgramCadre("PR");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradMessagingEntity> ent = Optional.of(objEntity);
		Mockito.when(gradMessagingRepository.findByProgramCodeAndMessageType(programCode,msgType)).thenReturn(ent);
		codeService.getSpecificGradMessagingCode(programCode,msgType);
		
	}
	
	@Test
	public void testGetSpecificMessageCodeReturnsNull() {
		String programCode = "2018-FN";
		String msgType = "GRADUATED";
		Mockito.when(gradMessagingRepository.findByProgramCodeAndMessageType(programCode,msgType)).thenReturn(Optional.empty());
		codeService.getSpecificGradMessagingCode(programCode,msgType);
		
	}
	
	@Test
	public void testGetAllCareerProgramsCodeList() {
		List<GradCareerProgramEntity> gradCareerProgramList = new ArrayList<>();
		GradCareerProgramEntity obj = new GradCareerProgramEntity();
		obj.setCode("AY");
		obj.setDescription("Archaeology");
		gradCareerProgramList.add(obj);
		obj = new GradCareerProgramEntity();
		obj.setCode("BE");
		obj.setDescription("Business Education");
		gradCareerProgramList.add(obj);
		Mockito.when(gradCareerProgramRepository.findAll()).thenReturn(gradCareerProgramList);
		codeService.getAllCareerProgramCodeList();
	}
	
	@Test
	public void testGetSpecificCareerProgramCode() {
		String cpcCode = "AY";
		GradCareerProgram obj = new GradCareerProgram();
		obj.setCode("AY");
		obj.setDescription("Archaeology");
		obj.toString();
		GradCareerProgramEntity objEntity = new GradCareerProgramEntity();
		objEntity.setCode("AY");
		objEntity.setDescription("Archaeology");
		Optional<GradCareerProgramEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCareerProgramRepository.findById(cpcCode)).thenReturn(ent);
		codeService.getSpecificCareerProgramCode(cpcCode);
	}
	
	@Test
	public void testGetSpecificCareerProgramCodeReturnsNull() {
		String cpcCode = "AZ";
		Mockito.when(gradCareerProgramRepository.findById(cpcCode)).thenReturn(Optional.empty());
		codeService.getSpecificCareerProgramCode(cpcCode);
	}
	
	@Test
	public void testGetAllRequirementTypesCodeList() {
		List<GradRequirementTypesEntity> gradRequirementTypesList = new ArrayList<>();
		GradRequirementTypesEntity obj = new GradRequirementTypesEntity();
		obj.setCode("M");
		obj.setDescription("MATCH");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradRequirementTypesList.add(obj);
		obj = new GradRequirementTypesEntity();
		obj.setCode("MC");
		obj.setDescription("MINCREDITS");
		gradRequirementTypesList.add(obj);
		Mockito.when(gradRequirementTypesRepository.findAll()).thenReturn(gradRequirementTypesList);
		codeService.getAllRequirementTypeCodeList();
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode() {
		String reqType = "M";
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("M");
		obj.setDescription("MATCH");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("M");
		objEntity.setDescription("MATCH");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradRequirementTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradRequirementTypesRepository.findById(reqType)).thenReturn(ent);
		codeService.getSpecificRequirementTypeCode(reqType);
	}
	
	@Test
	public void testGetSpecificRequirementTypesCodeReturnsNull() {
		String reqType = "E";
		Mockito.when(gradRequirementTypesRepository.findById(reqType)).thenReturn(Optional.empty());
		codeService.getSpecificRequirementTypeCode(reqType);
	}
	
	@Test
	public void testCreateGradUngradReasons() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradUngradReasonsRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradUngradReasonsRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradUngradReasons(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradUngradReasons_codeAlreadyExists() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradUngradReasonsEntity> ent = Optional.of(objEntity);
		Mockito.when(gradUngradReasonsRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradUngradReasons(obj);
		
	}
	
	@Test
	public void testUpdateGradUngradReasons() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradUngradReasonsEntity> ent = Optional.of(objEntity);
		Mockito.when(gradUngradReasonsRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradUngradReasonsRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradUngradReasons(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradUngradReasons_codeAlreadyExists() {
		GradUngradReasons obj = new GradUngradReasons();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradUngradReasonsRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradUngradReasons(obj);
		
	}
	
	
	@Test
	public void testCreateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradCertificateTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradCertificateTypes_codeAlreadyExists() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradCertificateTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradCertificateTypes(obj);		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradCertificateTypes_codeAlreadyExists() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradCertificateTypes(obj);
		
	}
	
	
	
	
	@Test
	public void testCreateGradRequirementTypes() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradRequirementTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradRequirementTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradRequirementTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradRequirementTypes_codeAlreadyExists() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradRequirementTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradRequirementTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradRequirementTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradRequirementTypes() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradRequirementTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradRequirementTypesRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradRequirementTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradRequirementTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradRequirementTypes_codeAlreadyExists() {
		GradRequirementTypes obj = new GradRequirementTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradRequirementTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradRequirementTypes(obj);
		
	}
	
	@Test
	public void testCreateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradReportTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradReportTypes_codeAlreadyExists() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradReportTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradReportTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradReportTypes_codeAlreadyExists() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradReportTypes(obj);
		
	}
	
	@Test
	public void testGetAllStudentStatusCodeList() {
		List<StudentStatusEntity> gradStudentStatusList = new ArrayList<>();
		StudentStatusEntity obj = new StudentStatusEntity();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradStudentStatusList.add(obj);
		obj = new StudentStatusEntity();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradStudentStatusList.add(obj);
		Mockito.when(studentStatusRepository.findAll()).thenReturn(gradStudentStatusList);
		codeService.getAllStudentStatusCodeList();
	}
	
	@Test
	public void testGetSpecificStudentStatusCode() {
		String reasonCode = "DC";
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<StudentStatusEntity> ent = Optional.of(objEntity);
		Mockito.when(studentStatusRepository.findById(reasonCode)).thenReturn(ent);
		codeService.getSpecificStudentStatusCode(reasonCode);
	}
	
	@Test
	public void testGetSpecificStudentStatusCodeReturnsNull() {
		String reasonCode = "DC";
		Mockito.when(studentStatusRepository.findById(reasonCode)).thenReturn(Optional.empty());
		codeService.getSpecificStudentStatusCode(reasonCode);
	}
	
	@Test
	public void testCreateStudentStatus() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(studentStatusRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(studentStatusRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createStudentStatus(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateStudentStatus_codeAlreadyExists() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<StudentStatusEntity> ent = Optional.of(objEntity);
		Mockito.when(studentStatusRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createStudentStatus(obj);
		
	}
	
	@Test
	public void testUpdateStudentStatus() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<StudentStatusEntity> ent = Optional.of(objEntity);
		Mockito.when(studentStatusRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(studentStatusRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateStudentStatus(obj);
		
	}
	
	@Test
	public void testUpdateStudentStatus_noCreatedUpdatedByData() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<StudentStatusEntity> ent = Optional.of(objEntity);
		Mockito.when(studentStatusRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(studentStatusRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateStudentStatus(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateStudentStatus_codeAlreadyExists() {
		StudentStatus obj = new StudentStatus();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(studentStatusRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateStudentStatus(obj);
		
	}
	
}
