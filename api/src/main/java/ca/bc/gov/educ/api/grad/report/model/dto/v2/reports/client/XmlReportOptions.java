package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import lombok.Data;

import java.io.Serializable;

@Data

public class XmlReportOptions implements Serializable {

	private boolean cacheReport;
	private String convertTo;
	private boolean overwrite;
	private String reportName;
	private String reportFile;

	public XmlReportOptions() {
	}

}
