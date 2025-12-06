package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class AcademicSessionDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionDesignator;
    private String sessionName;
    private String sessionSchoolYear;

    public String getSessionDesignator() {
        return sessionDesignator;
    }

    public void setSessionDesignator(final String sessionDesignator) {
        this.sessionDesignator = sessionDesignator;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(final String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionSchoolYear() {
        return sessionSchoolYear;
    }

    public void setSessionSchoolYear(final String sessionSchoolYear) {
        this.sessionSchoolYear = sessionSchoolYear;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "sessionDesignator=" + sessionDesignator + ", sessionName=" + sessionName + ", sessionSchoolYear=" + sessionSchoolYear + '}';
    }
}
