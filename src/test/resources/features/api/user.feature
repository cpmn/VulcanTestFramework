# Copyright (c) 2025 cpmn.tech
#
# Licensed under the MIT License.
# You may obtain a copy of the License at
# https://opensource.org/licenses/MIT
#
# This file is part of the VulcanTestFramework project.
# A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
#

Feature: User API

  @api @smoke
  Scenario: Get user by id
    When I request the user with id "1"
    Then the API response status should be 200
    And the API response field "id" should be 1

  @api @smoke
  Scenario: Get user by id as second attempt
    When I request the user with id "1"
    Then the API response status should be 200
    When I request the user with id "1"1