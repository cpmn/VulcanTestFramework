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

package com.vulcan.framework.shared.context;


/**
 * ScenarioKeys is the central registry of keys used by {@link ScenarioContext}.
 *
 * <p><b>Why this class exists:</b></p>
 * <ul>
 *   <li>Avoids fragile string literals scattered across the framework</li>
 *   <li>Prevents typos that cause runtime-only failures</li>
 *   <li>Makes scenario-shared data discoverable and self-documented</li>
 *   <li>Allows reuse across UI, API, and Hybrid scenarios</li>
 * </ul>
 *
 * <p>All keys defined here are intended to be:</p>
 * <ul>
 *   <li>Scenario-scoped (never global)</li>
 *   <li>Thread-safe via {@link ScenarioContext}</li>
 *   <li>Cleared automatically after each scenario</li>
 * </ul>
 */
public final class ScenarioKeys {
   private ScenarioKeys() {
      /// Utility class: prevent instantiation
   }
   /* ===============================
       Authentication / Identity
       =============================== */

    /**
     * Stores the active credentials used in the scenario.
     *
     * Type example:
     * <pre>{@code
     * Credentials
     * }</pre>
     *
     * Used by:
     * - UI login actions
     * - API authentication flows
     * - Hybrid UI + API scenarios
     */
    public static final String CREDENTIALS = "credentials";
    /**
     * Stores an authentication token retrieved via API login.
     *
     * Type example:
     * <pre>{@code
     * String
     * }</pre>
     *
     * Used by:
     * - API clients for authenticated requests
     * - Hybrid scenarios (API login → UI validation)
     */
    public static final String AUTH_TOKEN = "authToken";
    
    
    /* ===============================
       API State
       =============================== */

    /**
     * Stores the last API response executed in the scenario.
     *
     * Type example:
     * <pre>{@code
     * io.restassured.response.Response
     * }</pre>
     *
     * Used by:
     * - API assertions
     * - Chained API steps
     * - Debugging and reporting
     */
    public static final String LAST_API_RESPONSE = "lastApiResponse";
    
    /* ===============================
       Data Lifecycle / Seeding
       =============================== */

    /**
     * Stores the {@link DataRegistry} instance for the current scenario.
     *
     * Used by:
     * - API seeders
     * - UI seeders (if needed)
     * - Hooks teardown to clean created data automatically
     */
    public static final String DATA_REGISTRY = "dataRegistry";
    
    /**
     * Example of a seeded entity identifier.
     *
     * Type example:
     * <pre>{@code
     * String (userId)
     * }</pre>
     *
     * Used by:
     * - API validation steps
     * - Hybrid scenarios (API create → UI verify)
     * - Cleanup via {@link DataRegistry}
     *
     * Note:
     * This is an example key. Prefer registering cleanup actions
     * rather than relying solely on raw IDs.
     */
    public static final String CREATED_USER_ID = "createdUserId";

    // API client lifecycle (per scenario)
public static final String API_CLIENT_REGISTRY = "apiClientRegistry";
}
