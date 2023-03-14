package ca.bc.gov.educ.api.grad.report.model.dto;

import org.json.JSONObject;

import java.util.Base64;

public class TokenResponseCached {

    private long tokenExpiry = 0;
    private TokenResponse tokenResponse;

    // tokenExpiry-[seconds] provides a slight offset, if token WILL expire in
    // [seconds], obtain a new one
    private final int offset;

    public TokenResponseCached(int offset) {
        this.offset = offset;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.setTokenExpiry(tokenResponse);
        this.tokenResponse = tokenResponse;
    }

    public boolean isExpired() {
        // tokenExpiry-[seconds] provides a slight offset, if token WILL expire in
        // 10 seconds, obtain a new one
        return (tokenResponse == null) || (tokenExpiry-offset) < (System.currentTimeMillis() / 1000);
    }

    private void setTokenExpiry(TokenResponse tokenResponse){
        String[] parts = tokenResponse.getAccess_token().split("\\.");
        JSONObject payload = new JSONObject(new String(Base64.getUrlDecoder().decode(parts[1])));
        this.tokenExpiry = payload.getLong("exp");
    }
}
