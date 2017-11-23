Feature: Creation of discussions

  Background:
    Given there is a Discussions server

  Scenario: create a discussion
    Given I have a discussion payload
    When I POST it to the /discussions endpoint
    Then I receive a 201 status code

  Scenario: read a discussion
    Given I'm using the API environnement
    When I GET it to the /discussions/id endpoint
    Then I receive a 200 status code

  Scenario: create a comment
    Given I have a comment payload
    When I POST it to the /discussions/id/comments endpoint
    Then I receive a 201 status code

