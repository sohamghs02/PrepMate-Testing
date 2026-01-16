Feature: User Signup for Prepmate Web Application

  Background:
    Given User is in the SignUpPage
    When User clicks on Signup Button

  Scenario Outline: A new user successfully signs up into the Web Application
    And User enters "<Name>", "<Email>", "<College>", "<Year>", "<Pass>" and "<ConfirmP>"
    And User clicks on Sign Up
    Then User is redirected to the Dashboard Section

    Examples:
      | Name      | Email              | College | Year | Pass      | ConfirmP  |
      | Test      | test@gmail.com     | XYZ     | 2025 | Test@123  | Test@123  |

  Scenario Outline: Signup fails when user already exists
    And User enters "<Name>", "<Email>", "<College>", "<Year>", "<Pass>" and "<ConfirmP>"
    And User clicks on Sign Up
    Then Error message should be displayed

    Examples:
      | Name        | Email                | College                  | Year | Pass       | ConfirmP   |
      | Soham Ghosh | sohamghs02@gmail.com | Narula Institute of Tech | 2025 | Soham@2002 | Soham@2002 |
