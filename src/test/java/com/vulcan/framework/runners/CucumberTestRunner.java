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

package com.vulcan.framework.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    glue = {
        "com.vulcan.framework.steps.ui",
        "com.vulcan.framework.steps.api",
        "com.vulcan.framework.hooks"
     },    
    monochrome = true
)
public class CucumberTestRunner {    
    // This class remains empty.
    // It is used only as an entry point for JUnit and Cucumber.
}
