package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

@Data
public class TokenResponse {

	private String access_token;
	private String refresh_token;
}
