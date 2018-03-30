package service.exception;

/**
 * Exception for errors in component requests.
 *
 * @Author Pavel Matyáš <matyapav@fel.cvut.cz>.
 */
public class ComponentRequestException extends Exception {

    /**
     * Instantiates a new Component request exception.
     *
     * @param message the message
     */
    public ComponentRequestException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Component request exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComponentRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
