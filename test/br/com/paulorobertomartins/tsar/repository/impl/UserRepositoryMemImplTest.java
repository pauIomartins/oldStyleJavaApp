package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.repository.UserRepositoryBase;
import br.com.paulorobertomartins.tsar.util.MemoryDatabase;

/**
 *
 * @author paulo.martins
 */
public class UserRepositoryMemImplTest extends UserRepositoryBase {
    
    private final MemoryDatabase database;

    public UserRepositoryMemImplTest() {
        super();
        this.database = new MemoryDatabase();
        this.userRepository = new UserRepositoryMemImpl(this.database);
    }
}
