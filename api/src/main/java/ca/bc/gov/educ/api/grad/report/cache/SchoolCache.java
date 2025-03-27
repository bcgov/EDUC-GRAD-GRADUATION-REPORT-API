package ca.bc.gov.educ.api.grad.report.cache;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.service.v2.InstituteService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SchoolCache {

  private static final Map<UUID, School> schools = new HashMap<>();
  private final InstituteService instituteService;

  public SchoolCache(InstituteService instituteService) {
    this.instituteService = instituteService;
  }

  @PostConstruct
  public void initialize() {
    refreshCache();
  }

  @Scheduled(cron = "0 0 0 * * ?") // Refresh cache every day at midnight
  public void refreshCache() {
    Map<UUID, School> allSchools = instituteService.getAllSchools()
        .stream()
        .collect(Collectors.toMap(school -> UUID.fromString(school.getSchoolId()), Function.identity()));
    synchronized (schools) {
      schools.clear();
      schools.putAll(allSchools);
    }
  }

  public School getSchool(UUID schoolId) {
    synchronized (schools) {
      return schools.computeIfAbsent(schoolId, instituteService::getSchool);
    }
  }
}
