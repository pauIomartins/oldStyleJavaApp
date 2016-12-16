package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.repository.UserRepositoryBase;

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
