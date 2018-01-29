Feature: Integration of Users and Surveys microservices

  Background:
    Given there is a Users server
    Given there is a Discussions server

  #Scenario: I can't post a comment to a discussion without token
    #Given Creat and auth user 0
    #Given User 0 POST in the /discussions endpoint a correct playload
    #Given I have a comment 0 with full payload
    #When User 1 POST comment 0 to the /discussions/id/comments endpoint without token
    #Then Receive a 403 discussion API status code




          #Scenario: user 0 delete a discussion who user 0 created with comments
            #Given Creat and auth user 0
            #Given User 0 post a discussion
            #Given at least 1 comments related to discussion
            #Given at least 1 reply to a comment
            #When User 0 delete it on the /discussions/id endpoints
            #Then Receive a 200



  Scenario: I can create a user
   Given I have a full user 0 payload
   When I POST user 0 to the /users endpoint
   Then I receive a 201 user API status code

  Scenario: I can authentificate a user
   Given I have a full user 0 payload
   Given I can create the user 0
   When I POST user 0 to the /auth endpoint
   Then I receive a 200 user API status code

  Scenario: I can't create a discussions without token
   Given I have a discussion with full payload for an article
   When User 0 POST it to the /discussions endpoint without token
   Then Receive a 403 discussion API status code

  Scenario: User 0 can post a discussion for an article
   Given Creat and auth user 0
   Given I have a discussion with full payload for an article
   When User 0 POST it to the /discussions endpoint with a token
   Then Receive a 201 discussion API status code
   Then There is a discussion for an article and user 0 is the author

  Scenario: User 1 can't post a discussion with an incorrect payload
   Given Creat and auth user 0
   Given I have an incorrect discussion payload
   When User 0 POST it to the /discussions endpoint with a token
   Then Receive a 400 discussion API status code

  Scenario: I can get all discussions
   Given There are 2 more discussions
   When I GET discussions to the /discussions endpoint
   Then Receive a 200 discussion API status code
   Then I can find the 2 more discussions

  Scenario: user 0 delete a discussion who user 0 created
   Given Creat and auth user 0
   Given User 0 POST in the /discussions endpoint a correct playload
   When User 0 DEL in the /discussion/id endpoint the discussion
   Then Receive a 204 discussion API status code

  Scenario: user 1 delete a discussion who user 0 created
   Given Creat and auth user 0
   Given Creat and auth user 1
   Given User 0 POST in the /discussions endpoint a correct playload
   When User 1 DEL in the /discussion/id endpoint the discussion
   Then Receive a 403 discussion API status code

  Scenario: I can create a discussions and comment it if I have a token
   Given Creat and auth user 0
   Given User 0 POST in the /discussions endpoint a correct playload
   Given I have a comment 0 with full payload
   When User 0 POST comment 0 to the /discussions/id/comments endpoint with token
   Then Receive a 201 discussion API status code

  Scenario: User 0 can POST a comment and he is the author
   Given Creat and auth user 0
   Given User 0 POST in the /discussions endpoint a correct playload
   Given I have a comment 0 with full payload
   When User 1 POST comment 0 to the /discussions/id/comments endpoint without token
   Then Receive a 201 discussion API status code
   Then There is a comment 0 for a discussion and user 0 is the author


#Problème d'ID
  Scenario: I can create a discussions,comment and reply to comment if I have a token
    Given User 0 creat a discussion and post a comment
    Given I have a reply 1 comment with full payload
    When User 1 POST comment 1 to the /discussions/id/comments endpoint without token
    Then Receive a 201 discussion API status code
