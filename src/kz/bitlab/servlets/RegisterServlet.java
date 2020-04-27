package kz.bitlab.servlets;

import kz.bitlab.db.DBManager;
import kz.bitlab.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redirect = "/register?error";

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re_password");
        String fullName = request.getParameter("full_name");

        if(email!=null&&!email.trim().equals("")
                &&password!=null&&!password.trim().equals("")
                &&rePassword!=null&&!rePassword.trim().equals("")
                &&fullName!=null){

            Users found = DBManager.getUser(email);
            if(found==null){

                if(rePassword.equals(password)){

                    DBManager.addUser(new Users(null, email, password, fullName));
                    redirect = "/register?success";

                }

            }

        }

        response.sendRedirect(redirect);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
