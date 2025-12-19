package com.vulcan.framework.api.client;

import com.vulcan.framework.config.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseApiClient provides a scenario-friendly API client foundation.
 *
 * Key design decision:
 * - Avoids setting global RestAssured.baseURI / RestAssured.config (shared JVM state).
 * - Builds a per-instance (per-scenario) RequestSpecification instead.
 *
 * This is safer for:
 * - per-scenario lifecycle
 * - future parallel execution
 * - switching env/baseUrl without bleeding across tests
 */
public abstract class BaseApiClient {

    /**
     * Use the runtime class (getClass()) so logs show the concrete client class.
     * This is more informative than BaseApiClient.class.
     */
    protected final Logger logger = LogManager.getLogger(getClass());

    protected final String baseUrl;
    protected final int timeoutMs;

    /** Base request spec reused for all calls made by this client instance. */
    private final RequestSpecification baseRequest;

    protected BaseApiClient() {
        this.baseUrl = ConfigManager.getInstance().get("api.baseUrl");
        this.timeoutMs = Integer.parseInt(ConfigManager.getInstance().get("api.timeout"));

        logger.info("Initializing API client | baseUrl={} | timeoutMs={}", baseUrl, timeoutMs);

        // Configure RestAssured timeouts
        RestAssuredConfig config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeoutMs)
                .setParam("http.socket.timeout", timeoutMs)
                .setParam("http.connection-manager.timeout", (long) timeoutMs));

        // Build a per-instance base request specification.
        // This avoids mutating RestAssured static globals.
        this.baseRequest = RestAssured.given()
            .baseUri(baseUrl)
            .config(config);
    }

   /**
     * JSON request builder.
     * Use for typical REST APIs that return JSON.
     */
    protected RequestSpecification requestJson() {
        return baseRequest
            .header("Accept", "application/json")
            .header("Content-Type", "application/json");
    }

    /**
     * HTML request builder.
     * Useful for calling UI sites (like SauceDemo) as a basic health check.
     */
    protected RequestSpecification requestHtml() {
        return baseRequest
            .header("Accept", "text/html");
    }

    /** Default GET for JSON APIs. */
    protected Response get(String path) {
        logger.info("GET (JSON) Request to endpoint: {}", path);
        return requestJson().when().get(path).thenReturn();
    }

    /** GET for HTML endpoints (SauceDemo). */
    protected Response getHtml(String path) {
        logger.info("GET (HTML) Request to endpoint: {}", path);
        return requestHtml().when().get(path).thenReturn();
    }
    
}
