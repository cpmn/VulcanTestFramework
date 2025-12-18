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
 * BaseApiClient provides:
 * - RestAssured global configuration (base URI + timeouts)
 * - Shared request builders (JSON by default, optional HTML)
 * - Convenience HTTP methods (GET)
 *
 * Note:
 * - JSON headers are the default because most APIs return JSON.
 * - For HTML endpoints (ex: SauceDemo home page), use requestHtml().
 */
public abstract class BaseApiClient {

    /**
     * Use the runtime class (getClass()) so logs show the concrete client class.
     * This is more informative than BaseApiClient.class.
     */
    protected final Logger logger = LogManager.getLogger(getClass());

    protected final String baseUrl;
    protected final int timeoutMs;

    protected BaseApiClient() {
        this.baseUrl = ConfigManager.getInstance().get("api.baseUrl");
        this.timeoutMs = Integer.parseInt(ConfigManager.getInstance().get("api.timeout"));

        logger.info("Initializing API client | baseUrl={} | timeoutMs={}", baseUrl, timeoutMs);

        // Configure RestAssured globally for this JVM run.
        RestAssured.baseURI = baseUrl;
        RestAssured.config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeoutMs)
                .setParam("http.socket.timeout", timeoutMs)
                .setParam("http.connection-manager.timeout", (long) timeoutMs));
    }

    /**
     * Default request for JSON APIs.
     *
     * Why:
     * - Most services are JSON-based
     * - Keeps your existing UserApiClient behavior unchanged
     */
    protected RequestSpecification requestJson() {
        return RestAssured.given()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json");
    }

    /**
     * Request builder for HTML endpoints (ex: SauceDemo landing page).
     *
     * Why:
     * - Some endpoints return HTML (not JSON)
     * - Allows health-check scenarios without fighting headers
     *
     * Note:
     * - No Content-Type is needed for GET.
     */
    protected RequestSpecification requestHtml() {
        return RestAssured.given()
            .header("Accept", "text/html");
    }

    /**
     * Default GET uses JSON request settings.
     * This keeps your existing API tests stable.
     */
    protected Response get(String path) {
        logger.info("GET (JSON) Request to endpoint: {}", path);
        return requestJson().when().get(path).thenReturn();
    }

    /**
     * GET for HTML endpoints.
     * Use this for SauceDemo home page checks.
     */
    protected Response getHtml(String path) {
        logger.info("GET (HTML) Request to endpoint: {}", path);
        return requestHtml().when().get(path).thenReturn();
    }
}
