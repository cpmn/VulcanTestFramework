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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserApiClient extends BaseApiClient {

    private static final Logger logger = LogManager.getLogger(UserApiClient.class);

    private static final String USERS_ENDPOINT = "/users/";

    public Response getUserById(String userId) { 
        String path = USERS_ENDPOINT + userId; 
        logger.info("Requesting user by id={} in path={}", userId, path); return get(path); 
    }
    
}
