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
import org.openqa.selenium.WebDriver;

public class Hooks {
    @Before
    public void setUp() {
        // Get the browser instance from DriverFactory
        WebDriver driver = DriverFactory.getDriver();

        // Read the base URL from config.properties
        String baseUrl = ConfigManager.getInstance().get("baseUrl");

        // Navigate to the base URL
        driver.get(baseUrl);
    }
    @After
    public void tearDown() {
        // Quit the browser after each scenario
        DriverFactory.quitDriver();
    }
}
