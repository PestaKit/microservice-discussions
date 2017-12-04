Feature: Creation of discussions

  Background:
    Given there is a Discussions server
    Given I have a InputDiscussion payload
    Given I have a InputComment payload

  Scenario: create a discussion
    When I POST a correct discussion payload to the /discussions endpoint
    Then I receive a 201 status code

  Scenario: get a discussion
    Given I have a discussion
    When I GET it to the /discussions endpoint
    Then I receive a 200 status code

  Scenario: get a discussion by id
    Given  I have a discussion
    When I GET it to the /discussions/id endpoint
    Then I receive a 200 status code

  Scenario: get a discussion which not exist
    When I GET a discussion by incorrect id to the /discussions/id endpoint
    Then I receive a 404 status code

  Scenario: create a comment
    Given I have a discussion
    When I POST the InputComment payload to the /discussions/id/comments endpoint
    Then I receive a 201 status code

  Scenario: get a comment
    Given I have a comment
    When I GET it to the /discussions/id/comments/idComment endpoint
    Then I receive a 200 status code

  Scenario: get a comment which not exist
    Given I have a discussion
    When I GET a comment which not exist to the /discussions/id/comments/idComment endpoint
    Then I receive a 404 status code

  Scenario: put a comment
    Given I have a comment
    When I PUT it to the /discussions/id/comments/idComment endpoint
    Then I receive a 204 status code

  Scenario: put a comment which not exist
    Given I have a discussion
    When I PUT a comment which not exist to the /discussions/id/comments/idComment endpoint
    Then I receive a 404 status code

  Scenario: delete a comment
    Given I have a comment
    When I DELETE the InputComment payload to the /discussions/id/comments/idComment endpoint
    Then I receive a 204 status code
    
