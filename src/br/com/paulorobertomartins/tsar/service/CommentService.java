package br.com.paulorobertomartins.tsar.service;

import br.com.paulorobertomartins.tsar.model.Comment;
import br.com.paulorobertomartins.tsar.repository.CommentRepository;
import br.com.paulorobertomartins.tsar.repository.factory.RepositoryFactory;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class CommentService implements Service<Comment, Long> {

    private final CommentRepository repository;

    public CommentService() {
        this.repository = (CommentRepository) RepositoryFactory.getRepository(Comment.class);
    }

    @Override
    public void create(Comment entity) {
        repository.create(entity);
    }

    @Override
    public void update(Comment entity) {
        repository.update(entity);
    }

    @Override
    public void delete(Comment entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Comment entity) {
        return repository.exists(entity);

    }

    @Override
    public Comment findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Comment> listAll() {
        return repository.listAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
