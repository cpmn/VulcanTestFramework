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

import com.vulcan.framework.shared.context.ScenarioContext;
import com.vulcan.framework.shared.context.DataRegistry;
import com.vulcan.framework.shared.context.ScenarioKeys;
import com.vulcan.framework.api.client.HealthApiClient;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Steps that validate basic API connectivity and demonstrate:
 * - ScenarioContext usage (store last API response)
 * - DataRegistry usage (register cleanup actions per scenario)
 *
 * This is our foundation before adding real CRUD.
 */
public class HealthSteps {
    
    private static final Logger logger = LogManager.getLogger(HealthSteps.class);
    private final HealthApiClient healthApiClient = new HealthApiClient();

    @When("I call the API health endpoint")
    public void i_call_the_api_health_endpoint() {
        
        // Call GET / and store the response for later assertions
        logger.info("Calling the API Health endpoint");
        Response response = healthApiClient.getRoot();
        ScenarioContext.put(ScenarioKeys.LAST_API_RESPONSE, response);

        //Get or create the DataRegistry for this scenario
        DataRegistry registry = ScenarioContext.getOrCreate(
            ScenarioKeys.DATA_REGISTRY, 
            DataRegistry.class, 
            DataRegistry::new
        );

        // Register a placeholder cleanup action for this scenario
        registry.registerCleanup(() -> {
            logger.info("Executing cleanup action for API Health scenario");
        });
        
        logger.info("Health response stored in ScenarioContext under key={}", ScenarioKeys.LAST_API_RESPONSE);
    }
    @Then("the API response body should contain {string}")
    public void the_api_response_body_should_contain(String expectedText) {
        Response response = ScenarioContext.get(ScenarioKeys.LAST_API_RESPONSE, Response.class);
        String body = response.getBody().asString();

        assertTrue(
            "Expected response body to contain: " + expectedText,
            body.contains(expectedText)
        );
    }    
}