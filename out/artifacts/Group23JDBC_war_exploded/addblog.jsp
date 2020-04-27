<%@ page import="java.util.ArrayList" %>
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
      <div class="row mt-5">
        <div class="col-sm-10 offset-1">
          <form action="/addblog" method="post">
            <div class="form-group">
              <label>
                TITLE:
              </label>
              <input type="text" name="title" class="form-control">
            </div>
            <div class="form-group">
              <label>
                SHORT CONTENT:
              </label>
              <textarea name = "short_content"></textarea>
            </div>
            <div class="form-group">
              <label>
                CONTENT:
              </label>
              <textarea name = "content"></textarea>
            </div>
            <div class="form-group">
              <button class="btn btn-success">ADD BLOG</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <%@include file="templates/scripts.jsp"%>
  </body>
</html>