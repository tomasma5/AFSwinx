package service.servlet;

import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;

import java.util.List;

public interface ScreenManagementService {

    public void addNewScreen(Screen screen);

    public void removeScreen(ObjectId id);

    public void updateScreen(Screen updatedScreen);

    public List<Screen> getAllScreensByApplication(ObjectId applicationId);

    public Screen findScreenById(ObjectId id);

    public int getScreenCount(ObjectId applicationId);

}
