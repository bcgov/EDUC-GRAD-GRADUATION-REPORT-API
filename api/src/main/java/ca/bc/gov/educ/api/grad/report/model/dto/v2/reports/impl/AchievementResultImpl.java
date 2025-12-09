package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class AchievementResultImpl extends AbstractDomainEntity
        implements AchievementResult {

    private static final long serialVersionUID = 4L;

    private Course course;
    private Mark mark;
    private String requirement = "";
    private String requirementName = "";
    private String equivalency = "";
    private String usedForGrad = "";

    public AchievementResultImpl() {
    }

    public AchievementResultImpl(final Course course, final Mark mark) {
        this.course = course;
        this.mark = mark;
    }

    public AchievementResultImpl(final String req, final String equiv, final String ufg, final String reqName) {
        this.equivalency = equiv;
        this.requirement = req;
        this.usedForGrad = ufg;
        this.requirementName = reqName;
    }

    @Override
    public Course getCourse() {
        return this.course;
    }

    @Override
    @JsonDeserialize(as = MarkImpl.class)
    public Mark getMark() {
        return this.mark;
    }

    @Override
    @JsonProperty("requirement")
    public String getRequirementMet() {
        return this.requirement;
    }

    @Override
    @JsonProperty("requirementName")
    public String getRequirementMetName() {
        return this.requirementName;
    }

    @Override
    @JsonProperty("equivalency")
    public String getEquivalencyChallenge() {
        return equivalency;
    }

    @Override
    @JsonProperty("usedForGrad")
    public String getUsedForGrad() {
        return usedForGrad;
    }

    public void setEquivalencyChallenge(String equivalency) {
        this.equivalency = equivalency;
    }

    public void setUsedForGrad(String usedForGrad) {
        this.usedForGrad = usedForGrad;
    }

    public void setRequirementMet(String requirement) {
        this.requirement = requirement;
    }

    public void setRequirementMetName(String requirementName) {
        this.requirementName = requirementName;
    }

    public void setCourse(Course course) {
        if (course != null) {
            this.course = course;
        }
    }

    public void setMark(Mark mark) {
        if (mark != null) {
            this.mark = mark;
        }
    }

    /**
     * Throws UnsupportedOperationException.
     *
     * @return Not used.
     */
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
