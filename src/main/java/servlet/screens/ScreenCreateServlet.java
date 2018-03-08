package servlet.screens;

import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import service.servlet.ComponentManagementService;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.net.MalformedURLException;

import static servlet.screens.ScreenListServlet.LIST_ROUTE;

public class ScreenCreateServlet extends HttpServlet {


    static final String CREATE_URL = "/WEB-INF/pages/screens/create.jsp";
    static final String CREATE_ROUTE = "create";

    @Inject
    private ScreenManagementService screenManagementService;

    @Inject
    private ComponentManagementService componentManagementService;

    @Context
    private ResourceInfo resourceInfo;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter(ParameterNames.APPLICATION_ID);
        String screenId = request.getParameter(ParameterNames.SCREEN_ID);
        if (applicationId == null || applicationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ObjectId appObjId = new ObjectId(applicationId);
        if (screenId != null) {
            ObjectId screenObjId = new ObjectId(screenId);

            Screen screen = screenManagementService.findScreenById(screenObjId);
            request.setAttribute(ParameterNames.SCREEN_ID, screen.getId());
            request.setAttribute(ParameterNames.SCREEN_URL, screen.getScreenUrl());
            request.setAttribute(ParameterNames.SCREEN_KEY, screen.getKey());
            request.setAttribute(ParameterNames.SCREEN_NAME, screen.getName());
            request.setAttribute(ParameterNames.SCREEN_MENU_ORDER, screen.getMenuOrder());
            request.setAttribute(ParameterNames.LINKED_COMPONENTS, screen.getComponents());
            request.setAttribute(ParameterNames.COMPONENTS_OPTIONS,
                    componentManagementService.getComponentsNotInScreen(screenObjId, appObjId));
        } else {
            request.setAttribute(ParameterNames.COMPONENTS_OPTIONS,
                    componentManagementService.getAllComponentsByApplication(appObjId));
            request.setAttribute(ParameterNames.SCREEN_MENU_ORDER, screenManagementService.getScreenCount(appObjId) + 1);
        }
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);


        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appIdString = req.getParameter(ParameterNames.APPLICATION_ID);
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String screenId = req.getParameter(ParameterNames.SCREEN_ID);
        String linkedComponentsCountString = req.getParameter(ParameterNames.LINKED_COMPONENTS_COUNT);
        try {
            Screen screen = getScreen(screenId);
            updateScreenProperties(req, appIdString, screen);
            updateLinkedComponents(req, Integer.parseInt(linkedComponentsCountString), screen);
            createOrUpdateScreen(screenId, screen);
            resp.sendRedirect(LIST_ROUTE + "?app=" + appIdString);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("screenUrlError", "URL has bad format.");
        }

        setInputToRequest(req, appIdString);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }

    private void setInputToRequest(HttpServletRequest req, String appIdString) {
        req.setAttribute(ParameterNames.SCREEN_KEY, req.getParameter(ParameterNames.SCREEN_KEY));
        req.setAttribute(ParameterNames.SCREEN_URL, req.getParameter(ParameterNames.SCREEN_URL));
        req.setAttribute(ParameterNames.SCREEN_NAME, req.getParameter(ParameterNames.SCREEN_NAME));
        req.setAttribute(ParameterNames.SCREEN_MENU_ORDER, req.getParameter(ParameterNames.SCREEN_MENU_ORDER));
        req.setAttribute(ParameterNames.APPLICATION_ID, appIdString);
    }

    private void createOrUpdateScreen(String screenId, Screen screen) {
        if (screenId == null || screenId.isEmpty()) {
            screenManagementService.addNewScreen(screen);
        } else {
            screenManagementService.updateScreen(screen);
        }
    }

    private void updateLinkedComponents(HttpServletRequest req, int linkedComponentsCount, Screen screen) {
        if (screen.getComponents() != null) {
            screen.getComponents().clear();
        }
        for (int i = 0; i < linkedComponentsCount; i++) {
            String componentId = req.getParameter(ParameterNames.LINKED_COMPONENT_ID + (i + 1));
            ComponentResource componentResource = componentManagementService.findById(new ObjectId(componentId));
            componentManagementService.addComponentToScreen(componentResource, screen);
            componentManagementService.filterComponentsScreenReferences(componentResource);
        }
    }

    private String updateScreenProperties(HttpServletRequest req, String appIdString, Screen screen) {
        screen.setApplicationId(new ObjectId(appIdString));
        String screenUrl = req.getParameter(ParameterNames.SCREEN_URL);
        String screenName = req.getParameter(ParameterNames.SCREEN_NAME);
        String key = req.getParameter(ParameterNames.SCREEN_KEY);
        String menuOrder = req.getParameter(ParameterNames.SCREEN_MENU_ORDER);
        if (screenUrl == null || screenUrl.isEmpty()) {
            screenUrl = req.getScheme() +
                    "://" +
                    req.getServerName() +
                    ":" +
                    req.getServerPort() +
                    req.getContextPath() +
                    "/api/screens/" +
                    screen.getId();
        }
        screen.setKey(key);
        screen.setScreenUrl(screenUrl);
        if (screenName != null && !screenName.isEmpty()) {
            screen.setName(screenName);
        }
        screen.setMenuOrder(Integer.parseInt(menuOrder));
        return screenUrl;
    }

    private Screen getScreen(String screenId) {
        Screen screen;
        if (screenId == null || screenId.isEmpty()) {
            screen = new Screen();
            screen.setId(new ObjectId());
        } else {
            screen = screenManagementService.findScreenById(new ObjectId(screenId));
        }
        return screen;
    }
}
