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


package com.vulcan.framework.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);

    private static ConfigManager instance;    

    private ConfigManager() {
        logger.info("ConfigManager initialized. Reading configuration from system properties (gradle.properties).");
    }
    public static ConfigManager getInstance() {
        if (instance == null) {
            logger.info("Creating ConfigManager instance");
            instance = new ConfigManager();
        }
        return instance;
    }
    public String get(String key) {
        String value = System.getProperty(key);

        if (value == null) {
            logger.error(
                "Configuration property '{}' not found in system properties. " +
                "Make sure it is defined in gradle.properties or passed via -P/-D.",
                key
            );
            throw new RuntimeException("Property '" + key + "' not found in system properties");
        }

        logger.debug("Reading config property '{}' = '{}'", key, value);
        return value.trim();
    }
}


