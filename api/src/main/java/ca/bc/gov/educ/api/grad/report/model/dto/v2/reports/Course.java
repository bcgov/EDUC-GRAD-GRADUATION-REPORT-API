package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;
import java.util.Objects;

import static java.lang.String.format;

public class Course extends AbstractDomainEntity {

    private static final long serialVersionUID = 4L;

    private String name = "";
    private String code = "";
    private String level = "";
    private String credits = "";
    private String sessionDate = "";
    private String type = "";
    private String relatedCourse = "";
    private String relatedLevel = "";

    public Course() {
    }

    public Course(
            final String name,
            final String code,
            final String level,
            final String credits,
            final String session,
            final String type,
            final String relatedCourse,
            final String relatedLevel) {
        this.name = name;
        this.code = code;
        this.level = level;
        this.credits = credits;
        this.sessionDate = session;
        this.type = type;
        this.relatedCourse = relatedCourse;
        this.relatedLevel = relatedLevel;
    }


    public String getName() {
        return this.name;
    }


    public String getCode() {
        return this.code;
    }


    public String getLevel() {
        return this.level;
    }


    public String getCredits() {
        return this.credits;
    }


    public String getSessionDate() {
        return this.sessionDate;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getRelatedCourse() {
        return this.relatedCourse;
    }

    public void setRelatedCourse(String relatedCourse) {
        this.relatedCourse = relatedCourse;
    }


    public String getRelatedLevel() {
        return this.relatedLevel;
    }

    public void setRelatedLevel(String relatedLevel) {
        this.relatedLevel = relatedLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    /**
     * Returns a unique identifier for this instance.
     *
     * @return A unique number.
     */

    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.code);
        hash = 67 * hash + Objects.hashCode(this.level);
        hash = 67 * hash + Objects.hashCode(this.sessionDate);
        return hash;
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.level, other.level)) {
            return false;
        }
        return Objects.equals(this.sessionDate, other.sessionDate);
    }


    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public String toString() {
        return format("%s("
                + "courseName = <%s>, "
                + "courseCode = <%s>, "
                + "courseLevel = <%s>, "
                + "courseType = <%s>"
                + ")",
                getClass().getSimpleName(),
                getName(),
                getCode(),
                getLevel(),
                getType());
    }
}
