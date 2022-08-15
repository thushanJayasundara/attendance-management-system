package com.org.carder.constant;

public enum UserRole {
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    STAFF("STAFF");

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

}
