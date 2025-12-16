/*
 * Copyright (c) 2025 cpmn.tech
 *
 * Licensed under the MIT License.
 * You may obtain a copy of the License at
 * https://opensource.org/licenses/MIT
 *
 * This file is part of the VulcanTestFramework project.
 * A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
 */

package com.vulcan.framework.shared.auth;

/**
 * Centralized credentials registry for test users.
 *
 * Goals:
 * - Avoid exposing passwords in feature files
 * - Share auth data between UI and API tests
 * - Keep step definitions expressive and safe
 *
 * NOTE:
 * For real systems, secrets should come from env variables or vaults.
 */
public enum Credentials {
    STANDARD_USER(
        UserRole.STANDARD,
        "standard_user",
        "secret_sauce"
    ),

    ADMINISTRATOR(
        UserRole.ADMINISTRATOR,
        "admin_user",
        "secret_sauce"
    ),

    LOCKED_USER(
        UserRole.LOCKED,
        "locked_out_user",
        "secret_sauce"
    ),

    PROBLEM_USER(
        UserRole.PROBLEM,
        "problem_user",
        "secret_sauce"
    ),

    PERFORMANCE_USER(
        UserRole.PERFORMANCE,
        "performance_glitch_user",
        "secret_sauce"
    );

    private final UserRole role;
    private final String username;
    private final String password;

    Credentials(UserRole role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
    public UserRole role() {
        return role;
    }
    public String username() {
        return username;
    }
    public String password() {
        return password;
    }
    public static Credentials byRole(String roleName) {
        UserRole role = UserRole.from(roleName);
        for (Credentials cred : Credentials.values()) {
            if (cred.role() == role) {
                return cred;
            }
        }
        throw new IllegalArgumentException("No credentials found for role: " + roleName);
    }
}