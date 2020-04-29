package kz.bitlab.servlets;

import kz.bitlab.db.DBManager;
import kz.bitlab.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");

        if(currentUser!=null) {

            String fullName = request.getParameter("full_name");

            if(fullName!=null){

                Users user = DBManager.getUser(currentUser.getEmail());
                user.setFullName(fullName);
                DBManager.updateProfile(user);

                request.getSession().setAttribute("CURRENT_USER", user);

                response.sendRedirect("/profile?usersuccess");

            }

        }else{

            response.sendRedirect("/");

        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Users currentUser = (Users) request.getAttribute("currentUser");

        if(currentUser!=null) {
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }else{
            response.sendRedirect("/login");
        }

    }
}
