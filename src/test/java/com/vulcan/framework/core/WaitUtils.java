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

package com.vulcan.framework.core;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WaitUtils centralize explicit waits used by the UI layer.
 * 
 * Why:
 * - Stabilizes UI automation by waiting for conditions before interacting
 * - Keeps wait logic consistent and reusable across the framework
 * 
 * Notes:
 * - ONLY use explicit waits here; avoid implicit waits in WebDriver setup
 * - Timeout is passed from BasePage to allow scenario-specific configuration
 */
public class WaitUtils {
    private final WebDriver driver;
    private final Duration timeout;

    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        if(driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }
        this.driver = driver;
        this.timeout = Duration.ofSeconds(timeoutSeconds);        
    }

    /** Wait until element is visible. Returns the same element once visible */    
    public WebElement waitForVisible(WebElement element) {
        return new WebDriverWait(driver, timeout)
            .until(ExpectedConditions.visibilityOf(element));
    }

    /** Wait until element is clickable. Returns the same element once clickable */
    public WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(driver, timeout)
            .until(ExpectedConditions.elementToBeClickable(element));
    }
    /** Wait until title contains expected text */
    public boolean waitForTitleContains(String expectedText) {
        return new WebDriverWait(driver, timeout)
            .until(ExpectedConditions.titleContains(expectedText));
    }

    /** Wait until URL contains expected text */
    public boolean waitForUrlContains(String expectedText) {
        return new WebDriverWait(driver, timeout)
            .until(ExpectedConditions.urlContains(expectedText));
    }
}
