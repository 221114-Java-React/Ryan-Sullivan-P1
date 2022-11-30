package org.example.ers.models;

import io.javalin.core.security.RouteRole;

public enum UserRole implements RouteRole {
    EMPLOYEE, MANAGER, ADMIN;
}
