Feature: Creation of discussions

  Background:
    Given there is a Discussions server

  Scenario: create a discussion
    Given I have a discussion payload
    When I POST it to the /discussion endpoint
    Then I receive a 201 status code