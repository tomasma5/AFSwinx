package service;

import model.Screen;
import org.bson.types.ObjectId;

public interface ScreenManagementService {

    public void addNewScreen(Screen screen);

    public void removeScreen(ObjectId id);

    public void updateScreen(Screen updatedScreen);


}
