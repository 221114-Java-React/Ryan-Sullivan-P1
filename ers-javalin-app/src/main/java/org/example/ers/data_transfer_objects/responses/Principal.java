package org.example.ers.data_transfer_objects.responses;

import org.example.ers.models.UserRole;

import javax.management.relation.Role;

public class Principal {
    private String id;
    private String username;

    public Principal(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
