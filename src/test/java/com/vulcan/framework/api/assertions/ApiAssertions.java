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

package com.vulcan.framework.api.assertions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.restassured.response.Response;


public class ApiAssertions {
    public static void assertStatusCode(Response response, int expectedStatus) {
        assertNotNull("Response should not be null", response);
        assertEquals("Unexpected status code", expectedStatus, response.getStatusCode());
    }
    
    public static void assertJsonIntEquals(Response response, String jsonPath, int expectedValue) {
        int actual = response.jsonPath().getInt(jsonPath);
        assertEquals("Unexpected value at jsonPath: " + jsonPath, expectedValue, actual);
    }
}
