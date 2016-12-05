package br.com.storeautomacao.tsar.repository.impl;

import br.com.storeautomacao.tsar.model.User;
import br.com.storeautomacao.tsar.repository.UserRepository;
import br.com.storeautomacao.tsar.util.MemoryDatabase;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class UserRepositoryMemImpl implements UserRepository {

    private final MemoryDatabase database;

    public UserRepositoryMemImpl(MemoryDatabase bd) {
        this.database = bd;
    }

    @Override
    public void create(User entity) {
        database.getUsers().add(entity);
    }

    @Override
    public void update(User entity) {
        for (int i = 0; i < database.getUsers().size(); i++) {
            if (database.getUsers().get(i).equals(entity)) {
                database.getUsers().set(i, entity);
            }
        }
    }

    @Override
    public void delete(User entity) {
        for (int i = 0; i < database.getUsers().size(); i++) {
            if (database.getUsers().get(i).equals(entity)) {
                database.getUsers().remove(i);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        this.delete(this.findById(id));
    }

    @Override
    public boolean exists(User entity) {
        return this.findById(entity.getUserId()) != null;
    }
    
    @Override
    public User findById(Long entityId) {        
        for (int i = 0; i < database.getUsers().size(); i++) {
            if (database.getUsers().get(i).getUserId().equals(entityId)) {
                return database.getUsers().get(i);
            }
        }        
        return null;
    }

    @Override
    public List<User> listAll() {
        return database.getUsers();
    }

    @Override
    public long count() {
        return database.getUsers().size();
    }
}
