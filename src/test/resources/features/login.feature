#
# Copyright (c) 2025 cpmn.tech
#
# Licensed under the MIT License.
# You may obtain a copy of the License at
# https://opensource.org/licenses/MIT
#
# This file is part of the VulcanTestFramework project.
# A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
#

Feature: Login

   As a user of the demo application
   I want to open the Login page
   So that I can start a session

   @smoke @Login
   Scenario: Open login page
      Given I am on the login page
      Then I should see the login form
