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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);

    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        loadProperties();
    }
    public static ConfigManager getInstance() {
        if (instance == null) {
            logger.info("Creating ConfigManager instance");
            instance = new ConfigManager();
        }
        return instance;
    }
    private void loadProperties() {
        logger.info("Loading configuration from config.properties"); 

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("config.properties file not found in resources directory");
                throw new RuntimeException("config.properties file not found in resources directory.");
            }
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException ex) {
            logger.error("Failed to load configuration file", ex);
            throw new RuntimeException("config.properties file not found in resources directory.");
        }
    }
    public String get(String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            logger.error("Property '{}' not found in config.properties", key);
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }

        logger.debug("Reading config property '{}' = '{}'", key, value);
        return value.trim();
    }
}


