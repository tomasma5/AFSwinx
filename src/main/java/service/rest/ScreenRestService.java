package service.rest;

import model.Screen;
import org.bson.types.ObjectId;
import service.exception.ServiceException;

import java.util.List;

public interface ScreenRestService {

    public Screen getScreenById(ObjectId screenId) throws ServiceException;

    public List<Screen> getAllScreens();
}
