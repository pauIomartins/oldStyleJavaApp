package br.com.paulorobertomartins.tsar.service;

import br.com.paulorobertomartins.tsar.model.Profile;
import br.com.paulorobertomartins.tsar.repository.ProfileRepository;
import br.com.paulorobertomartins.tsar.repository.factory.RepositoryFactory;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class ProfileService implements Service<Profile, Long> {

    private final ProfileRepository repository;

    public ProfileService() {
        this.repository = (ProfileRepository) RepositoryFactory.getRepository(Profile.class);
    }

    @Override
    public void create(Profile entity) {
        repository.create(entity);
    }

    @Override
    public void update(Profile entity) {
        repository.update(entity);
    }

    @Override
    public void delete(Profile entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Profile entity) {
        return repository.exists(entity);

    }

    @Override
    public Profile findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Profile> listAll() {
        return repository.listAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
