package service.exception;

/**
 * Exception for errors in component requests.
 *
 * @Author Pavel Matyáš <matyapav@fel.cvut.cz>.
 */
public class ComponentRequestException extends Exception {

    public ComponentRequestException(String message) {
        super(message);
    }

    public ComponentRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
