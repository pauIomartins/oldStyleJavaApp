package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Comment;
import br.com.paulorobertomartins.tsar.repository.CommentRepository;
import br.com.paulorobertomartins.tsar.repository.PostRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
import br.com.paulorobertomartins.tsar.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo.martins
 */
public class CommentRepositoryJDBCImpl implements CommentRepository {

    private Connection connection;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
        this.postRepository = new PostRepositoryJDBCImpl();
    }

    @Override
    public void create(Comment entity) {
        final String command = "INSERT INTO \"comment\" (comment_id, \"content\", post_id, user_id, created_at, update_at) VALUES (?, ? , ?, ?, ?)";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getCommentId());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setLong(3, entity.getPost().getPostId());                
                preparedStatement.setLong(4, entity.getUser().getUserId());
                preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
                preparedStatement.setDate(6, new java.sql.Date(new Date().getTime()));
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Comment entity) {
        final String command = "UPDATE \"comment\" SET \"content\" = ?, post_id = ?, user_id = ?, update_at = ? WHERE comment_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setString(1, entity.getContent());
                preparedStatement.setLong(2, entity.getUser().getUserId());
                preparedStatement.setLong(2, entity.getPost().getPostId());
                preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
                preparedStatement.setLong(4, entity.getCommentId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Comment entity) {
        this.deleteById(entity.getCommentId());
    }

    @Override
    public void deleteById(Long id) {
        final String command = "DELETE FROM \"comment\" WHERE comment_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, id);
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public boolean exists(Comment entity) {
        return this.findById(entity.getCommentId()) != null;
    }

    @Override
    public Comment findById(Long id) {
        final String command = "SELECT comment_id, \"content\", post_id, user_id, created_at, update_at FROM \"comment\" WHERE comment_id = ?";
        ResultSet resultSet;
        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getLong("comment_id"));
                    comment.setContent(resultSet.getString("\"content\""));
                    comment.setPost(postRepository.findById(resultSet.getLong("post_id")));                    
                    comment.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    comment.setUpdatedAt(resultSet.getDate("update_at"));
                    comment.setCreatedAt(resultSet.getDate("created_at"));                    
                    return comment;
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Comment> listAll() {
        // Make SQL query to retrive all comments
        final String command = "SELECT comment_id, \"content\", post_id, user_id, created_at, update_at FROM \"comment\"";

        // Create a new list to return
        List<Comment> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Comments
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                // Loop all Comments in ResultSet
                while (resultSet.next()) {

                    // For each line create a new Comment and populate it 
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getLong("comment_id"));
                    comment.setContent(resultSet.getString("\"content\""));
                    comment.setPost(postRepository.findById(resultSet.getLong("post_id")));
                    comment.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    comment.setUpdatedAt(resultSet.getDate("update_at"));
                    comment.setCreatedAt(resultSet.getDate("created_at"));                     

                    // Add comment to the list that will be returned
                    list.add(comment);
                }
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public long count() {
        // Make SQL query to retrive comments count
        final String command = "SELECT COUNT(comment_id) FROM \"comment\"";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Comments
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                resultSet.next();
                return resultSet.getLong(1);
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
