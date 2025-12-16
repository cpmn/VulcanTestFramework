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

import com.vulcan.framework.ui.actions.LoginActions;
import com.vulcan.framework.ui.assertions.UiAssertions;
import com.vulcan.framework.ui.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class LoginSteps {    
    private final LoginPage loginPage = new LoginPage();
    private final LoginActions loginActions = new LoginActions();
        
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        // Keep this minimal. Hooks already navigates to ui.baseUrl.
        // Optionally later: loginPage.open();
    }
    @Then("I should see the login form")
    public void i_should_see_the_login_form() {
        UiAssertions.assertLoginFromVisible(loginPage);
    }
    
}
