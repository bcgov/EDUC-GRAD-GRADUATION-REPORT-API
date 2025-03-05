package ca.bc.gov.educ.api.grad.report.repository.v2;

import ca.bc.gov.educ.api.grad.report.model.entity.v2.SchoolReportLightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("schoolReportsLightRepositoryV2")
public interface SchoolReportLightRepository extends JpaRepository<SchoolReportLightEntity, UUID>, JpaSpecificationExecutor<SchoolReportLightEntity> {

    @Query("select c.schoolOfRecordId from SchoolReportsLightEntityV2 c where c.reportTypeCode=:reportType")
    List<UUID> getReportSchoolOfRecordsByReportType(String reportType);

    @Query("select c.id from SchoolReportsLightEntityV2 c where c.schoolOfRecordId IN (:schoolOfRecordIds) and c.reportTypeCode=:reportType")
    List<UUID> getReportGuidsBySchoolOfRecordsAndReportType(List<UUID> schoolOfRecordIds, String reportType);

    @Query("select count(*) from SchoolReportsLightEntityV2 c where c.schoolOfRecordId IN (:schoolOfRecordIds) and c.reportTypeCode=:reportType")
    Integer countBySchoolOfRecordsAndReportType(List<UUID> schoolOfRecordIds, String reportType);
}
