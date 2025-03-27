package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class SchoolCacheServiceIntegrationTest {
  @MockBean
  private InstituteService instituteService;

  @Autowired
  private SchoolCacheService schoolCacheService;

  @Test
  void testRefreshCache() {
    School mockSchool = new School();
    mockSchool.setSchoolId(UUID.randomUUID().toString());
    when(instituteService.getAllSchools()).thenReturn(List.of(mockSchool));

    schoolCacheService.refreshCache();

    School cachedSchool = schoolCacheService.getSchool(UUID.fromString(mockSchool.getSchoolId()));
    assertEquals(mockSchool, cachedSchool);
  }

  @Test
  void testGetSchool() {
    UUID schoolId = UUID.randomUUID();
    School mockSchool = new School();
    mockSchool.setSchoolId(schoolId.toString());
    when(instituteService.getSchool(any(UUID.class))).thenReturn(mockSchool);

    School result = schoolCacheService.getSchool(schoolId);

    assertEquals(mockSchool, result);
  }
}
