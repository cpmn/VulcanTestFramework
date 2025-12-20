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

package com.vulcan.framework.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage represents the SauceDemo login screen.
 *
 * Rules:
 * - Page methods are low-level actions on the page (enter/click/check).
 * - High-level flows belong in LoginActions (e.g., login as role/user).
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage() {
        super();
        logger.info("LoginPage initialized");
    }

    public void enterUsername(String username) {
        type(usernameField, "usernameField", username);
    }

    public void enterPassword(String password) {
        // Never log raw passwords
        typeSensitive(passwordField, "passwordField", password);
    }

    public void clickLogin() {
        click(loginButton, "loginButton");
    }

    public boolean isLoginFormVisible() {
        return isDisplayed(usernameField, "usernameField")
                && isDisplayed(passwordField, "passwordField")
                && isDisplayed(loginButton, "loginButton");
    }

    /**
     * Low-level convenience method.
     * (The recommended place for high-level flows is LoginActions.)
     */
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}