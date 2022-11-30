package org.example.ers.utilities.enums;

import io.javalin.core.security.RouteRole;

public enum UserRole implements RouteRole {
    EMPLOYEE, MANAGER, ADMIN;
}
