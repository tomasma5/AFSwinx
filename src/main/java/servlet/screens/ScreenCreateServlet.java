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
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.net.MalformedURLException;

import static java.util.stream.Collectors.toList;

public class ScreenCreateServlet extends HttpServlet {


    private static final String CREATE_URL = "/WEB-INF/pages/screens/create.jsp";

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

        if (screenId != null) {
            Screen screen = screenManagementService.findScreenById(new ObjectId(screenId));
            request.setAttribute(ParameterNames.SCREEN_ID, screen.getId());
            request.setAttribute(ParameterNames.SCREEN_URL, screen.getScreenUrl());
            request.setAttribute(ParameterNames.SCREEN_HEADING, screen.getHeading());
            request.setAttribute(ParameterNames.LINKED_COMPONENTS, screen.getComponents());
            request.setAttribute(ParameterNames.COMPONENTS_OPTIONS, componentManagementService
                    .getAllComponentsByApplication(new ObjectId(applicationId)).stream()
                    .filter(componentResource -> componentResource.getReferencedScreensIds() == null || !componentResource.getReferencedScreensIds().contains(new ObjectId(screenId)))
                    .collect(toList()));
        } else {
            request.setAttribute(ParameterNames.COMPONENTS_OPTIONS, componentManagementService.getAllComponentsByApplication(new ObjectId(applicationId)));
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
        String heading = req.getParameter(ParameterNames.SCREEN_HEADING);
        String screenUrl = req.getParameter(ParameterNames.SCREEN_URL);
        String linkedComponentsCountString = req.getParameter(ParameterNames.LINKED_COMPONENTS_COUNT);
        try {
            int linkedComponentsCount = Integer.parseInt(linkedComponentsCountString);

            Screen screen;
            if (screenId == null || screenId.isEmpty()) {
                screen = new Screen();
                screen.setId(new ObjectId());
            } else {
                screen = screenManagementService.findScreenById(new ObjectId(screenId));
            }

            screen.setHeading(heading);
            screen.setApplicationId(new ObjectId(appIdString));
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
            screen.setScreenUrl(screenUrl);

            if (screen.getComponents() != null) {
                screen.getComponents().clear();
            }
            for (int i = 0; i < linkedComponentsCount; i++) {
                String componentId = req.getParameter(ParameterNames.LINKED_COMPONENT_ID + (i + 1));
                ComponentResource componentResource = componentManagementService.findById(new ObjectId(componentId));
                componentManagementService.addComponentToScreen(componentResource, screen);
                componentManagementService.filterComponentsScreenReferences(componentResource);
            }


            if (screenId == null || screenId.isEmpty()) {

                screenManagementService.addNewScreen(screen);
            } else {
                screenManagementService.updateScreen(screen);
            }
            resp.sendRedirect("list?app=" + appIdString);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("screenUrlError", "URL has bad format.");
        }

        req.setAttribute(ParameterNames.SCREEN_HEADING, heading);
        req.setAttribute(ParameterNames.APPLICATION_ID, appIdString);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }
}
