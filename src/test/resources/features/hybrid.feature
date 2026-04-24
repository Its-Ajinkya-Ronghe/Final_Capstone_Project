Feature: Hybrid Banking Test

  Scenario Outline: End-to-End Flow
    Given I create user "<row>"
    When I login and transfer "200"
    Then I verify balance via API

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |