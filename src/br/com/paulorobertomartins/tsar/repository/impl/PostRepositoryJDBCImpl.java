package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Post;
import br.com.paulorobertomartins.tsar.repository.PostRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
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
public class PostRepositoryJDBCImpl extends AbstractJDBCRepository implements PostRepository {

    private final UserRepository userRepository;

    public PostRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Post entity) {
        final String command = "INSERT INTO \"post\" (post_id, \"content\", user_id, created_at, update_at) VALUES (?, ? , ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, entity.getPostId());
            preparedStatement.setString(2, entity.getContent());
            preparedStatement.setLong(3, entity.getUser().getUserId());
            preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Post entity) {
        final String command = "UPDATE \"post\" SET \"content\" = ?, user_id = ?, update_at = ? WHERE post_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setString(1, entity.getContent());
            preparedStatement.setLong(2, entity.getUser().getUserId());
            preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
            preparedStatement.setLong(4, entity.getPostId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
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
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
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
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setPostId(resultSet.getLong("post_id"));
                post.setContent(resultSet.getString("content"));
                post.setUser(userRepository.findById(resultSet.getLong("user_id")));
                post.setUpdatedAt(resultSet.getDate("update_at"));
                post.setCreatedAt(resultSet.getDate("created_at"));
                return post;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<Post> listAll() {
        final String command = "SELECT post_id, \"content\", user_id, created_at, update_at FROM \"post\"";

        List<Post> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setPostId(resultSet.getLong("post_id"));
                post.setContent(resultSet.getString("content"));
                post.setUser(userRepository.findById(resultSet.getLong("user_id")));
                post.setUpdatedAt(resultSet.getDate("update_at"));
                post.setCreatedAt(resultSet.getDate("created_at"));
                list.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public long count() {
        final String command = "SELECT COUNT(post_id) FROM \"post\"";

        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(PostRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
