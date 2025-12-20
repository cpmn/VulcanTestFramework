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
   Scenario: Open login page as Administrator
      Given I am on the login page
      And I should see the login form
      Then I log in as role "ADMINISTRATOR"
   
   @smoke
   Scenario: Open login page as a Performance user
      Given I am on the login page
      And I should see the login form
      Then I log in as role "PERFORMANCE"

   @smoke
   Scenario: Open login page as Standard user
      Given I am on the login page
      And I should see the login form
      Then I log in as role "STANDARD"