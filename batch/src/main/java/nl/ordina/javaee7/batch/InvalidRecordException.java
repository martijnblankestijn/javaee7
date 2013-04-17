package nl.ordina.javaee7.batch;

/**
 *
 */
public class InvalidRecordException extends Exception {
  public InvalidRecordException(final String message) {
    super(message);
  }

  public InvalidRecordException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
