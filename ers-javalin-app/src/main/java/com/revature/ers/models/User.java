package com.revature.ers.models;

public class User {
    private String userId;
    private String username;
    private String email;
    private String passwordHash;
    private String givenName;
    private String surname;
    private boolean isActive;
    private String roleId;

    public User(String id, String username, String email, String password, String givenName, String surname) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.givenName = givenName;
        this.surname = surname;
    }

    public User(String userId,
                String username,
                String email,
                String password,
                String givenName,
                String surname,
                boolean isActive,
                String roleId)
    {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.givenName = givenName;
        this.surname = surname;
        this.isActive = isActive;
        this.roleId = roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRoleId() {
        return roleId;
    }

}
