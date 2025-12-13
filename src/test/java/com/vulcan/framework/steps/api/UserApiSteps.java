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

package com.vulcan.framework.steps.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vulcan.framework.api.assertions.ApiAssertions;
import com.vulcan.framework.api.client.UserApiClient;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UserApiSteps {
    private static final Logger logger = LogManager.getLogger(UserApiSteps.class);
    private final UserApiClient userApiClient = new UserApiClient();
    private Response lastResponse;

    @When("I request the user with id {string}")
    public void i_request_the_user_with_id(String userId) {
        logger.info("Calling API: get user by id={}", userId);
        lastResponse = userApiClient.getUserById(userId);
        logger.info("API response status={}", lastResponse.getStatusCode());
    }

    @Then("the API response status should be {int}")
    public void the_api_response_status_should_be(int expectedStatus) {
        ApiAssertions.assertStatusCode(lastResponse, expectedStatus);
    }

    @Then("the API response field {string} should be {int}")
    public void the_api_response_field_should_be(String jsonPath, int expectedValue) {
        ApiAssertions.assertJsonIntEquals(lastResponse, jsonPath, expectedValue);
    }
    
}
