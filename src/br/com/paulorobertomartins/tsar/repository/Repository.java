package br.com.paulorobertomartins.tsar.repository;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author paulo.martins
 * @param <T> Entity class
 * @param <ID> Entity Id Class
 */
public interface Repository<T, ID> {

    public void create(T entity) throws SQLException;
    public void update(T entity) throws SQLException;
    public void delete(T entity) throws SQLException;
    public void deleteById(ID id) throws SQLException;
    public boolean exists(T entity) throws SQLException;
    public T findById(ID id) throws SQLException;
    public List<T> listAll() throws SQLException;
    public long count() throws SQLException;
}
