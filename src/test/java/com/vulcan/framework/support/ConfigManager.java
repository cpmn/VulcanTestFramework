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


package com.vulcan.framework.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigManager {
    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        loadProperties();
    }
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in resources directory.");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("config.properties file not found in resources directory.");
        }
    }
    public String get(String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }

        return value.trim();
    }
}


