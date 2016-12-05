package br.com.storeautomacao.tsar.view;

import br.com.storeautomacao.tsar.util.MemoryDatabase;
import br.com.storeautomacao.tsar.model.User;
import br.com.storeautomacao.tsar.repository.Repository;
import br.com.storeautomacao.tsar.repository.impl.UserRepositoryJDBCImpl;
import br.com.storeautomacao.tsar.repository.impl.UserRepositoryMemImpl;
import java.util.Scanner;

/**
 *
 * @author paulo.martins
 */
public class Tsar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Scanner in = new Scanner(System.in);
        
        // Create a fake DB
        MemoryDatabase database = new MemoryDatabase();
        
        // Create repository to work with DB
        Repository rep = new UserRepositoryMemImpl(database);
        
        // Create repository to work with DB
        Repository repDB = new UserRepositoryJDBCImpl();        
        
        // Create first user
        System.out.println("Enter user email:");
        String email = in.next();
        User u = new User(email);
        u.setUserId(1L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(1L) );
        
        // Create user
        u = new User("odezio@email.com");
        u.setUserId(2L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(2L) );

        // Create user
        u = new User("tiago@email.com");
        u.setUserId(3L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(3L) );

        // Create user
        u = new User("ricardo@email.com");
        u.setUserId(4L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(4L) );        
        
        // Create user
        u = new User("marcelo@email.com");
        u.setUserId(5L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(5L));

        // Create user
        u = new User("andre@email.com");
        u.setUserId(6L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(6L));
        
        // Create user
        u = new User("luiz@email.com");
        u.setUserId(7L);
        rep.create(u);
        System.out.println("User: " +  rep.findById(7L));

        // Updating user
        System.out.println("=================");
        System.out.println("Enter user email:");
        email = in.next();
        u = new User(email);
        u.setUserId(1L);
        rep.update(u);
        System.out.println("User: " +  rep.findById(1L) );
        
        // Deleting user
        System.out.println("=================");
        rep.delete(u);
        System.out.println("User: " +  rep.findById(1L));
        
        
        // Listing all users
        System.out.println("=================");
        System.out.println(rep.listAll());
        
        
        // Listing all users
        System.out.println("========BANCO=========");
        System.out.println(repDB.listAll());
    }
    
}
