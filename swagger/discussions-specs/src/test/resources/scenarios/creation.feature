Feature: Creation of discussions

  Background:
    Given there is a Discussions server
    Given I have a InputDiscussion payload

  Scenario: create a discussion
    When I POST a correct discussion payload to the /discussions endpoint
    Then I receive a 201 status code
