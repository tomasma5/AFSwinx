package service.rest;

import model.Screen;
import org.bson.types.ObjectId;
import service.exception.ServiceException;

import java.util.List;

/**
 * Service for getting screens.
 */
public interface ScreenRestService {

    /**
     * Gets screen by id.
     *
     * @param screenId the screen id
     * @return the screen by id
     * @throws ServiceException the service exception
     */
    public Screen getScreenById(ObjectId screenId) throws ServiceException;

    /**
     * Gets all screens.
     *
     * @return the all screens
     */
    public List<Screen> getAllScreens();
}
