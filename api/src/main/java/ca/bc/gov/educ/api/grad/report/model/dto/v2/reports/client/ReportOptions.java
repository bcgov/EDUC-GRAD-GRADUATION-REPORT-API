package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ReportOptions implements Serializable {

	private boolean cacheReport;
	private String convertTo;
	private boolean overwrite;
	private boolean preview;
	private String reportName;
	private String reportFile;

}
