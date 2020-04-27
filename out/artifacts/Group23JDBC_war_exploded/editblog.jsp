<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.db.Blogs" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
  <head>
    <%@include file="templates/head.jsp"%>
    <script src="/tinymce/tinymce.min.js" referrerpolicy="origin"></script>
    <script>tinymce.init({selector:'textarea', menubar: "insert"});</script>
  </head>
  <body>

    <div class="container">
      <%@include file="templates/navbar.jsp"%>
    </div>
    <div class="container">
      <div class="row mt-5 mb-5 pb-5">
        <div class="col-sm-10 offset-1">

          <%
            Blogs blog = (Blogs) request.getAttribute("blog");
            if(blog!=null){
          %>

          <form action="/editblog" method="post">
            <input type="hidden" name="id" value="<%=blog.getId()%>">
            <div class="form-group">
              <label>
                TITLE:
              </label>
              <input type="text" name="title" class="form-control" value="<%=blog.getTitle()%>">
            </div>
            <div class="form-group">
              <label>
                SHORT CONTENT:
              </label>
              <textarea name = "short_content"><%=blog.getShortContent()%></textarea>
            </div>
            <div class="form-group">
              <label>
                CONTENT:
              </label>
              <textarea name = "content"><%=blog.getContent()%></textarea>
            </div>
            <div class="form-group">
              <button class="btn btn-success">SAVE BLOG</button>
            </div>
          </form>
          <form action="/deleteblog" method="post">
            <input type="hidden" name="id" value="<%=blog.getId()%>">
            <button class="btn btn-danger">DELETE BLOG</button>
          </form>

          <%
            }
          %>

        </div>
      </div>
    </div>
    <%@include file="templates/scripts.jsp"%>
  </body>
</html>