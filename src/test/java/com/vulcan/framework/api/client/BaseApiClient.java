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


import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vulcan.framework.config.ConfigManager;


public abstract class BaseApiClient {
 
    protected final Logger logger = LogManager.getLogger(BaseApiClient.class);
    
    protected final String baseUrl;
    protected final int timeoutMs;

    protected BaseApiClient() {
        this.baseUrl = ConfigManager.getInstance().get("api.baseUrl");
        this.timeoutMs = Integer.parseInt(ConfigManager.getInstance().get("api.timeout"));

        logger.info("Initializing API client | baseUrl={} | timeoutMs={}", baseUrl, timeoutMs);

        // Configure RestAssured globally
        RestAssured.baseURI = baseUrl;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeoutMs)
                .setParam("http.socket.timeout", timeoutMs)
                .setParam("http.connection-manager.timeout", (long) timeoutMs));
    }
    protected RequestSpecification request() {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
    protected Response get(String path) {
        logger.info("GET Request to endpoint: {}", path);
        return request()
                .when()
                .get(path)
                .thenReturn();                
    }    
}
