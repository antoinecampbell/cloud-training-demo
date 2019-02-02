package com.antoinecampbell.cloud.user

enum class SecurityRoles(val role: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    override fun toString(): String {
        return role
    }
}
