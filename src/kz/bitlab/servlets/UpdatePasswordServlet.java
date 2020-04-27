package kz.bitlab.servlets;

import kz.bitlab.db.DBManager;
import kz.bitlab.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updatepassword")
public class UpdatePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redirect = "/";
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");

        if(currentUser!=null) {

            redirect = "/profile?error";

            String oldPass = request.getParameter("old_password");
            String newPass = request.getParameter("new_password");
            String reNewPass = request.getParameter("confirm_new_password");

            if (oldPass != null && !oldPass.trim().equals("") && newPass != null && !newPass.trim().equals("") && reNewPass != null && !reNewPass.trim().equals("")) {

                Users user = DBManager.getUser(currentUser.getEmail());

                if(oldPass.equals(user.getPassword())){

                    if(newPass.equals(reNewPass)){

                        user.setPassword(newPass);
                        DBManager.updatePassword(user);

                        request.getSession().setAttribute("CURRENT_USER", user);

                        redirect = "/profile?success";

                    }

                }

            }

        }

        response.sendRedirect(redirect);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
