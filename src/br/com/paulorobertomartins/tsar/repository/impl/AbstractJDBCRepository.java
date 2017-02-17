package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.util.ConnectionManager;

/**
 *
 * @author paulo.martins
 */
public class AbstractJDBCRepository {
    
    protected final ConnectionManager connectionManager;

    public AbstractJDBCRepository() {
        this.connectionManager = ConnectionManager.getInstance();
    }
    
    
    
}
