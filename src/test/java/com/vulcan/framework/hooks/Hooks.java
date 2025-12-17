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
import com.vulcan.framework.shared.context.DataRegistry;
import com.vulcan.framework.shared.context.ScenarioContext;
import com.vulcan.framework.shared.context.ScenarioKeys;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.net.URI;
import java.util.Collection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks responsible for:
 *
 * <ul>
 *   <li>Determining if a scenario is UI or API (folder-based + tag-based fallback)</li>
 *   <li>Starting the browser and navigating to the UI base URL for UI scenarios</li>
 *   <li>Skipping browser setup for API scenarios</li>
 *   <li>Performing teardown: browser shutdown (UI only), scenario cleanup actions, and context cleanup</li>
 * </ul>
 *
 * Key design goals:
 * <ul>
 *   <li><b>No reliance on human-added tags</b>: API detection works via feature folder structure (/features/api/)</li>
 *   <li><b>Scenario isolation</b>: no data leaks between scenarios due to ThreadLocal + ScenarioContext.clear()</li>
 *   <li><b>Hybrid-ready</b>: a DataRegistry can register cleanup actions for data created via API or UI flows</li>
 * </ul>
 */

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    /**
     * Tracks whether a browser was started for the current scenario.
     *
     * Why ThreadLocal?
     * - Safe for future parallel execution
     * - Prevents one scenario from affecting another scenario’s teardown behavior
     */
    private static final ThreadLocal<Boolean> uiBrowserStarted = ThreadLocal.withInitial(() -> false);

    /**
     * Runs before each scenario.
     *
     * For API scenarios:
     * - Skips browser startup entirely.
     *
     * For UI scenarios:
     * - Initializes WebDriver (DriverFactory)
     * - Reads ui.baseUrl from configuration
     * - Navigates to ui.baseUrl
     */
    @Before
    public void setUp(Scenario scenario) {

        // Decide scenario type first (API vs UI)
        if (isApiScenario(scenario)) {
            logger.info("API scenario detected. Skipping browser setup. Scenario='{}'", scenario.getName());
            uiBrowserStarted.set(false);
            return;
        }

        // UI scenario: start browser and navigate to base URL
        logger.info("UI scenario detected. Setting up browser. Scenario='{}'", scenario.getName());
        uiBrowserStarted.set(true);      

        // Get the browser instance from DriverFactory
        WebDriver driver = DriverFactory.getDriver();

        // Read the base URL from gradle.properties
        String baseUrl = ConfigManager.getInstance().get("ui.baseUrl");

        // Navigate to the base URL
        logger.info("Navigating to baseUrl: {}", baseUrl);
        driver.get(baseUrl);
    }

    /**
     * Runs after each scenario.
     *
     * Teardown order (important):
     * 1) Quit browser only if it was started for this scenario (UI only)
     * 2) Execute DataRegistry cleanup actions (API/UI/Hybrid)
     * 3) Clear ThreadLocals + ScenarioContext to avoid leaks
     *
     * We use a try/finally to guarantee cleanup happens even if quitDriver fails.
     */
    @After
    public void tearDown(Scenario scenario) {
        
        try {

            if (Boolean.TRUE.equals(uiBrowserStarted.get()) && DriverFactory.isDriverInitialized()) {
                logger.info("UI scenario finished. Quitting browser. Scenario='{}'", scenario.getName());
                DriverFactory.quitDriver();
            } else {
                logger.info("No browser to quit for Scenario='{}'", scenario.getName());
            }
        } finally {
            // Run sceanario clean up actions(API/UI/Hibrid)
            DataRegistry dataRegistry = ScenarioContext.getOptional(ScenarioKeys.DATA_REGISTRY, DataRegistry.class);
            if (dataRegistry != null && !dataRegistry.isEmpty()) {
                logger.info("Running DataRegistry cleanup actions for Scenario='{}'", scenario.getName());
                dataRegistry.cleanupAll();
            }

            // Clear ThreadLocals to prevent state leakage between scenarios          
            uiBrowserStarted.remove();
            // Clear ScenarioContext to avoid data leakage between scenarios
            ScenarioContext.clear();
        }
    }

    /**
     * Determines whether the current scenario should be treated as an API scenario.
     *
     * Detection strategy:
     * 1) Tag-based (@api) as a convenience
     * 2) Folder-based (feature file path contains /features/api/) as the primary rule
     *
     * Folder-based detection is preferred because it does not rely on humans remembering tags.
     */
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
