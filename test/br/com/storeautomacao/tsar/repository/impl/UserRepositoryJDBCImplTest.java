package br.com.storeautomacao.tsar.repository.impl;

import br.com.storeautomacao.tsar.repository.UserRepositoryBase;

/**
 *
 * @author paulo.martins
 */
public class UserRepositoryJDBCImplTest extends UserRepositoryBase {
    
    public UserRepositoryJDBCImplTest() {
        super();
        this.userRepository = new UserRepositoryJDBCImpl();
    }
}
