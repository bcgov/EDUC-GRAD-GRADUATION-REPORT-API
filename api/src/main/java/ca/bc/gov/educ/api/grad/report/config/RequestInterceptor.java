package ca.bc.gov.educ.api.grad.report.config;

import ca.bc.gov.educ.api.grad.report.util.*;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements AsyncHandlerInterceptor {

	@Autowired
	GradValidation validation;

	@Autowired
	EducGradReportApiConstants constants;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		validation.clear();

		// correlationID
		val correlationID = request.getHeader(EducGradReportApiConstants.CORRELATION_ID);
		if (correlationID != null) {
			ThreadLocalStateUtil.setCorrelationID(correlationID);
		}

		// username
		JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authenticationToken.getCredentials();
		String username = JwtUtil.getName(jwt);
		if (username != null) {
			ThreadLocalStateUtil.setCurrentUser(username);
		}

		return true;
	}

	/**
	 * After completion.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @param ex       the ex
	 */
	@Override
	public void afterCompletion(@NonNull final HttpServletRequest request, final HttpServletResponse response, @NonNull final Object handler, final Exception ex) {
		LogHelper.logServerHttpReqResponseDetails(request, response, constants.isSplunkLogHelperEnabled());
		val correlationID = request.getHeader(EducGradReportApiConstants.CORRELATION_ID);
		if (correlationID != null) {
			response.setHeader(EducGradReportApiConstants.CORRELATION_ID, request.getHeader(EducGradReportApiConstants.CORRELATION_ID));
		}
		// clear
		ThreadLocalStateUtil.clear();
	}
}
