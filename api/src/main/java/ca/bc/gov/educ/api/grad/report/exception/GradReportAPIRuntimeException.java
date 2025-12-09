package ca.bc.gov.educ.api.grad.report.exception;

public class GradReportAPIRuntimeException extends RuntimeException {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 5241655513745148898L;

  public GradReportAPIRuntimeException(String message) {
		super(message);
  }
  
  public GradReportAPIRuntimeException(String msg, Throwable t) {
        super(msg, t);
}

  public GradReportAPIRuntimeException(Throwable exception) {
    super(exception);
  }

}
