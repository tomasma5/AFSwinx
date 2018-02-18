package servlet.screens;

import org.bson.types.ObjectId;
import service.ScreenManagementService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScreenCreateServlet extends HttpServlet {

    @Inject
    private ScreenManagementService screenManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Todo implement me
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo implement delete
    }
}
