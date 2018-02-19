package servlet.screens;

import model.Screen;
import org.bson.types.ObjectId;
import service.ScreenManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ScreenCreateServlet extends HttpServlet {



    private static final String CREATE_URL = "/WEB-INF/pages/screens/create.jsp";

    @Inject
    private ScreenManagementService screenManagementService;

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
        }
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO refactor???
        String appIdString = req.getParameter(ParameterNames.APPLICATION_ID);
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String screenId = req.getParameter(ParameterNames.SCREEN_ID);
        String heading = req.getParameter(ParameterNames.SCREEN_HEADING);
        String screenUrl = req.getParameter(ParameterNames.SCREEN_URL);
        try {
            new URL(screenUrl); //check format of url with trying to create URL object

            Screen screen;
            if (screenId == null || screenId.isEmpty()) {
                screen = new Screen();
            } else {
                screen = screenManagementService.findScreenById(new ObjectId(screenId));
            }

            screen.setHeading(heading);
            screen.setScreenUrl(screenUrl);
            screen.setApplicationId(new ObjectId(appIdString));

            if (screenId == null || screenId.isEmpty()) {
                screenManagementService.addNewScreen(screen);
            } else {
                screenManagementService.updateScreen(screen);
            }
            resp.sendRedirect("list?app="+appIdString);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("screenUrlError", "URL has bad format.");
        }

        req.setAttribute(ParameterNames.SCREEN_HEADING, heading);
        req.setAttribute(ParameterNames.SCREEN_URL, screenUrl);
        req.setAttribute(ParameterNames.APPLICATION_ID, appIdString);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }
}
