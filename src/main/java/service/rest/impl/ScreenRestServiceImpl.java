package service.rest.impl;

import dao.ScreenDao;
import model.Application;
import model.Screen;
import rest.security.RequestContext;
import service.exception.ServiceException;
import service.rest.ScreenRestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of service for getting data from application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ScreenRestServiceImpl implements ScreenRestService {

    private static final Logger LOGGER = Logger.getLogger(ScreenRestServiceImpl.class.getName());

    /**
     * The Screen dao.
     */
    @Inject
    ScreenDao screenDao;

    /**
     * The Request context.
     */
    @Inject
    RequestContext requestContext;

    @Override
    public Screen getScreenById(int screenId) throws ServiceException {
        Application application = requestContext.getCurrentApplication();
        if (application != null) {
            Screen screen = screenDao.getScreenByIdWithLoadedComponents(screenId);
            if (screen.getApplication().getId().equals(application.getId())) {
                return screen;
            } else {
                //this screen belongs to another application
                String errorMsg = "Cannot get screen, this screen belongs to another application";
                LOGGER.log(Level.SEVERE, errorMsg);
                throw new ServiceException(errorMsg);
            }
        }
        return null;
    }

    @Override
    public List<Screen> getAllScreens() {
        Application application = requestContext.getCurrentApplication();
        if (application != null) {
            return screenDao.getScreensWithLoadedComponents().stream()
                    .filter(screen -> screen.getApplication().getId() == application.getId())
                    .collect(toList());
        }
        return null;
    }
}
