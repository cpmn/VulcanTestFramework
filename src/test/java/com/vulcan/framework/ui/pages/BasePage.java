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

package com.vulcan.framework.ui.pages;

import com.vulcan.framework.config.ConfigManager;
import com.vulcan.framework.core.DriverFactory;
import com.vulcan.framework.core.WaitUtils;
import com.vulcan.framework.ui.actions.ElementActions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage is the parent class for all Page Objects.
 *
 * Responsibilities:
 * - Obtain WebDriver from DriverFactory
 * - Initialize @FindBy elements using PageFactory
 * - Provide stable, logged UI interactions via ElementActions + explicit waits
 *
 * Notes:
 * - We intentionally avoid Thread.sleep.
 * - Passwords/secrets should be typed using typeSensitive().
 */
public abstract class BasePage {

    protected final WebDriver driver;

    /**
     * Logger is created per actual page class (LoginPage, InventoryPage, etc.).
     * This makes logs easier to read than always "BasePage".
     */
    protected final Logger logger = LogManager.getLogger(getClass());

    /** Explicit wait utilities (visibility/clickable/title/url). */
    protected final WaitUtils wait;

    /** Centralized element interactions (click/type/getText/isDisplayed). */
    protected final ElementActions actions;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();

        // Reuse your existing ui.implicitWait as a simple "explicit wait timeout" for now.
        // Later you can introduce ui.explicitWait without touching pages.
        int timeoutSeconds = Integer.parseInt(ConfigManager.getInstance().get("ui.implicitWait"));

        this.wait = new WaitUtils(driver, timeoutSeconds);
        this.actions = new ElementActions(wait);

        PageFactory.initElements(driver, this);
    }

    // ---------------------------
    // Protected helper methods
    // ---------------------------

    /**
     * Clicks an element with waiting + consistent logging.
     *
     * @param element element to click
     * @param name friendly element name for logs
     */
    protected void click(WebElement element, String name) {
        actions.click(element, name);
    }

    /**
     * Types into an element with waiting + consistent logging.
     * Value is auto-masked if name looks sensitive (password/token/secret).
     */
    protected void type(WebElement element, String name, String text) {
        actions.type(element, name, text);
    }

    /**
     * Always masks the typed value in logs (recommended for password fields).
     */
    protected void typeSensitive(WebElement element, String name, String text) {
        actions.typeSensitive(element, name, text);
    }

    /**
     * Safe visibility check (returns false if element isn't present/displayed).
     */
    protected boolean isDisplayed(WebElement element, String name) {
        return actions.isDisplayed(element, name);
    }

    /**
     * Returns the current page title.
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("UI INFO | pageTitle='{}'", title);
        return title;
    }
}
