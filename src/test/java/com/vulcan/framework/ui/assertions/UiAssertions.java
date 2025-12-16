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

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import com.vulcan.framework.ui.pages.LoginPage;


public class UiAssertions {
    private static final Logger logger = LogManager.getLogger(UiAssertions.class);
    
    public static void assertLoginFromVisible(LoginPage loginPage) {
        logger.info("Asserting login form is visible");
        assertTrue("Login form should be visible", loginPage.isLoginFormVisible());
    }
}
