package servlet.screens;

import model.Screen;
import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
import service.servlet.ComponentManagementService;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;
import utils.Utils;

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

/**
 * Servlet for creating or edition {@link Screen}
 */
public class ScreenCreateServlet extends HttpServlet {


    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/screens/create.jsp";
    /**
     * The Create route.
     */
    static final String CREATE_ROUTE = "create";

    @Inject
    private ScreenManagementService screenManagementService;

    @Inject
    private ComponentManagementService componentManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

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
        String appIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String screenId = Utils.trimString(req.getParameter(ParameterNames.SCREEN_ID));
        String linkedComponentsCountString = Utils.trimString(req.getParameter(ParameterNames.LINKED_COMPONENTS_COUNT));
        try {
            Screen screen = screenManagementService.findOrCreateNewScreen(screenId);
            updateScreenProperties(req, appIdString, screen);
            componentManagementService.updateLinkedComponents(req, Integer.parseInt(linkedComponentsCountString), screen);
            createOrUpdateScreen(screenId, screen);
            resp.sendRedirect(LIST_ROUTE + "?"+ParameterNames.APPLICATION_ID+"=" + appIdString);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("screenUrlError", "URL has bad format.");
        }

        setInputToRequest(req, appIdString);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }

    private void setInputToRequest(HttpServletRequest req, String appIdString) {
        req.setAttribute(ParameterNames.SCREEN_KEY, Utils.trimString(req.getParameter(ParameterNames.SCREEN_KEY)));
        req.setAttribute(ParameterNames.SCREEN_URL, Utils.trimString(req.getParameter(ParameterNames.SCREEN_URL)));
        req.setAttribute(ParameterNames.SCREEN_NAME, Utils.trimString(req.getParameter(ParameterNames.SCREEN_NAME)));
        req.setAttribute(ParameterNames.SCREEN_MENU_ORDER, Utils.trimString(req.getParameter(ParameterNames.SCREEN_MENU_ORDER)));
        req.setAttribute(ParameterNames.APPLICATION_ID, appIdString);
    }

    private void createOrUpdateScreen(String screenId, Screen screen) {
        if (screenId == null || screenId.isEmpty()) {
            screenManagementService.addNewScreen(screen);
        } else {
            screenManagementService.updateScreen(screen);
        }
    }

    private void updateScreenProperties(HttpServletRequest req, String appIdString, Screen screen) {
        ObjectId appId = new ObjectId(appIdString);
        screen.setApplicationId(appId);
        String screenUrl = Utils.trimString(req.getParameter(ParameterNames.SCREEN_URL));
        String screenName = Utils.trimString(req.getParameter(ParameterNames.SCREEN_NAME));
        String key = Utils.trimString(req.getParameter(ParameterNames.SCREEN_KEY));
        String menuOrder = Utils.trimString(req.getParameter(ParameterNames.SCREEN_MENU_ORDER));
        screenUrl = screenManagementService.buildScreenUrl(applicationsManagementService.findById(appId), screen, screenUrl, req.getContextPath());
        screen.setKey(key);
        screen.setScreenUrl(screenUrl);
        if (screenName != null && !screenName.isEmpty()) {
            screen.setName(screenName);
        }
        screen.setMenuOrder(Integer.parseInt(menuOrder));
    }



}
