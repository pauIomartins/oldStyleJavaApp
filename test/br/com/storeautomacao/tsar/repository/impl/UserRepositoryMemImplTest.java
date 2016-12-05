package br.com.storeautomacao.tsar.repository.impl;

import br.com.storeautomacao.tsar.repository.UserRepositoryTest;
import br.com.storeautomacao.tsar.util.MemoryDatabase;

/**
 *
 * @author paulo.martins
 */
public class UserRepositoryMemImplTest extends UserRepositoryTest {
    
    private final MemoryDatabase database;

    public UserRepositoryMemImplTest() {
        super();
        this.database = new MemoryDatabase();
        this.userRepository = new UserRepositoryMemImpl(this.database);
    }
}
