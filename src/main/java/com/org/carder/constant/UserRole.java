package com.org.carder.constant;

public enum UserRole {
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER");

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

}
