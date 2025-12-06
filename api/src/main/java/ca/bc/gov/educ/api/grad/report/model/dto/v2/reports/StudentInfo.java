package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ca.bc.gov.educ.api.grad.report.constants.Constants.DATE_TRAX_YMD;
import static ca.bc.gov.educ.api.grad.report.util.VerifyUtils.asBoolean;
import static ca.bc.gov.educ.api.grad.report.util.VerifyUtils.trimSafe;

public class StudentInfo {

    private static final long serialVersionUID = 5L;

    private static final String CLASSNAME = StudentInfo.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASSNAME);

    // basic student info
    private String pen;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private LocalDate birthdate = LocalDate.now();

    // student transcript info
    private String schoolId = "";
    private Date reportDate = new Date(0L);
    private Date lastUpdateDate = new Date(0L);
    private String logo = "";
    private String gender = "";
    private String citizenship = "";
    private String status = "";
    private Boolean honourFlag = Boolean.FALSE;
    private Boolean dogwoodFlag = Boolean.FALSE;
    private String grade = "";
    private LocalDate gradDate;
    private String gradProgram = "";
    private String gradReqYear = "";
    private final List<String> academicProgram = new ArrayList<>();
    private Map<String, String> nonGradReasons = new HashMap<>();
    private String gradMessage = "";

    private String localId = "";
    private String hasOtherProgram = "";
    private List<OtherProgram> otherProgramParticipation = new ArrayList<>();

    // student address
    private String studentAddress1 = "";
    private String studentAddress2 = "";
    private String studentCity = "";
    private String studentProv = "";
    private String studentPostalCode = "";
    private String traxStudentCountry = "";
    private String isoStudentCountry;

    // school information
    private String mincode = "";
    private String schoolName = "";
    private String schoolStreet = "";
    private String schoolStreet2 = "";
    private String schoolCity = "";
    private String schoolPostalCode = "";
    private String schoolProv = "";
    private String schoolPhone = "";
    private String schoolTypeIndicator = "";
    private String schoolTypeBanner = "";

    public StudentInfo() {
    }

    public StudentInfo(
            final String studNo,
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate birthdate,
            final String localId,
            final String studGender,
            final String citizenship,
            final String mincode,
            final String studGrade,
            final LocalDate gradDate,
            final String gradProgram,
            final String gradReqYear,
            final String gradMessage,
            final Date updateDt,
            final String logoType,
            final String studentAddress1,
            final String studentAddress2,
            final String studentCity,
            final String studentProv,
            final String studentPostalCode,
            final String traxStudentCountry,
            final String studStatus,
            final Character honourFlag,
            final Character dogwoodFlag,
            final String prgmCode,
            final String prgmCode2,
            final String prgmCode3,
            final String prgmCode4,
            final String prgmCode5,
            final String schoolName,
            final String schoolStreet,
            final String schoolStreet2,
            final String schoolCity,
            final String schoolProv,
            final String schoolPostalCode,
            final String schoolPhone,
            final String schlIndType,
            final String schoolId) {

        this.pen = trimSafe(studNo);
        this.firstName = trimSafe(firstName);
        this.middleName = trimSafe(middleName);
        this.lastName = trimSafe(lastName);
        this.birthdate = birthdate;
        this.localId = trimSafe(localId);
        this.gender = studGender;
        this.citizenship = citizenship;
        this.mincode = trimSafe(mincode);
        this.grade = trimSafe(studGrade);
        this.gradDate = gradDate;
        this.gradProgram = trimSafe(gradProgram);
        this.gradReqYear = trimSafe(gradReqYear);
        this.gradMessage = trimSafe(gradMessage);
        this.reportDate = updateDt;
        this.logo = trimSafe(logoType);
        this.studentAddress1 = trimSafe(studentAddress1);
        this.studentAddress2 = trimSafe(studentAddress2);
        this.studentCity = trimSafe(studentCity);
        this.studentProv = trimSafe(studentProv);
        this.studentPostalCode = trimSafe(studentPostalCode);
        this.status = studStatus;

        this.honourFlag = asBoolean(honourFlag);
        this.dogwoodFlag = asBoolean(dogwoodFlag);

        this.schoolName = trimSafe(schoolName);
        this.schoolStreet = trimSafe(schoolStreet);
        this.schoolStreet2 = trimSafe(schoolStreet2);
        this.schoolCity = trimSafe(schoolCity);
        this.schoolProv = trimSafe(schoolProv);
        this.schoolPostalCode = trimSafe(schoolPostalCode);
        this.schoolPhone = trimSafe(schoolPhone);
        this.schoolTypeIndicator = trimSafe(schlIndType);
        this.schoolId = schoolId;
        //setSchoolTypeBanner();

        this.academicProgram.add(trimSafe(prgmCode));
        this.academicProgram.add(trimSafe(prgmCode2));
        this.academicProgram.add(trimSafe(prgmCode3));
        this.academicProgram.add(trimSafe(prgmCode4));
        this.academicProgram.add(trimSafe(prgmCode5));
        this.traxStudentCountry = trimSafe(traxStudentCountry);
    }


    public String getPen() {
        return this.pen;
    }


    public String getFirstName() {
        return this.firstName;
    }


    public String getMiddleName() {
        return this.middleName == null ? "" : this.middleName;
    }


    public String getLastName() {
        return this.lastName;
    }


    @JsonFormat(pattern= ReportApiConstants.BIRTHDATE_FORMAT)
    public LocalDate getBirthdate() {
        return this.birthdate;
    }


    public String getStudentAddress1() {
        return this.studentAddress1;
    }


    public String getStudentAddress2() {
        return this.studentAddress2;
    }


    public String getStudentCity() {
        return this.studentCity;
    }


    public String getStudentProv() {
        return this.studentProv;
    }


    public String getStudentPostalCode() {
        return this.studentPostalCode;
    }


    public String getStudentStatus() {
        return this.status;
    }


    public String getGender() {
        return this.gender;
    }


    public String getCitizenship() {
        return citizenship;
    }


    public Boolean isHonourFlag() {
        return this.honourFlag;
    }


    public Boolean isDogwoodFlag() {
        return this.dogwoodFlag;
    }


    public String getGrade() {
        return this.grade;
    }


    public String getGradProgram() {
        return this.gradProgram;
    }

    public void setGradProgram(String gradProgram) {
        this.gradProgram = gradProgram;
    }


    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) { this.localId = localId; }


    public List<OtherProgram> getOtherProgramParticipation() {
        return otherProgramParticipation;
    }


    public String getHasOtherProgram() {
        return this.hasOtherProgram;
    }


    public String getMincode() {
        return this.mincode;
    }


    public String getSchoolProv() {
        return this.schoolProv;
    }


    public String getSchoolPhone() {
        return this.schoolPhone;
    }


    public Map<String, String> getNonGradReasons() {
        return this.nonGradReasons;
    }

    /**
     * set the reason why the student failed to graduate.
     *
     * @param reasons
     */
    public void setNonGradReasons(final Map<String, String> reasons) {
        this.nonGradReasons = reasons;
    }


    public LocalDate getGradDate() {
        return this.gradDate;
    }


    public String getSchoolName() {
        return this.schoolName;
    }


    public String getSchoolStreet() {
        return this.schoolStreet;
    }


    public String getSchoolCity() {
        return this.schoolCity;
    }


    public String getSchoolPostalCode() {
        return this.schoolPostalCode;
    }


    public Date getReportDate() {
        return this.reportDate;
    }


    @JsonFormat(pattern= ReportApiConstants.DATETIME_FORMAT)
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdated) {
        this.lastUpdateDate = lastUpdated;
    }


    public String getLogo() {
        return this.logo;
    }


    public String getSchoolId() {
        return this.schoolId;
    }


    public List<String> getAcademicProgram() {
        return this.academicProgram;
    }


    public String getSchoolTypeIndicator() {
        return this.schoolTypeIndicator;
    }


    public String getSchoolTypeBanner() {
        return this.schoolTypeBanner;
    }

    public void setHasOtherProgram(String hasOtherProgram) {
        this.hasOtherProgram = hasOtherProgram;
    }

    public void setOtherProgramParticipation(List<OtherProgram> otherProgramParticipation) {
        this.otherProgramParticipation = otherProgramParticipation;
    }

    /**
     * set the school type banner.
     *
     */
    private void setSchoolTypeBanner() {
        switch (getSchoolTypeIndicator()) {
            case "1":
                this.schoolTypeBanner = "B.C. INDEPENDENT SCHOOLS - GROUP 1";
                break;
            case "2":
                this.schoolTypeBanner = "B.C. INDEPENDENT SCHOOLS - GROUP 2";
                break;
            case "4":
                this.schoolTypeBanner = "B.C. INDEPENDENT SCHOOLS - GROUP 4";
                break;
            default:
                this.schoolTypeBanner = "";
        }
    }


    public String getSchoolStreet2() {
        return schoolStreet2;
    }


    public String getGradMessage() {
        return gradMessage;
    }


    public String getCountryCode() {
        return isoStudentCountry;
    }


    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.pen);
        hash = 13 * hash + Objects.hashCode(this.firstName);
        hash = 13 * hash + Objects.hashCode(this.lastName);
        hash = 13 * hash + Objects.hashCode(this.birthdate);
        hash = 13 * hash + Objects.hashCode(this.schoolId);
        return hash;
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentInfo other = (StudentInfo) obj;
        if (!(this.pen.equals(other.pen))) {
            return false;
        }
        if (!(this.firstName.equals(other.firstName))) {
            return false;
        }
        if (!(this.lastName.equals(other.lastName))) {
            return false;
        }
        return this.schoolId.equals(other.schoolId);
    }

    /**
     * Creates a date instance from a date in DATE_TRAX_YMD format.
     *
     * @param traxDate The TRAX date to convert to a string before parsing.
     * @return The given TRAX date as a Date instance.
     */
    private Date createDate(final Long traxDate) {
        return traxDate == null ? new Date() : createDate(traxDate.toString());
    }

    /**
     * Creates a date instance from a date in DATE_TRAX_YMD format.
     *
     * @param traxDate The date in DATE_TRAX_YMD format.
     * @return A new date instance.
     */
    private Date createDate(final String traxDate) {
        return createDate(traxDate, DATE_TRAX_YMD);
    }

    /**
     * Create a Date data type from the given String value using the specified
     * pattern.
     *
     * @param d The unparsed date to convert into a Date instance.
     * @param pattern The date/time pattern to use for parsing the date.
     * @return The given formatted date value as a date instance.
     */
    private Date createDate(final String d, final String pattern) {
        Date date = new Date();

        try {
            if (d != null && !d.isEmpty() && !"0".equals(d)) {
                final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                date = sdf.parse(d);
            }
        } catch (final ParseException e) {
            date = new Date();
            LOG.log(Level.WARNING,
                    "Failed to parse date {0} using {1}",
                    new Object[]{d, pattern});
        }

        return date;
    }

    public JRBeanCollectionDataSource getOtherProgramParticipationdataSource() {
        return new JRBeanCollectionDataSource(otherProgramParticipation, false);
    }


    public String getGradReqYear() {
        return this.gradReqYear;
    }
}
