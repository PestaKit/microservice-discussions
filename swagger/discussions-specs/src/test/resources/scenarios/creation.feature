Feature: Creation of discussions

  Background:
    Given there is a Discussions server
    Given I have a InputDiscussion payload
    Given I have a InputComment payload

  Scenario: create a discussion
    When I POST a correct discussion payload to the /discussions endpoint
    Then I receive a 201 status code
  
  
  Scenario: create a comment
    Given I have a discussion
    When I POST the InputComment payload to the /discussions/id/comments endpoint
    Then I receive a 201 status code

  Scenario: get a comment
    Given I have a discussion
    When I GET the OutputComment payload to the /discussions/id/comments/idComment endpoint
    Then I receive a 200 status code
    
