package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.Address;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class StudentSearchRequest implements Serializable {
    private List<UUID> schoolIds = new ArrayList<>();
    private List<UUID> districtIds = new ArrayList<>();
    private List<String> schoolCategoryCodes = new ArrayList<>();
    private List<String> pens = new ArrayList<>();
    private List<String> programs = new ArrayList<>();
    private List<UUID> studentIDs = new ArrayList<>();
    private List<String> statuses = new ArrayList<>();
    private List<String> reportTypes = new ArrayList<>();

    private String user;
    private Address address;

    @JsonFormat(pattern= EducGradReportApiConstants.DEFAULT_DATE_FORMAT)
    LocalDate gradDateFrom;
    @JsonFormat(pattern= EducGradReportApiConstants.DEFAULT_DATE_FORMAT)
    LocalDate gradDateTo;

    Boolean validateInput;
    String activityCode;
    String localDownload;

    @JsonIgnore
    public boolean isEmpty() {
        return  (schoolIds == null || schoolIds.isEmpty()) &&
                (districtIds == null || districtIds.isEmpty()) &&
                (schoolCategoryCodes == null || schoolCategoryCodes.isEmpty()) &&
                (pens == null || pens.isEmpty()) &&
                (studentIDs == null || studentIDs.isEmpty());
    }

}
