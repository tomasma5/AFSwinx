package servlet;

import service.ApplicationsManagementService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationsServlet extends HttpServlet {

    @Inject
    private ApplicationsManagementService applicationsManagementService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applications", applicationsManagementService.getAll());
        getServletContext().getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}
