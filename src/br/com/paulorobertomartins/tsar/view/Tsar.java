package br.com.paulorobertomartins.tsar.view;

import br.com.paulorobertomartins.tsar.util.MemoryDatabase;
import br.com.paulorobertomartins.tsar.model.User;
import br.com.paulorobertomartins.tsar.repository.Repository;
import br.com.paulorobertomartins.tsar.repository.impl.UserRepositoryJDBCImpl;
import br.com.paulorobertomartins.tsar.repository.impl.UserRepositoryMemImpl;
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
        User u = new User(1L, email);
        rep.create(u);
        System.out.println("User: " + rep.findById(1L));

        // Create user
        u = new User(2L, "odezio@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(2L));

        // Create user
        u = new User(3L, "tiago@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(3L));

        // Create user
        u = new User(4L, "ricardo@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(4L));

        // Create user
        u = new User(5L, "marcelo@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(5L));

        // Create user
        u = new User(6L, "andre@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(6L));

        // Create user
        u = new User(7L, "luiz@email.com");
        rep.create(u);
        System.out.println("User: " + rep.findById(7L));

        // Updating user
        System.out.println("=================");
        System.out.println("Enter user email:");
        email = in.next();
        u = new User(1L, email);
        rep.update(u);
        System.out.println("User: " + rep.findById(1L));

        // Deleting user
        System.out.println("=================");
        rep.delete(u);
        System.out.println("User: " + rep.findById(1L));

        // Listing all users
        System.out.println("=================");
        System.out.println(rep.listAll());

        // Listing all users
        System.out.println("========BANCO=========");
        System.out.println(repDB.listAll());

    }

}
