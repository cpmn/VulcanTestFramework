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

import com.vulcan.framework.config.ConfigManager;
import com.vulcan.framework.core.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    // Track per-scenario if UI browser was started (safe for future parallel runs)
    private static final ThreadLocal<Boolean> uiBrowserStarted = ThreadLocal.withInitial(() -> false);

    @Before
    public void setUp(Scenario scenario) {

        if (isApiScenario(scenario)) {
            logger.info("API scenario detected. Skipping browser setup. Scenario='{}'", scenario.getName());
            uiBrowserStarted.set(false);
            return;
        }
        logger.info("UI scenario detected. Setting up browser. Scenario='{}'", scenario.getName());
        uiBrowserStarted.set(true);      

        // Get the browser instance from DriverFactory
        WebDriver driver = DriverFactory.getDriver();

        // Read the base URL from config.properties
        String baseUrl = ConfigManager.getInstance().get("ui.baseUrl");

        // Navigate to the base URL
        logger.info("Navigating to baseUrl: {}", baseUrl);
        driver.get(baseUrl);
    }
    @After
    public void tearDown(Scenario scenario) {
        // Only quit browser if we started it for this scenario
        if (Boolean.TRUE.equals(uiBrowserStarted.get()) && DriverFactory.isDriverInitialized()) {
            logger.info("UI scenario finished. Quitting browser. Scenario='{}'", scenario.getName());
            DriverFactory.quitDriver();
        } else {
            logger.info("No browser to quit for Scenario='{}'", scenario.getName());
        }
        uiBrowserStarted.remove();
    }

    private boolean isApiScenario(Scenario scenario) {
        // 1) Tag-based detection
        Collection<String> tags = scenario.getSourceTagNames();
        if (tags.contains("@api")) {
            return true;
        }

        // 2) Folder-based detection (feature path contains /features/api/)
        // This works even if someone forgets to tag scenarios.
        try {
            URI uri = scenario.getUri(); // available in Cucumber 7+
            if (uri != null) {
                String path = uri.toString().replace("\\", "/");
                return path.contains("/features/api/");
            }
        } catch (Exception ignored) {
            // If getUri() isn't available in your setup for any reason, we simply rely on tags.
        }

        return false;
    }
}
