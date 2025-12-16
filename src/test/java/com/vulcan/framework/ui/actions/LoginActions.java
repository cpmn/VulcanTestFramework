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

package com.vulcan.framework.ui.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.vulcan.framework.ui.pages.LoginPage;

public class LoginActions {
    
    private static final Logger logger = LogManager.getLogger(LoginActions.class);
    private final LoginPage loginPage;

    public LoginActions(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    /**
     * High-level login flow.
     * Keeps step definitions thin and reusable.
     */
    public void login(String username, String password) {
        logger.info("Loggin in with username: {}", username);
        loginPage.loginAs(username, password);
        logger.info("Login flow finished | username={}", username);      
    }


    
}
