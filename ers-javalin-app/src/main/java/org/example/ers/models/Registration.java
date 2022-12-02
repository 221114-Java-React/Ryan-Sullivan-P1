package org.example.ers.models;

public class Registration {
    String requestId;
    String username;
    String email;
    String passwordHash;
    String givenName;
    String surname;
    String roleId;

    public Registration(String requestId, String username, String email, String passwordHash, String givenName, String surname, String roleId) {
        this.requestId = requestId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.givenName = givenName;
        this.surname = surname;
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
