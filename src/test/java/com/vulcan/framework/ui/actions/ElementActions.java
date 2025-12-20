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

package com.vulcan.framework.ui.actions;

import com.vulcan.framework.core.WaitUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * ElementActions provides a single, consistent API for UI interactions.
 *
 * Why:
 * - Keeps Page Objects clean (they describe the page, not the timing logic).
 * - Adds stability (explicit waits before actions).
 * - Adds observability (standard logs for every action).
 *
 * Design rule:
 * - All click/type/read checks should go through this class.
 */
public class ElementActions {

    private static final Logger logger = LogManager.getLogger(ElementActions.class);

    private final WaitUtils wait;

    public ElementActions(WaitUtils wait) {
        if (wait == null) {
            throw new IllegalArgumentException("wait cannot be null");
        }
        this.wait = wait;
    }

    /** Click with a friendly element name for logs. */
    public void click(WebElement element, String name) {
        String safeName = normalizeName(name);
        logger.info("UI ACTION | click | element='{}'", safeName);
        wait.waitForClickable(element).click();
    }

    /** Type text into an element (with masking if it's sensitive). */
    public void type(WebElement element, String name, String value) {
        String safeName = normalizeName(name);
        boolean sensitive = isSensitiveField(safeName);

        logger.info("UI ACTION | type | element='{}' | value={}",
                safeName,
                sensitive ? "<masked>" : String.valueOf(value));

        WebElement visible = wait.waitForVisible(element);
        visible.clear();
        visible.sendKeys(value);
    }

    /** Explicit “sensitive” typing: always masked, even if name doesn't contain password. */
    public void typeSensitive(WebElement element, String name, String value) {
        String safeName = normalizeName(name);
        logger.info("UI ACTION | typeSensitive | element='{}' | value=<masked>", safeName);

        WebElement visible = wait.waitForVisible(element);
        visible.clear();
        visible.sendKeys(value);
    }

    /** Returns text after waiting for visibility. */
    public String getText(WebElement element, String name) {
        String safeName = normalizeName(name);
        WebElement visible = wait.waitForVisible(element);
        String text = visible.getText();
        logger.info("UI ACTION | getText | element='{}' | text='{}'", safeName, text);
        return text;
    }

    /** Safe visibility check (doesn't throw if element isn't visible). */
    public boolean isDisplayed(WebElement element, String name) {
        String safeName = normalizeName(name);
        try {
            boolean displayed = element.isDisplayed();
            logger.info("UI ACTION | isDisplayed | element='{}' | displayed={}", safeName, displayed);
            return displayed;
        } catch (Exception e) {
            logger.info("UI ACTION | isDisplayed | element='{}' | displayed=false (exception={})",
                    safeName, e.getClass().getSimpleName());
            return false;
        }
    }

    private String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) return "unknown-element";
        return name.trim();
    }

    private boolean isSensitiveField(String elementName) {
        String n = elementName.toLowerCase();
        return n.contains("password") || n.contains("secret") || n.contains("token");
    }
}
