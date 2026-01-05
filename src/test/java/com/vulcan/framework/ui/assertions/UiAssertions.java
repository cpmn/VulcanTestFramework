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

package com.vulcan.framework.ui.assertions;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;

import com.vulcan.framework.core.DriverFactory;
import com.vulcan.framework.ui.pages.InventoryPage;
import com.vulcan.framework.ui.pages.LoginPage;


public class UiAssertions {
    private static final Logger logger = LogManager.getLogger(UiAssertions.class);
    
    public static void assertLoginFormVisible(LoginPage loginPage) {
        logger.info("Asserting login form is visible");
        assertTrue("Login form should be visible", loginPage.isLoginFormVisible());
    }
    /**
     * "Successfully logged in" (SauceDemo) means:
     * - Browser navigated to inventory.html (Products page)
     */
    public static void assertSuccessfullyLoggedIn() {
        WebDriver driver = DriverFactory.getDriver();
        String url = driver.getCurrentUrl();
        assertTrue("Expected to be on inventory page after login, but url was: " + url,
                url.contains("inventory.html"));
    }

    /**
     * Validates we can actually see the Products page UI.
     * Stronger than URL-only checks.
     */
    public static void assertProductsPageVisible() {
        InventoryPage inventoryPage = new InventoryPage();
        assertTrue("Expected Products page to be visible (inventory page not loaded).",
                inventoryPage.isLoaded());
    }
}
