package service.exception;

/**
 * Application exception for service layer.
 *
 * @Author Pavel Matyáš <matyapav@fel.cvut.cz>.
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
