package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SchoolCacheService {

  private static final Map<UUID, School> schools = new HashMap<>();
  private final InstituteService instituteService;

  public SchoolCacheService(InstituteService instituteService) {
    this.instituteService = instituteService;
  }

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
