package br.com.storeautomacao.tsar.repository.impl;

import br.com.storeautomacao.tsar.repository.UserRepositoryTest;

/**
 *
 * @author paulo.martins
 */
public class UserRepositoryJDBCImplTest extends UserRepositoryTest {
    
    public UserRepositoryJDBCImplTest() {
        super();
        this.userRepository = new UserRepositoryJDBCImpl();
    }
}
