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

package com.vulcan.framework.steps;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.vulcan.framework.support.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class LoginSteps {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);
    private final WebDriver driver;

    public LoginSteps() {
        // Get the driver instance from DriverFactory
        this.driver = DriverFactory.getDriver();
    }
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        // The @before hook in DriverFactory already initializes the driver and navigate to the baseURL
        logger.info("Verifying that we are on the login page");
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);       
    }
    @Then("I should see the login form")
    public void i_should_see_the_login_form() {
        logger.info("Checking that the login form is displayed");
        // For saucedemo.com:
        // Username input: id='user-name'
        // Password input: id='password'
        // Login button: id='login-button'
        boolean usernameVisible = driver.findElement(By.id("user-name")).isDisplayed();
        boolean passwordVisible = driver.findElement(By.id("password")).isDisplayed();
        boolean loginButtonVisible = driver.findElement(By.id("login-button")).isDisplayed();

        logger.info("Username field visible: {}", usernameVisible);
        logger.info("Password field visible: {}", passwordVisible);
        logger.info("Login button visible: {}", loginButtonVisible);
        
        assertTrue("Expected User-Name field to be visible", usernameVisible);
        assertTrue("Expected password field to be visible", passwordVisible);
        assertTrue("Expected Login Button field to be visible", loginButtonVisible);
    }
    
}
