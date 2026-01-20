@run3
Feature: Testing to add a new subject from admin panel
  Scenario: User is able to add a new subject Cucumber in Subjects section from admin panel
    Given User is in Home Page
    When User clicks on Login
    And User enters admin email and admin password
    And User enters admin panel and clicks on Add Subject
    And User enters Subject Name and Description and Choose Icon
    And User clicks on add subject
    Then User is displayed Success Message
