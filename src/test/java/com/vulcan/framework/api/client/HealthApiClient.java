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

package com.vulcan.framework.api.client;

import io.restassured.response.Response;

/**
 * HealthApiClient provides minimal endpoints used only to validate connectivity
 * and framework wiring (base URL, timeouts, logging, scenario context, cleanup).
 *
 * This is intentionally simple: it is not "CRUD", it is a smoke/health call.
 */
public class HealthApiClient extends BaseApiClient {
    
   /**
     * Calls GET / on the configured api.baseUrl.
     * For SauceDemo this returns HTML (not JSON), which is fine for a health check.
     */
    public Response getRoot() {
        logger.info("Calling GET / for health check");
        return getHtml("/");
    }
    
}
