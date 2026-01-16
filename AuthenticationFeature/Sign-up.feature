Feature: User Signup for Prepmate Web Application

  Background:
    Given User is in the SignUpPage
    When User clicks on Signup Button

  # ---------------- POSITIVE SCENARIO ----------------
  Scenario Outline: A new user successfully signs up into the Web Application
    And User enters <Full Name>, <Email>, <College Name>, <Passout Year>, <Password> and <Confirm Password>
    And User clicks on Sign Up
    Then User is redirected to the Dashboard Section

    Examples:
      | Full Name     | Email                | College Name            | Passout Year | Password   | Confirm Password |
      | Test User     | testuser@gmail.com   | XYZ                     | 2025         | Test@1234  | Test@1234        |

  # ---------------- NEGATIVE SCENARIO ----------------
  Scenario Outline: Signup fails when user already exists
    And User enters <Full Name>, <Email>, <College Name>, <Passout Year>, <Password> and <Confirm Password>
    And User clicks on Sign Up
    Then Error message "User already exists" should be displayed

    Examples:
      | Full Name      | Email                   | College Name            | Passout Year | Password    | Confirm Password |
      | Soham Ghosh    | sohamghs02@gmail.com    | Narula Institute of Tech| 2025         | Soham@2002  | Soham@2002       |
