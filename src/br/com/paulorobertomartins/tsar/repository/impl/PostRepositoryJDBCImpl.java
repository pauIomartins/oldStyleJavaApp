package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Post;
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
public class PostRepositoryJDBCImpl implements PostRepository {

    private Connection connection;
    private final UserRepository userRepository;

    public PostRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Post entity) {
        final String command = "INSERT INTO \"post\" (post_id, \"content\", user_id, created_at, update_at) VALUES (?, ? , ?, ?, ?)";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getPostId());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setLong(3, entity.getUser().getUserId());
                preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
                preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Post entity) {
        final String command = "UPDATE \"post\" SET \"content\" = ?, user_id = ?, update_at = ? WHERE post_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setString(1, entity.getContent());
                preparedStatement.setLong(2, entity.getUser().getUserId());
                preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
                preparedStatement.setLong(4, entity.getPostId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Post entity) {
        this.deleteById(entity.getPostId());
    }

    @Override
    public void deleteById(Long id) {
        final String command = "DELETE FROM \"post\" WHERE post_id = ?";
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
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public boolean exists(Post entity) {
        return this.findById(entity.getPostId()) != null;
    }

    @Override
    public Post findById(Long id) {
        final String command = "SELECT post_id, \"content\", user_id, created_at, update_at FROM \"post\" WHERE post_id = ?";
        ResultSet resultSet;
        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("post_id"));
                    post.setContent(resultSet.getString("\"content\""));
                    post.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    post.setUpdatedAt(resultSet.getDate("update_at"));
                    post.setCreatedAt(resultSet.getDate("created_at"));                    
                    return post;
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> listAll() {
        // Make SQL query to retrive all posts
        final String command = "SELECT post_id, \"content\", user_id, created_at, update_at FROM \"post\"";

        // Create a new list to return
        List<Post> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Posts
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                // Loop all Posts in ResultSet
                while (resultSet.next()) {

                    // For each line create a new Post and populate it 
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("post_id"));
                    post.setContent(resultSet.getString("\"content\""));
                    post.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    post.setUpdatedAt(resultSet.getDate("update_at"));
                    post.setCreatedAt(resultSet.getDate("created_at"));                     

                    // Add post to the list that will be returned
                    list.add(post);
                }
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public long count() {
        // Make SQL query to retrive posts count
        final String command = "SELECT COUNT(post_id) FROM \"post\"";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Posts
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                resultSet.next();
                return resultSet.getLong(1);
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
