package com.revature.ers.data_transfer_objects.responses;

public class TokenResponse {
    private String authorization;

    public TokenResponse(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
