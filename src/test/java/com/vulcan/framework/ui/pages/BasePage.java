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

import com.vulcan.framework.core.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final Logger logger = LogManager.getLogger(BasePage.class);

    protected BasePage() {
        // Get the shared WebDriver instance from DriverFactory
        this.driver = DriverFactory.getDriver();
        // Initialize @FindBy elements in the child page classes
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        logger.info("Clicking on element: {}", describeElement(element));
        element.click();
    }

    protected void type(WebElement element, String text) {
        logger.info("Typing text '{}' into element: {}", text, describeElement(element));
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isDisplayed(WebElement element) {
       boolean visible = element.isDisplayed();
       logger.info("Element {} is displayed: {}", describeElement(element), visible);
       return visible;
    }
    public String getPageTitle(){
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }

    private String describeElement(WebElement element) {
        // This will not be perfect, but is useful in logs
        try {
            return element.toString();
        } catch (Exception e) {
            return "WebElement";
        }
    }
    
}
