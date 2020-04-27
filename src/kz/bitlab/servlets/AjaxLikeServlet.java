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
import java.io.PrintWriter;

@WebServlet("/ajaxlike")
public class AjaxLikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirect = "/";
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");
        if(currentUser!=null) {
            Long blogId = Long.parseLong(request.getParameter("blog_id"));
            if (blogId != null) {
                Blogs blog = DBManager.getBlog(blogId);
                int likes = 0;
                if(blog!=null){
                    likes = DBManager.likeBlog(currentUser.getId(),blogId);
                    PrintWriter out = response.getWriter();
                    out.print(likes);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
