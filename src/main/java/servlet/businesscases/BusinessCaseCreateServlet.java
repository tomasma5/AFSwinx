package servlet.businesscases;

import service.servlet.BusinessCaseManagementService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BusinessCaseCreateServlet extends HttpServlet {

    static final String CREATE_URL = "/WEB-INF/pages/businesscases/create.jsp";
    static final String CREATE_ROUTE = "create";

    @Inject
    private BusinessCaseManagementService bcManagementService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
