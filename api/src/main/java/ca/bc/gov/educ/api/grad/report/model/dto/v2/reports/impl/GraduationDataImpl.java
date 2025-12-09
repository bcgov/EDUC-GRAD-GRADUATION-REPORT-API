package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AbstractDomainEntity;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GraduationData;
import ca.bc.gov.educ.api.grad.report.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class GraduationDataImpl extends AbstractDomainEntity
        implements GraduationData, Serializable {

    /**
     * null means that the student hasn't graduated.
     */
    private LocalDate graduationDate;
    private Boolean honorsFlag = FALSE;
    private Boolean dogwoodFlag = FALSE;
    private List<String> programCodes = new ArrayList<>();
    private List<String> programNames = new ArrayList<>();
    private String totalCreditsUsedForGrad = "";

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasGraduated() {
        return this.graduationDate != null;
    }

    @Override
    @JsonFormat(pattern= ReportApiConstants.DEFAULT_DATE_FORMAT)
    public LocalDate getGraduationDate() {
        return this.graduationDate == null ? LocalDate.now() : this.graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getTruncatedGraduationDate() {
        LocalDate result = getGraduationDate();
        if(result != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
            return sdf.format(DateUtils.toDate(result));
        }
        return null;
    }

    public String getFullGraduationDate() {
        LocalDate result = getGraduationDate();
        if(result != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(DateUtils.toDate(result));
        }
        return null;
    }

    public void setDogwoodFlag(Boolean dogwoodFlag) {
        this.dogwoodFlag = dogwoodFlag;
    }

    @Override
    public Boolean getDogwoodFlag() {
        return this.dogwoodFlag;
    }

    @Override
    public List<String> getProgramCodes() {
        return programCodes;
    }

    public void setProgramCodes(List<String> programCodes) {
        this.programCodes = programCodes;
    }

    @Override
    public String getTotalCreditsUsedForGrad() {
        return totalCreditsUsedForGrad;
    }

    public void setTotalCreditsUsedForGrad(String totalCreditsUsedForGrad) {
        this.totalCreditsUsedForGrad = totalCreditsUsedForGrad;
    }

    @Override
    public Boolean getHonorsFlag() {
        return this.honorsFlag;
    }

    public void setHonorsFlag(Boolean honorsFlag) {
        this.honorsFlag = honorsFlag;
    }

    @Override
    public List<String> getProgramNames() {
        return programNames;
    }

    public void setProgramNames(List<String> programNames) {
        this.programNames = programNames;
    }
}
