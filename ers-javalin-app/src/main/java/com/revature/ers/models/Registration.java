package com.revature.ers.models;

public class Registration {
    String requestId;
    String username;
    String email;
    String passwordHash;
    String givenName;
    String surname;
    String roleId;

    public Registration() {
    }

    public Registration(String requestId, String username, String email, String passwordHash, String givenName, String surname, String roleId) {
        this.requestId = requestId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.givenName = givenName;
        this.surname = surname;
        this.roleId = roleId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getSurname() {
        return surname;
    }

    public String getRoleId() {
        return roleId;
    }
}
