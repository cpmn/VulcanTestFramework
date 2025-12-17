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

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;

/**
 * Central registry of ScenarioContext keys.
 *
 * Why:
 * - Avoid string typos
 * - Make keys discoverable
 * - Reuse across UI + API + hybrid steps
 */
public final class ScenarioKeys {
   private ScenarioKeys() {
      // Prevent instantiation
      /* utility class */
   }
    public static final String CREDENTIALS = "credentials";
    public static final String AUTH_TOKEN = "authToken";
    
    //API Request/Response storage
    public static final String LAST_API_RESPONSE = "lastApiResponse";
    
    //Data lifecycle management / seed entities
    public static final String CREATED_USER_ID = "createdUserId";
}
