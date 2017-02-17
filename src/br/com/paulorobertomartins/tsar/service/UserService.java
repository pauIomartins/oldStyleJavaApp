package br.com.paulorobertomartins.tsar.service;

import br.com.paulorobertomartins.tsar.model.User;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
import br.com.paulorobertomartins.tsar.repository.factory.RepositoryFactory;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class UserService implements Service<User, Long> {

    private final UserRepository repository;

    public UserService() {
        this.repository = (UserRepository) RepositoryFactory.getRepository(User.class);
    }

    @Override
    public void create(User entity) {
        repository.create(entity);
    }

    @Override
    public void update(User entity) {
        repository.update(entity);

    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(User entity) {
        return repository.exists(entity);

    }

    @Override
    public User findById(Long id) {
        return repository.findById(id);

    }

    @Override
    public List<User> listAll() {
        return repository.listAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
