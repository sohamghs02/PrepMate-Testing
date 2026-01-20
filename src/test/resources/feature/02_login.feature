@run2
Feature: Testing User Login into PrepMate Web Application

  Scenario Outline: User successfully logs into the Web Application
    Given User is in the Sign-UpPage
    When User clicks on Login Button
    And User enters "<email>" and "<password>"
    And User Clicks on Submit button
    Then System shows "<result>" and user successfully enters the dashboard section

    Examples:
      | email              | password  | result               |
      | test@gmail.com     | Test@123  | Dashboard     |
      | testcase@gmail.com | Test@123  | Invalid Credentials  |
      | test@gmail.com     | Test@1234 | Invalid Credentials  |
