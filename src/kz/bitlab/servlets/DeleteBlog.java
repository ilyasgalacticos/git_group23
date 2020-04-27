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

@WebServlet("/deleteblog")
public class DeleteBlog extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redirect = "/";
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");

        if(currentUser!=null) {

            redirect = "/";

            Long id = Long.parseLong(request.getParameter("id"));

            if (id!=null) {

                Blogs blog = DBManager.getBlog(id);

                if(blog.getUser().getId()==currentUser.getId()){

                    DBManager.deleteBlog(blog);
                    redirect = "/";

                }

            }
        }

        response.sendRedirect(redirect);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
