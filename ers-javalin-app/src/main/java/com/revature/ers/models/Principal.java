package com.revature.ers.models;

public class Principal {
    private String id;
    private RoleEnum role;

    public Principal(String id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public RoleEnum getRole() {
        return this.role;
    }
}
