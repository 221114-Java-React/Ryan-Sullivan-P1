package com.revature.ers.models;

import io.javalin.core.security.RouteRole;

public enum RoleEnum implements RouteRole {
    ADMIN, MANAGER, EMPLOYEE
}
