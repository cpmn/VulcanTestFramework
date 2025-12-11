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

package com.vulcan.framework.steps.ui;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.vulcan.framework.ui.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class LoginSteps {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);
    private final LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }
        
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        // The @before hook in DriverFactory already initializes the driver and navigate to the baseURL
        logger.info("Verifying that we are on the login page");
        String title = loginPage.getPageTitle();
        logger.info("Current page title: {}", title);     
    }
    @Then("I should see the login form")
    public void i_should_see_the_login_form() {
        logger.info("Checking that the login form is displayed");
        boolean visible = loginPage.isLoginFormDisplayed();
        assertTrue("Expected login form to be visible", visible);
    }
    
}
