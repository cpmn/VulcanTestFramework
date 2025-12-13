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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.vulcan.framework.config.ConfigManager;
import java.time.Duration;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            logger.info("WebDriver is null. Creating a new instance.");
            createDriver();
        }else {
            logger.debug("Reusing existing WebDriver instance.");
        }
        return driver;
    }

    private static void createDriver() {
        String browser = ConfigManager.getInstance().get("ui.browser").toLowerCase();
        int implicitWait = Integer.parseInt(ConfigManager.getInstance().get("ui.implicitWait"));

        logger.info("Creating WebDriver for browser: {}", browser);

        switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    logger.error("Unsupported browser configured: {}", browser);
                    throw new RuntimeException("Unsupported browser: " + browser);
        }

        logger.info("Setting implicit wait to {} seconds", implicitWait);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        logger.info("Maximizing browser window");       
        driver.manage().window().maximize();
    }
    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driver = null;
        }  else {
            logger.debug("quitDriver() called but WebDriver is already null.");
        }
    }
    public static boolean isDriverInitialized() {
        return driver != null;
    }    
}
