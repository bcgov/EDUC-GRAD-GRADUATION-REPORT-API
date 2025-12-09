package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.constants.AchievementType;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AchievementOrderType;

public class AchievementOrderTypeImpl extends OrderTypeImpl
        implements AchievementOrderType {

    private static final long serialVersionUID = 3L;

    private String name;
    private AchievementType achievementType;

    public AchievementOrderTypeImpl() {
    }

    /**
     * Constructs with paper type based on the certificate that was ordered.
     *
     * @param achievementType Type of certificate ordered.
     */
    public AchievementOrderTypeImpl(final AchievementType achievementType) {
        this.achievementType = achievementType;
        setPaperType(achievementType.getPaperType());
    }

    /**
     * Returns the human-readable name for certificates.
     *
     * @return "Certificates"
     */
    @Override
    public String getName() {
        return "Achievements";
    }

    public void setName(String name) {
        this.name = name;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
        this.setPaperType(this.achievementType.getPaperType());
    }

    @Override
    public String toString() {
        return "AchievementOrderTypeImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
