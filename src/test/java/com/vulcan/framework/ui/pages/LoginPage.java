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
        logger.info("Entering username: {}", username);
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        type(passwordField, password);
    }

    public void clickLogin() {
        logger.info("Clicking login button");
        click(loginButton);
    }

    public boolean isLoginFormDisplayed() {
        logger.info("Checking if login form is visible");
        return isDisplayed(usernameField) && isDisplayed(passwordField) && isDisplayed(loginButton);
    }

    public void loginAs(String username, String password) {
        logger.info("Performing login as user: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    
}
