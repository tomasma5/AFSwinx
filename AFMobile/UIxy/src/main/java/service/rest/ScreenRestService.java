package service.rest;

import model.Screen;
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
    public Screen getScreenById(int screenId) throws ServiceException;

    /**
     * Gets screen by key.
     *
     * @param screenKey the screen id
     * @return the screen by key
     * @throws ServiceException the service exception
     */
    public Screen getScreenByKey(String screenKey) throws ServiceException;

    /**
     * Gets all screens.
     *
     * @return the all screens
     */
    public List<Screen> getAllScreens();
}
