package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import ca.bc.gov.educ.api.grad.report.constants.AchievementType;

public interface AchievementOrderType extends OrderType {
    AchievementType getAchievementType();
}
