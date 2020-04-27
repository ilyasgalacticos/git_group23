<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.db.Blogs" %>
<%@ page import="kz.bitlab.db.Comments" %>
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
        <div class="col-sm-12">

          <%
            Blogs blog = (Blogs) request.getAttribute("blog");
            if(blog!=null){
          %>
          <div class="jumbotron">
            <h4>
              <%=blog.getTitle()%>
            </h4>
            <p class="lead">
              <%=blog.getShortContent()%>
            </p>
            <p class="lead">
              <%=blog.getContent()%>
            </p>
            <hr class="my-4">
            <p>Posted by <%=blog.getUser().getFullName()%> at <%=blog.getPostDate()%></p>
            <%
              if(currentUser!=null){
            %>
              <a href="JavaScript:void(0)" style="text-decoration: none;" onclick="toLike(<%=blog.getId() %>)"><span>&#10084;</span></a>
            <%
              }
            %>
            <span id = "like_amount_id"><%=blog.getLikes()%></span>
            likes
            <br>
            <%
              if(currentUser!=null){
                if(blog.getUser().getId()==currentUser.getId()){
            %>
              <br>
              <a href="/editblog?id=<%=blog.getId()%>" class="btn btn-info btn-sm">EDIT BLOG</a>
            <%
                }
              }
            %>
            <%
              if(currentUser!=null){
            %>
              <form action="/addcomment" method="post">
                <div class="row mt-4">
                  <div class="col-sm-12">
                    <input type="hidden" name="blog_id" value="<%=blog.getId()%>">
                    <textarea class="form-control" name="comment"></textarea>
                    <button class="btn btn-success mt-3">ADD COMMENT</button>
                  </div>
                </div>
              </form>
            <%
              }
            %>
            <div class="list-group mt-3">
              <%
                ArrayList<Comments> comments = (ArrayList<Comments>)request.getAttribute("comments");
                if(comments!=null){
                  for(Comments c : comments){
              %>
              <a class="list-group-item list-group-item-action">
                <div class="d-flex w-100 justify-content-between">
                  <h5 class="mb-1">
                    <%=c.getUser().getFullName()%>
                  </h5>
                  <small>
                    <%=c.getPostDate()%>
                  </small>
                </div>
                <p class="mb-1">
                  <%=c.getComment()%>
                </p>
              </a>
              <%
                  }
                }
              %>
            </div>

          </div>
          <%
            }
          %>

        </div>
      </div>
    </div>
    <%@include file="templates/scripts.jsp"%>
    <%
      if(currentUser!=null){
    %>
    <script type="text/javascript">
      function toLike(blogId) {
        $.post("/ajaxlike", {
          blog_id: blogId
        }, function (result) {
          $("#like_amount_id").html(result);
        });
      }
    </script>
    <%
      }
    %>
  </body>
</html>