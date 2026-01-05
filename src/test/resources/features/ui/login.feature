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
  I want to log in to the application
  So that I can access the system according to my role

  @smoke @login
  Scenario: Successful login as Administrator
   Given I am on the login page
     And the login form is displayed
   When I log in as role "ADMINISTRATOR"
   Then I should be successfully logged in
     And I should see the products page

  @smoke @login
  Scenario: Successful login as Performance user
    Given I am on the login page
    And the login form is displayed
    When I log in as role "PERFORMANCE"
    Then I should be successfully logged in
     And I should see the products page

  @smoke @login
  Scenario: Successful login as Standard user
    Given I am on the login page
    And the login form is displayed
    When I log in as role "STANDARD"
    Then I should be successfully logged in
     And I should see the products page
