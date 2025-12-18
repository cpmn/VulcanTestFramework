# Copyright (c) 2025 cpmn.tech
#
# Licensed under the MIT License.
# You may obtain a copy of the License at
# https://opensource.org/licenses/MIT
#
# This file is part of the VulcanTestFramework project.
# A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
#

@api
Feature: API Health Check

  Scenario: SauceDemo base URL responds successfully
    When I call the API health endpoint
    Then the API response status should be 200
    And the API response body should contain "Swag Labs"
