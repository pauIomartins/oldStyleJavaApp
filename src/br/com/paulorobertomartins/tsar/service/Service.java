package br.com.paulorobertomartins.tsar.service;

import java.util.List;

/**
 *
 * @author paulo.martins
 * @param <T> Entity class
 * @param <ID> Entity Id Class
 */
public interface Service<T, ID> {

    public void create(T entity);
    public void update(T entity);
    public void delete(T entity);
    public void deleteById(ID id);
    public boolean exists(T entity);
    public T findById(ID id);
    public List<T> listAll();
    public long count();
}
