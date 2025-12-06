package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class TotalCounts implements Serializable {

    private Integer totalCertificates = 0;
    private Integer totalTranscripts = 0;

    public void countCertificate(int count) {
        totalCertificates = totalCertificates + count;
    }

    public void countTranscript(int count) {
        totalTranscripts = totalTranscripts + count;
    }
}
