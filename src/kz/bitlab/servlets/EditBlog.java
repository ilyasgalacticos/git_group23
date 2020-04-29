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

@WebServlet("/editblog")
public class EditBlog extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redirect = "/";
        Users currentUser = (Users) request.getSession().getAttribute("CURRENT_USER");

        if(currentUser!=null) {

            redirect = "/";

            Long id = Long.parseLong(request.getParameter("id"));
            String title = request.getParameter("title");
            String shortContent = request.getParameter("short_content");
            String content = request.getParameter("content");

            if (id!=null && title != null && shortContent != null && content != null) {

                Blogs blog = DBManager.getBlog(id);
                if(blog.getUser().getId()==currentUser.getId()){

                    blog.setTitle(title);
                    blog.setShortContent(shortContent);
                    blog.setContent(content);

                    DBManager.updateBlog(blog);
                    redirect = "/readblog?id="+id+"&success";
                }

            }
        }

        response.sendRedirect(redirect);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Users currentUser = (Users) request.getAttribute("currentUser");

        if(currentUser!=null) {

            Long id = Long.parseLong(request.getParameter("id"));
            Blogs blog = DBManager.getBlog(id);

            if(blog != null && currentUser.getId()==blog.getUser().getId()) {

                request.setAttribute("blog", blog);
                request.getRequestDispatcher("/editblog.jsp").forward(request, response);

            } else {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }

        }else{
            response.sendRedirect("/");
        }
    }
}
