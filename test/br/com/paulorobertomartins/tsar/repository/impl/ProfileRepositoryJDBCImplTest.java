package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Profile;
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
public class ProfileRepositoryJDBCImplTest {
    
    public ProfileRepositoryJDBCImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Profile entity = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        instance.create(entity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Profile entity = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        instance.update(entity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Profile entity = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        instance.delete(entity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testDeleteById() {
        System.out.println("deleteById");
        Long id = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        instance.deleteById(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testExists() {
        System.out.println("exists");
        Profile entity = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        boolean expResult = false;
        boolean result = instance.exists(entity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        Long id = null;
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        Profile expResult = null;
        Profile result = instance.findById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAll method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testListAll() {
        System.out.println("listAll");
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        List<Profile> expResult = null;
        List<Profile> result = instance.listAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class ProfileRepositoryJDBCImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        ProfileRepositoryJDBCImpl instance = new ProfileRepositoryJDBCImpl();
        long expResult = 0L;
        long result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
