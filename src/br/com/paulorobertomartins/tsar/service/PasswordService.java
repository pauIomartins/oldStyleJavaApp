package br.com.paulorobertomartins.tsar.service;

import br.com.paulorobertomartins.tsar.model.Password;
import br.com.paulorobertomartins.tsar.repository.PasswordRepository;
import br.com.paulorobertomartins.tsar.repository.factory.RepositoryFactory;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class PasswordService implements Service<Password, Long> {

    private final PasswordRepository repository;

    public PasswordService() {
        this.repository = (PasswordRepository) RepositoryFactory.getRepository(Password.class);
    }

    @Override
    public void create(Password entity) {
        repository.create(entity);
    }

    @Override
    public void update(Password entity) {
        repository.update(entity);

    }

    @Override
    public void delete(Password entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Password entity) {
        return repository.exists(entity);

    }

    @Override
    public Password findById(Long id) {
        return repository.findById(id);

    }

    @Override
    public List<Password> listAll() {
        return repository.listAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
