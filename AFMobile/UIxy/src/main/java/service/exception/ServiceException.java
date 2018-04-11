package service.exception;

/**
 * Application exception for service layer.
 *
 * @Author Pavel Matyáš <matyapav@fel.cvut.cz>.
 */
public class ServiceException extends Exception {

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
