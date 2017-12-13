Feature: Creation of discussions

  Background:
    Given there is a Discussions server
    Given I have a InputDiscussion payload
    Given I have a InputComment payload
    Given I have a discussion
    Given I have a comment

  Scenario: create a discussion
    When I POST a correct discussion payload to the /discussions endpoint
    Then I receive a 201 status code

  Scenario: get a discussion
    When I GET it to the /discussions endpoint
    Then I receive a 200 status code

  Scenario: get a discussion by id
    When I GET it to the /discussions/id endpoint
    Then I receive a 200 status code

  Scenario: get a discussion which not exist
    When I GET a discussion by incorrect id to the /discussions/id endpoint
    Then I receive a 404 status code

  Scenario: create a comment
    When I POST the InputComment payload to the /discussions/id/comments endpoint
    Then I receive a 201 status code

  Scenario: get all comments
    When I GET all discussion to the /discussion/id/comments endpoint
    Then I receive a 200 status code

  Scenario: get all comments by incorrect discussion id
    When I GET all discussion by incorrect discussion id to the /discussion/id/comments endpoint
    Then I receive a 404 status code
    

  Scenario: get a comment
    When I GET it to the /discussions/id/comments/idComment endpoint
    Then I receive a 200 status code

  Scenario: get a comment which not exist
    When I GET a comment which not exist to the /discussions/id/comments/idComment endpoint
    Then I receive a 404 status code

  Scenario: put a comment
    When I PUT it to the /discussions/id/comments/idComment endpoint
    Then I receive a 204 status code

  Scenario: put a comment which not exist
    When I PUT a comment which not exist to the /discussions/id/comments/idComment endpoint
    Then I receive a 404 status code

  Scenario: delete a discussion by id
    When I DELETE it to the /discussions/id endpoint
    Then I receive a 204 status code

  Scenario: delete a comment
    When I DELETE it to the /discussions/id/comments/idComment endpoint
    Then I receive a 204 status code
    
