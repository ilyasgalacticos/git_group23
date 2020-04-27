package kz.bitlab.servlets;

import kz.bitlab.db.Blogs;
import kz.bitlab.db.DBManager;
import kz.bitlab.db.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addblog")
public class AddBlog extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redirect = "/";
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");

        if(currentUser!=null) {

            redirect = "/addblog?error";

            String title = request.getParameter("title");
            String shortContent = request.getParameter("short_content");
            String content = request.getParameter("content");

            if (title != null && shortContent != null && content != null) {

                DBManager.addBlog(new Blogs(null, currentUser, title, shortContent, content, null));
                redirect = "/addblog?success";

            }
        }

        response.sendRedirect(redirect);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");
        if(currentUser!=null) {
            request.getRequestDispatcher("/addblog.jsp").forward(request, response);
        }else{
            response.sendRedirect("/login");
        }
    }
}
