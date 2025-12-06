package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AcademicSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private AcademicSessionDetail academicSessionDetail;

    @XmlElement(name = "course")
    private List<TranscriptResult> course = new ArrayList<>();

    @XmlElement(name = "achievement")
    private List<AchievementResult> achievement = new ArrayList<>();

    public AcademicSessionDetail getAcademicSessionDetail() {
        return academicSessionDetail;
    }

    public void setAcademicSessionDetail(final AcademicSessionDetail academicSessionDetail) {
        this.academicSessionDetail = academicSessionDetail;
    }

    public List<TranscriptResult> getCourse() {
        return course;
    }

    public void setCourse(final List<TranscriptResult> course) {
        this.course = course;
    }

    public List<AchievementResult> getAchievement() {
        return achievement;
    }

    public void setAchievement(final List<AchievementResult> achievement) {
        this.achievement = achievement;
    }

    /**
     * Helper method to add a transcript result to the student's list of
     * transcript results.
     *
     * @param transcriptResult The transcript result to add to the internal list
     * of transcript results.
     */
    public void addTranscriptResult(final TranscriptResult transcriptResult) {
        getCourse().add(transcriptResult);
    }

    public void addAchievementResult(final AchievementResult achievementResult) {
        getAchievement().add(achievementResult);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "academicSessionDetail=" + academicSessionDetail + ", course=" + course + '}';
    }
}
