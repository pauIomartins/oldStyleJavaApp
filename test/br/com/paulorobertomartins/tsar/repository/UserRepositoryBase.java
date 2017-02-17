package br.com.paulorobertomartins.tsar.repository;

import br.com.paulorobertomartins.tsar.model.User;
import java.sql.SQLException;

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
    public void setUp() throws SQLException {
        User u = new User(1L, "paulo@email.com");
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(1L) );
        
        // Create user
        u = new User(2L, "odezio@email.com");
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(2L) );

        // Create user
        u = new User(3L, "tiago@email.com");
        userRepository.create(u);
        System.out.println("User: " +  userRepository.findById(3L) );
    }
    
    @After
    public void tearDown() throws SQLException {    
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
    public void testCreate() throws SQLException {
        System.out.println("create");
        User entity = new User(100L, "paulo.martins@email.com");
        userRepository.create(entity);
        
        User newUser = userRepository.findById(100L);
        assertEquals(entity, newUser);
        
        userRepository.deleteById(100L);
    }

    /**
     * Test of update method, of class UserRepositoryMemImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate() throws SQLException {
        System.out.println("update");
        User entity = userRepository.findById(1L);
        entity.setEmail("alterado@email.com");
        userRepository.update(entity);

        User updatedUser = userRepository.findById(1L);
        assertEquals(entity.getEmail(), updatedUser.getEmail());
    }

    /**
     * Test of delete method, of class UserRepositoryMemImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void testDelete() throws SQLException {
        System.out.println("delete");
        
        User entity = userRepository.findById(1L);
        userRepository.delete(entity);

        User deletedUser = userRepository.findById(1L);
        assertEquals(deletedUser, null);
    }

    /**
     * Test of deleteById method, of class UserRepositoryMemImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void testDeleteById() throws SQLException {
        System.out.println("deleteById");
        
        userRepository.deleteById(1L);

        User deletedUser = userRepository.findById(1L);
        assertEquals(deletedUser, null);
    }

    /**
     * Test of exists method, of class UserRepositoryMemImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void testExists() throws SQLException {
        System.out.println("exists");
        
        User entity = userRepository.findById(1L);
        assertEquals(true, userRepository.exists(entity));
        
        User user = new User(999L, "nao@existe.com");
        assertEquals(false, userRepository.exists(user));
    }

    /**
     * Test of findById method, of class UserRepositoryMemImpl.
     */
    @Test
    public void testFindById() throws SQLException {
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
     * @throws java.sql.SQLException
     */
    @Test
    public void testListAll() throws SQLException {
        System.out.println("listAll");
        List<User> result = userRepository.listAll();
        assertEquals(3, result.size());
    }

    /**
     * Test of count method, of class UserRepositoryMemImpl.
     * @throws java.sql.SQLException
     */
    @Test
    public void testCount() throws SQLException {
        System.out.println("count");
        assertEquals(3, userRepository.count());
    }
    
}
