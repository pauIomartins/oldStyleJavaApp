package br.com.storeautomacao.tsar.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author paulo.martins
 */
public class Password {
    
    private Long passwordId;
    private String password;
    private Boolean active;
    private Date createdAt;

    public Password() {
    }

    public Long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.passwordId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Password other = (Password) obj;
        if (!Objects.equals(this.passwordId, other.passwordId)) {
            return false;
        }
        return true;
    }
    
    
    
}
