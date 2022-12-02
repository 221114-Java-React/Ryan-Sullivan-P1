package org.example.ers.models;

import io.javalin.core.security.RouteRole;

public enum RoleEnum implements RouteRole {
    ADMIN, MANAGER, EMPLOYEE
}
