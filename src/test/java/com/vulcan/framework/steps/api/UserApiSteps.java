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

import com.vulcan.framework.api.assertions.ApiAssertions;
import com.vulcan.framework.api.client.UserApiClient;
import com.vulcan.framework.shared.context.ScenarioContext;
import com.vulcan.framework.shared.context.ScenarioKeys;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for User API scenarios.
 *
 * Design notes:
 * - Stores the latest Response in ScenarioContext to make steps independent of instance fields.
 * - Safer for parallel execution and cross-step reuse (any step can read LAST_API_RESPONSE).
 */
public class UserApiSteps {

    private static final Logger logger = LogManager.getLogger(UserApiSteps.class);
    private final UserApiClient userApiClient = new UserApiClient();

    @When("I request the user with id {string}")
    public void i_request_the_user_with_id(String userId) {
        logger.info("Calling API: get user by id={}", userId);

        Response response = userApiClient.getUserById(userId);

        // Store for later steps (status, field assertions, etc.)
        ScenarioContext.put(ScenarioKeys.LAST_API_RESPONSE, response);

        logger.info("API response stored in ScenarioContext | key={} | status={}",
                ScenarioKeys.LAST_API_RESPONSE,
                response == null ? "null" : response.getStatusCode());
    }

    @Then("the API response status should be {int}")
    public void the_api_response_status_should_be(int expectedStatus) {
        Response response = ScenarioContext.get(ScenarioKeys.LAST_API_RESPONSE, Response.class);
        ApiAssertions.assertStatusCode(response, expectedStatus);
    }

    @Then("the API response field {string} should be {int}")
    public void the_api_response_field_should_be(String jsonPath, int expectedValue) {
        Response response = ScenarioContext.get(ScenarioKeys.LAST_API_RESPONSE, Response.class);
        ApiAssertions.assertJsonIntEquals(response, jsonPath, expectedValue);
    }
}
