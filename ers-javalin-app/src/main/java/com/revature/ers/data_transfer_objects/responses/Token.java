package com.revature.ers.data_transfer_objects.responses;

public class Token {
    private String authorization;

    public Token(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
