package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Profile;
import br.com.paulorobertomartins.tsar.model.User;
import br.com.paulorobertomartins.tsar.repository.ProfileRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
import java.sql.SQLException;
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
public class ProfileRepositoryJDBCImplTest {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileRepositoryJDBCImplTest() {
        this.userRepository = new UserRepositoryJDBCImpl();
        this.profileRepository = new ProfileRepositoryJDBCImpl();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {

        User u = userRepository.findById(500L);
        if (u == null) {
            u = new User(500L, "paulo@email.com");
            userRepository.create(u);
        }

        if (profileRepository.findById(500L) == null) {
            Profile p = new Profile(u, "Paulo", "Roberto", "Martins", "Developer", "Store Automação");
            profileRepository.create(p);
        }
    }

    @After
    public void tearDown() throws SQLException {
        profileRepository.deleteById(500L);
        userRepository.deleteById(500L);        
    }

    /**
     * Test of create method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testCreate() throws SQLException {
        System.out.println("create");

        User u = new User(150L, "paulo.outro@email.com");
        userRepository.create(u);
        System.out.println("User: " + userRepository.findById(150L));

        Profile p = new Profile(u, "Paulo", "Roberto", "Martins", "Developer", "Store Automação");
        profileRepository.create(p);

        Profile other = profileRepository.findById(150L);

        assertEquals(p, other);
        
        profileRepository.deleteById(150L);
        userRepository.deleteById(150L);             
    }

    /**
     * Test of update method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate() throws SQLException {
        System.out.println("update");

        Profile p = profileRepository.findById(500L);

        String firstName = "Paulo Alterado";
        String middleName = "Roberto Alterado";
        String lastName = "Martins Alterado";
        String position = "Posição Alterada";
        String company = "Empresa Alterada";

        p.setFirstName(firstName);
        p.setMiddleName(middleName);
        p.setLastName(lastName);
        p.setPosition(position);
        p.setCompany(company);

        profileRepository.update(p);

        Profile other = profileRepository.findById(500L);

        assertEquals(other.getFirstName(), firstName);
        assertEquals(other.getMiddleName(), middleName);
        assertEquals(other.getLastName(), lastName);
        assertEquals(other.getPosition(), position);
        assertEquals(other.getCompany(), company);
    }

    /**
     * Test of delete method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testDelete() throws SQLException {
        System.out.println("delete");

        User u = new User(200L, "delete@email.com");
        userRepository.create(u);

        Profile p = new Profile(u, "Paulo", "Roberto", "Martins", "Developer", "Store Automação");
        profileRepository.create(p);

        profileRepository.delete(p);
        userRepository.delete(u);

        Profile other = profileRepository.findById(200L);

        assertEquals(other, null);
    }

    /**
     * Test of deleteById method, of class ProfileRepositoryJDBCImpl.
     * 
     */
    @Test
    public void testDeleteById() throws SQLException {
        System.out.println("deleteById");

        User u = new User(201L, "deletebyid@email.com");
        userRepository.create(u);

        Profile p = new Profile(u, "Paulo", "Roberto", "Martins", "Developer", "Store Automação");
        profileRepository.create(p);

        profileRepository.deleteById(201L);
        userRepository.deleteById(201L);

        Profile other = profileRepository.findById(201L);

        assertEquals(other, null);
    }

    /**
     * Test of exists method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testExists() throws SQLException {
        System.out.println("exists");

        Profile p = profileRepository.findById(500L);

        assertTrue(profileRepository.exists(p));
        p = new Profile(new User(1234L, "testeexists@teste.com"), "Paulo", "Roberto", "Martins", "Developer", "Store Automação");
        assertFalse(profileRepository.exists(p));
    }

    /**
     * Test of listAll method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testListAll() throws SQLException {
        System.out.println("listAll");

        assertEquals(profileRepository.listAll().size(), 1);
    }

    /**
     * Test of count method, of class ProfileRepositoryJDBCImpl.
     * 
     * @throws java.sql.SQLException
     */
    @Test
    public void testCount() throws SQLException {
        System.out.println("count");

        assertEquals(profileRepository.count(), 1);
    }

}
