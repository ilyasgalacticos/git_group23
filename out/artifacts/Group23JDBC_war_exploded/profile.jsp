<%@ page import="java.util.ArrayList" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
  <head>
    <%@include file="templates/head.jsp"%>
  </head>
  <body>
    <div class="container">
      <%@include file="templates/navbar.jsp"%>
    </div>
    <div class="container">
      <div class="row mt-5">
        <div class="col-sm-6 offset-3">
          <%
            String usererror = request.getParameter("usererror");
            if(usererror!=null){
          %>
          <div class="alert alert-danger">
            Couldn't update user
          </div>
          <%
            }
          %>
          <%
            String usersuccess = request.getParameter("usersuccess");
            if(usersuccess!=null){
          %>
          <div class="alert alert-success">
            User updated successfully
          </div>
          <%
            }
          %>
          <form action="/profile" method="post">
            <div class="form-group">
              <label>
                Full Name :
              </label>
              <input type="text" class="form-control" name="full_name" value="<%=currentUser.getFullName()%>">
            </div>
            <button class="btn btn-success">
              UPDATE PROFILE
            </button>
          </form>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-sm-6 offset-3">
          <%
            String error = request.getParameter("error");
            if(error!=null){
          %>
          <div class="alert alert-danger">
            Couldn't update password
          </div>
          <%
            }
          %>
          <%
            String success = request.getParameter("success");
            if(success!=null){
          %>
          <div class="alert alert-success">
            Password updated successfully
          </div>
          <%
            }
          %>
          <form action="/updatepassword" method="post">
            <div class="form-group">
              <label>
                Old Password :
              </label>
              <input type="password" class="form-control" name="old_password">
            </div>
            <div class="form-group">
              <label>
                New Password :
              </label>
              <input type="password" class="form-control" name="new_password">
            </div>
            <div class="form-group">
              <label>
                Confirm New Password :
              </label>
              <input type="password" class="form-control" name="confirm_new_password">
            </div>
            <button class="btn btn-success">
              UPDATE PASSWORD
            </button>
          </form>
        </div>
      </div>
    </div>
    <%@include file="templates/scripts.jsp"%>
  </body>
</html>