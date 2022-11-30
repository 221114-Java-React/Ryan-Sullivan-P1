package org.example.ers.data_transfer_objects.responses;

import org.example.ers.utilities.enums.UserRole;

public class Principal {
    private String id;
    private String username;
    private UserRole role;

    public Principal(String id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public UserRole getRole() {
        return this.role;
    }
}
