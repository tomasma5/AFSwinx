package service.rest;

import model.Screen;
import org.bson.types.ObjectId;
import service.exception.ServiceException;

import java.util.List;

public interface ScreenRestService {

    public Screen getScreenById(ObjectId screenId) throws ServiceException;

    public Screen getScreenByName(String name) throws ServiceException;

    public List<Screen> getAllScreens();
}
