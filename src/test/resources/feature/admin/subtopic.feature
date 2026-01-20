Feature: User successfully adds a new subtopic
  Scenario: User successfully adds a new subtopic in OOP

    Given User is in the Home Page Section
    When User Clicks on Login
    And User enters admin Email and admin password
    And User clicks on login button
    And User clicks on admin panel and clicks on add subtopic
    And User clicks on Select Subject and clicks on Add Subtopic
    And User clics on Add Subtopic Button
    Then Success message displayed to user