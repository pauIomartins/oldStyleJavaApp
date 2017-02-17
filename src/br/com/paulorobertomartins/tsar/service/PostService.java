package br.com.paulorobertomartins.tsar.service;

import br.com.paulorobertomartins.tsar.model.Post;
import br.com.paulorobertomartins.tsar.repository.PostRepository;
import br.com.paulorobertomartins.tsar.repository.factory.RepositoryFactory;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class PostService implements Service<Post, Long> {

    private final PostRepository repository;

    public PostService() {
        this.repository = (PostRepository) RepositoryFactory.getRepository(Post.class);
    }

    @Override
    public void create(Post entity) {
        repository.create(entity);
    }

    @Override
    public void update(Post entity) {
        repository.update(entity);

    }

    @Override
    public void delete(Post entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Post entity) {
        return repository.exists(entity);

    }

    @Override
    public Post findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> listAll() {
        return repository.listAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
