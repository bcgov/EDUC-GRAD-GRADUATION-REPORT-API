package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.fetch;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ReportOptions implements Serializable {

	private static final long serialVersionUID = 2L;

	private boolean cacheReport;
	private String convertTo;
	private boolean overwrite;
	private boolean preview;
	private String reportName;
	private String reportFile;

}
