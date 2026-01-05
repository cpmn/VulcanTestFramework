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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage {

    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public InventoryPage() {
        super();
        logger.info("InventoryPage initialized");
    }

    /**
     * Return true if we are on the Product page.
     * Uses stable UI signals instead of sleeps.
     */
    public boolean isLoaded() {
        return isDisplayed(inventoryContainer, "inventoryContainer")
                && isDisplayed(pageTitle, "pageTitle")
                && pageTitle.getText().equals("Products");
    }
}
