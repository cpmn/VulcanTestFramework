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

package com.vulcan.framework.hooks;

import com.vulcan.framework.support.ConfigManager;
import com.vulcan.framework.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {

        logger.info("Starting scenario - setting up WebDriver and navigating to baseUrl");

        // Get the browser instance from DriverFactory
        WebDriver driver = DriverFactory.getDriver();

        // Read the base URL from config.properties
        String baseUrl = ConfigManager.getInstance().get("baseUrl");

        // Navigate to the base URL
        logger.info("Navigating to baseUrl: {}", baseUrl);
        driver.get(baseUrl);
    }
    @After
    public void tearDown() {
        // Quit the browser after each scenario
        logger.info("Scenario finished - quitting WebDriver");
        DriverFactory.quitDriver();
    }
}
