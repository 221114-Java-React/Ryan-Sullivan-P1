package com.revature.ers.models;

import io.javalin.core.security.RouteRole;

public class UserRole {
    String roleId;
    String apiName;
    String labelName;

    public UserRole(String roleId, String apiName, String labelName) {
        this.roleId = roleId;
        this.apiName = apiName;
        this.labelName = labelName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
