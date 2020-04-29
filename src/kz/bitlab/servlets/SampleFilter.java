package kz.bitlab.servlets;

import kz.bitlab.db.DBManager;
import kz.bitlab.db.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SampleFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest)req).getSession();
        Users sessionUser = (Users) session.getAttribute("CURRENT_USER");

        if(sessionUser!=null){

            Users user = DBManager.getUser(sessionUser.getEmail());
            if(user.getPassword().equals(sessionUser.getPassword())){

                req.setAttribute("currentUser", user);

            }else{

                session.removeAttribute("CURRENT_USER");

            }

        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
