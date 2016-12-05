package br.com.storeautomacao.tsar.util;

import br.com.storeautomacao.tsar.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo.martins
 */
public class MemoryDatabase {

    private List<User> users;
    
    public MemoryDatabase() {
        this.users = new ArrayList<User>();
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
