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
 * Logical roles used by the application.
 * Independent from how authentication is performed.
 */
public enum UserRole {
    STANDARD,
    ADMINISTRATOR,
    LOCKED,
    PROBLEM,
    PERFORMANCE;

    public static UserRole from(String roleName){
        return UserRole.valueOf(roleName.trim().toUpperCase());
    }    
}
