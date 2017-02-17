package br.com.paulorobertomartins.tsar.repository.factory;

import br.com.paulorobertomartins.tsar.model.Comment;
import br.com.paulorobertomartins.tsar.model.Password;
import br.com.paulorobertomartins.tsar.model.Post;
import br.com.paulorobertomartins.tsar.model.Profile;
import br.com.paulorobertomartins.tsar.model.User;
import br.com.paulorobertomartins.tsar.repository.Repository;
import br.com.paulorobertomartins.tsar.repository.impl.CommentRepositoryJDBCImpl;
import br.com.paulorobertomartins.tsar.repository.impl.PasswordRepositoryJDBCImpl;
import br.com.paulorobertomartins.tsar.repository.impl.PostRepositoryJDBCImpl;
import br.com.paulorobertomartins.tsar.repository.impl.ProfileRepositoryJDBCImpl;
import br.com.paulorobertomartins.tsar.repository.impl.UserRepositoryJDBCImpl;

/**
 *
 * @author paulo.martins
 */
public class RepositoryFactory {

    public static Repository getRepository(Class entityClass) {

        if (entityClass == User.class) {
            return new UserRepositoryJDBCImpl();
        } else if (entityClass == Password.class) {
            return new PasswordRepositoryJDBCImpl();
        } else if (entityClass == Profile.class) {
            return new ProfileRepositoryJDBCImpl();
        } else if (entityClass == Post.class) {
            return new PostRepositoryJDBCImpl();
        } else if (entityClass == Comment.class) {
            return new CommentRepositoryJDBCImpl();
        } else {
            return null;
        }
    }
}
