package br.com.storeautomacao.tsar.repository;

import br.com.storeautomacao.tsar.model.User;

import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulo.martins
 */
public abstract class UserRepositoryBase {
    
    protected UserRepository userRepository;
    
    public UserRepositoryBase() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        User u = new User("paulo@email.com");
        u.setUserId(1L);
        u.setCreatedAt(new Date());
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(1L) );
        
        // Create user
        u = new User("odezio@email.com");
        u.setUserId(2L);
        u.setCreatedAt(new Date());
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(2L) );

        // Create user
        u = new User("tiago@email.com");
        u.setUserId(3L);
        u.setCreatedAt(new Date());
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(3L) );
    }
    
    @After
    public void tearDown() {    
        User user = userRepository.findById(1L);
        if (user != null) {
            userRepository.delete(user);
        }
        user = userRepository.findById(2L);
        if (user != null) {
            userRepository.delete(user);
        }        
        user = userRepository.findById(3L);
        if (user != null) {
            userRepository.delete(user);
        }         
    }

    /**
     * Test of create method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        User entity = new User("paulo.martins@email.com");
        entity.setUserId(100L);
        entity.setCreatedAt(new Date());
        userRepository.create(entity);
        
        User newUser = userRepository.findById(100L);
        assertEquals(entity, newUser);
        
        userRepository.deleteById(100L);
    }

    /**
     * Test of update method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        User entity = userRepository.findById(1L);
        entity.setEmail("alterado@email.com");
        userRepository.update(entity);

        User updatedUser = userRepository.findById(1L);
        assertEquals(entity.getEmail(), updatedUser.getEmail());
    }

    /**
     * Test of delete method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        User entity = userRepository.findById(1L);
        userRepository.delete(entity);

        User deletedUser = userRepository.findById(1L);
        assertEquals(deletedUser, null);
    }

    /**
     * Test of deleteById method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testDeleteById() {
        System.out.println("deleteById");
        
        userRepository.deleteById(1L);

        User deletedUser = userRepository.findById(1L);
        assertEquals(deletedUser, null);
    }

    /**
     * Test of exists method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testExists() {
        System.out.println("exists");
        
        User entity = userRepository.findById(1L);
        assertEquals(true, userRepository.exists(entity));
        
        User user = new User("nao@existe.com");
        user.setUserId(999L);
        assertEquals(false, userRepository.exists(user));
    }

    /**
     * Test of findById method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");

        Long id = 1L;
        User entity = userRepository.findById(1L);
        assertEquals(id, entity.getUserId());

        id = 999L;
        entity = userRepository.findById(id);
        assertEquals(null, entity);
    }

    /**
     * Test of listAll method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testListAll() {
        System.out.println("listAll");
        List<User> result = userRepository.listAll();
        assertEquals(3, result.size());
    }

    /**
     * Test of count method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        assertEquals(3, userRepository.count());
    }
    
}
