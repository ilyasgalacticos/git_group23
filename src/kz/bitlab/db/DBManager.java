package kz.bitlab.db;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private static Connection connection;

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/group23_db?useUnicode=true&serverTimezone=UTC", "root", "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addUser(Users user){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO users(email, password, full_name) " +
                    "VALUES (?, ?, ?)");

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static Users getUser(String email){

        Users user = null;

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                user = new Users(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name")
                );
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;

    }

    public static void updatePassword(Users user){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE users SET password = ? WHERE id = ?");

            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());
            statement.executeUpdate();

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void updateProfile(Users user){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE users SET full_name = ? WHERE id = ?");

            statement.setString(1, user.getFullName());
            statement.setLong(2, user.getId());
            statement.executeUpdate();

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void addBlog(Blogs blog){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO blogs(user_id, title, short_content, content, post_date) " +
                    "VALUES (?, ?, ?, ?, NOW())");

            statement.setLong(1, blog.getUser().getId());
            statement.setString(2, blog.getTitle());
            statement.setString(3, blog.getShortContent());
            statement.setString(4, blog.getContent());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void updateBlog(Blogs blog){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE blogs SET title = ?, short_content = ?, content = ? " +
                    "WHERE id = ?");


            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getShortContent());
            statement.setString(3, blog.getContent());
            statement.setLong(4, blog.getId());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void deleteBlog(Blogs blog){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM blogs WHERE id = ? AND user_id = ?");


            statement.setLong(1, blog.getId());
            statement.setLong(2, blog.getUser().getId());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Blogs> getAllBlogs(){

        ArrayList<Blogs> allBlogs = new ArrayList<>();

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT b.id, b.title, b.short_content, b.content, b.post_date, b.user_id, u.full_name, b.likes " +
                    "FROM blogs b INNER JOIN users u ON u.id = b.user_id " +
                    "ORDER BY b.post_date DESC ");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                allBlogs.add(new Blogs(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                null, null,
                                resultSet.getString("full_name")
                        ),
                        resultSet.getString("title"),
                        resultSet.getString("short_content"),
                        resultSet.getString("content"),
                        resultSet.getDate("post_date"),
                        resultSet.getInt("likes")
                ));

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return allBlogs;
    }

    public static ArrayList<Blogs> searchBlogs(String key){

        ArrayList<Blogs> allBlogs = new ArrayList<>();

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT b.id, b.title, b.short_content, b.content, b.post_date, b.user_id, u.full_name, b.likes " +
                    "FROM blogs b INNER JOIN users u ON u.id = b.user_id " +
                    "WHERE b.title LIKE ? OR b.short_content LIKE ? OR b.content LIKE ? " +
                    "ORDER BY b.post_date DESC ");

            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
            statement.setString(3, "%"+key+"%");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                allBlogs.add(new Blogs(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                null, null,
                                resultSet.getString("full_name")
                        ),
                        resultSet.getString("title"),
                        resultSet.getString("short_content"),
                        resultSet.getString("content"),
                        resultSet.getDate("post_date"),
                        resultSet.getInt("likes")
                ));

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return allBlogs;
    }


    public static Blogs getBlog(Long id){

        Blogs blog = null;

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT b.id, b.title, b.short_content, b.content, b.post_date, b.user_id, u.full_name, b.likes " +
                    "FROM blogs b INNER JOIN users u ON u.id = b.user_id " +
                    "WHERE b.id = ? ");

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                blog = new Blogs(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                null, null,
                                resultSet.getString("full_name")
                        ),
                        resultSet.getString("title"),
                        resultSet.getString("short_content"),
                        resultSet.getString("content"),
                        resultSet.getDate("post_date"),
                        resultSet.getInt("likes")
                );

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return blog;
    }

    public static void addComment(Comments comment){

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO comments(user_id, blog_id, comment, post_date) " +
                    "VALUES (?, ?, ?, NOW())");

            statement.setLong(1, comment.getUser().getId());
            statement.setLong(2, comment.getBlog().getId());
            statement.setString(3, comment.getComment());

            statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Comments> getAllCommentsByBlogId(Long id){

        ArrayList<Comments> comments = new ArrayList<>();

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT c.id, c.user_id, c.blog_id, c.comment, c.post_date, u.full_name " +
                    "FROM comments c " +
                    "LEFT OUTER JOIN users u ON u.id = c.user_id " +
                    "WHERE c.blog_id = ? " +
                    "ORDER BY c.post_date DESC ");

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                comments.add(new Comments(
                        resultSet.getLong("id"),
                        new Users(
                                resultSet.getLong("user_id"),
                                null, null,
                                resultSet.getString("full_name")
                        ),
                        new Blogs(
                                resultSet.getLong("blog_id"),
                                null, null, null, null, null
                        ),
                        resultSet.getString("comment"),
                        resultSet.getDate("post_date")
                ));
            }
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return comments;

    }

    public static int likeBlog(Long userId, Long blogId){

        int likes = 0;

        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM likes WHERE user_id = ? AND blog_id = ?");

            statement.setLong(1, userId);
            statement.setLong(2, blogId);

            ResultSet resultSet = statement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();
            statement.close();

            if(exists){
                PreparedStatement likeStatement = connection.prepareStatement("" +
                        "DELETE FROM likes WHERE user_id = ? AND blog_id = ?");
                likeStatement.setLong(1, userId);
                likeStatement.setLong(2, blogId);
                likeStatement.executeUpdate();
                likeStatement.close();
            }else{
                PreparedStatement likeStatement = connection.prepareStatement("" +
                        "INSERT INTO likes (user_id, blog_id) VALUES (?, ?)");
                likeStatement.setLong(1, userId);
                likeStatement.setLong(2, blogId);
                likeStatement.executeUpdate();
                likeStatement.close();
            }

            PreparedStatement blogStatement = connection.prepareStatement("" +
                    "SELECT COUNT(*) as like_amnt FROM likes WHERE blog_id = ?");

            blogStatement.setLong(1, blogId);
            ResultSet blogResultSet = blogStatement.executeQuery();

            if(blogResultSet.next()){
                likes = blogResultSet.getInt("like_amnt");
            }

            blogResultSet.close();
            blogStatement.close();

            PreparedStatement lastStatement = connection.prepareStatement("" +
                    "UPDATE blogs SET likes = ? WHERE id = ?");
            lastStatement.setInt(1, likes);
            lastStatement.setLong(2, blogId);
            lastStatement.executeUpdate();
            lastStatement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return likes;

    }

}
