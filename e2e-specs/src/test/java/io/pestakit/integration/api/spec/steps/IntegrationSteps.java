package io.pestakit.integration.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Th;
import io.pestakit.integration.api.spec.helpers.Environment;

import io.pestakit.users.api.dto.Credentials;
import io.pestakit.users.api.dto.Token;
import io.pestakit.users.api.dto.User;

import io.pestakit.discussions.api.dto.InputDiscussion;
import io.pestakit.discussions.api.dto.InputComment;
import io.pestakit.discussions.api.dto.OutputDiscussion;
import io.pestakit.discussions.api.dto.OutputComment;
import org.assertj.core.util.Compatibility;
import org.junit.Assert;
import org.omg.CORBA_2_3.portable.OutputStream;


import javax.sound.midi.SysexMessage;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Miguel Santamaria on 15/12/17.
 * modified by Adrien Marco 29/12/17.
 */
public class IntegrationSteps {

    private Environment environment;

    //API users
    private io.pestakit.users.api.DefaultApi usersApi;
    private io.pestakit.users.ApiResponse lastApiResponseUsersApi;
    private io.pestakit.users.ApiException lastApiExceptionUsersApi;
    private boolean lastApiCallThrewExceptionUsersApi;
    private int lastStatusCodeUsersApi;
    private User user;
    private int uid;
    private Token usersToken[] = new Token[2];

    //API Discussions
    private io.pestakit.discussions.api.DefaultApi discussionsApi;
    private io.pestakit.discussions.ApiResponse lastApiResponseDiscussionsApi;
    private io.pestakit.discussions.ApiException lastApiExceptionDiscussionsApi;
    private boolean lastApiCallThrewExceptionDiscussionsApi;
    private int lastStatusCodeDiscussionsApi;
    private InputDiscussion discussion;
    private InputComment comments[] = new InputComment[2];
    private int commentsId[] = new int[2];
    private Object discussionLocation;
    private Object commentLocation;
    private int discussionId;
    private User users[] = new User[2];
    private OutputDiscussion outputDiscussion;
    List<OutputDiscussion> outputDiscussions;
    private int randomIdArticle;
    private int lastNbDiscussion;
    private OutputComment outputComment;

    public IntegrationSteps(Environment environment) {
        this.environment = environment;
        this.usersApi = environment.getUsersApi();
        this.discussionsApi = environment.getDiscussionsApi();
    }

    /* Server check */

    @Given("^there is a Users server$")
    public void there_is_a_Users_server() throws Throwable {
        assertNotNull(environment.getUsersApi());
    }
    @Given("^there is a Discussions server$")
    public void there_is_a_Discussions_server() throws Throwable {
        assertNotNull(environment.getDiscussionsApi());
    }


    /* Users Steps */

    @Given("^I have a full user (\\d) payload$")
    public void i_have_a_correct_user_payload(int userID) {
        users[userID] = new User();
        String name = "manu";
        uid = (int) new Date().getTime();
        name = uid + name;

        users[userID].username("user" + name);
        users[userID].password("pass" + name);
        users[userID].setEmail(name + "@" + name + ".com");
        users[userID].setFirstName("first" + name);
        users[userID].setLastName("last" + name);
        users[userID].setDisplayName("display" + name);
    }

    @Given("^I can create the user (\\d)$")
    public void i_can_create_the_user(int userID) throws Throwable {
        i_POST_user_to_the_users_endpoint(userID);
    }

    @Given("^Creat and auth user (\\d+)$")
    public void creat_and_auth_user(int userID) throws Throwable {
        i_have_a_correct_user_payload(userID);
        i_can_create_the_user(userID);
        i_receive_a_user_api_status_code(201);
        i_POST_user_to_the_auth_endpoint(userID);
        i_receive_a_user_api_status_code(200);
    }

    @When("^I POST user (\\d) to the /users endpoint$")
    public void i_POST_user_to_the_users_endpoint(int userID) throws Throwable {
        try {
            lastApiResponseUsersApi = usersApi.createUserWithHttpInfo(users[userID]);
            lastApiCallThrewExceptionUsersApi = false;
            lastApiExceptionUsersApi = null;
            lastStatusCodeUsersApi = lastApiResponseUsersApi.getStatusCode();
        } catch (io.pestakit.users.ApiException e) {
            lastApiCallThrewExceptionUsersApi = true;
            lastApiResponseUsersApi = null;
            lastApiExceptionUsersApi = e;
            lastStatusCodeUsersApi = lastApiExceptionUsersApi.getCode();
        }
    }

    @When("^I POST user (\\d) to the /auth endpoint$")
    public void i_POST_user_to_the_auth_endpoint(int userID) throws Throwable {
        try {
            Credentials credentials = new Credentials();
            credentials.setPassword(users[userID].getPassword());
            credentials.setIdentifier(users[userID].getUsername());
            lastApiResponseUsersApi = usersApi.authWithHttpInfo(credentials);
            usersToken[userID] = (Token) lastApiResponseUsersApi.getData();
            lastApiCallThrewExceptionUsersApi = false;
            lastApiExceptionUsersApi = null;
            lastStatusCodeUsersApi = lastApiResponseUsersApi.getStatusCode();
        } catch (io.pestakit.users.ApiException e) {
            lastApiCallThrewExceptionUsersApi = true;
            lastApiResponseUsersApi = null;
            lastApiExceptionUsersApi = e;
            lastStatusCodeUsersApi = lastApiExceptionUsersApi.getCode();
        }
    }

    @Then("^I receive a (\\d+) user API status code$")
    public void i_receive_a_user_api_status_code(int code) throws Throwable {
        assertEquals(code, lastStatusCodeUsersApi);
    }




    /* Discussions steps*/

    @Given("^I have an incorrect discussion payload$")
    public void i_have_an_incorrect_discussion_payload() {
        discussion = new io.pestakit.discussions.api.dto.InputDiscussion();
    }

    @Given("^I have a discussion with full payload for an article$")
    public void user_have_a_discussion_with_full_payload() throws Throwable {
        discussion = new io.pestakit.discussions.api.dto.InputDiscussion();
        randomIdArticle = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        discussion.setIdArticle(randomIdArticle);
    }

    @Given("^User (\\d) POST in the /discussions endpoint a correct playload$")
    public void user_post_correct_discussion_playload(int code) throws Throwable {
        user_have_a_discussion_with_full_payload();
        user_POST_discussion_to_the_discussions_endpoint_with_a_token(code);
        receive_a_discussion_api_status_code(201);
    }

    @When("^User (\\d) POST it to the /discussions endpoint without token$")
    public void user_POST_discussion_to_the_discussions_endpoint_without_a_token(int userID) throws Throwable {
        try {

            lastApiResponseDiscussionsApi = discussionsApi.createDiscussionWithHttpInfo(discussion);
            lastApiCallThrewExceptionDiscussionsApi = false;

            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

            discussionLocation = lastApiResponseDiscussionsApi.getHeaders().get("Location");
            String locationStr = discussionLocation.toString();
            String idStr = locationStr.substring(locationStr.lastIndexOf('/') + 1);
            idStr = idStr.substring(0, idStr.length() - 1);
            discussionId = Integer.parseInt(idStr);

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }

    @When("^User (\\d) DEL in the /discussion/id endpoint the discussion$")
    public void user_del_discussion(int userID) throws Throwable {
        try {
            String value = "Bearer " + usersToken[userID].getToken();
            discussionsApi.getApiClient().addDefaultHeader("Authorization", value);

            lastApiResponseDiscussionsApi = discussionsApi.delDiscussionWithHttpInfo(discussionId);
            lastApiCallThrewExceptionDiscussionsApi = false;

            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }

    @Given("^There are (\\d) more discussions$")
    public void there_are_several_discussions(int nbdiscussion) throws Throwable{
        i_GET_it_to_the_discussions_endpoint();
        lastNbDiscussion = outputDiscussions.size();
        for(int i = 0; i <nbdiscussion; i++){
            creat_and_auth_user(i);
            user_post_correct_discussion_playload(i);
        }

    }



    @When("^User (\\d) POST it to the /discussions endpoint with a token$")
    public void user_POST_discussion_to_the_discussions_endpoint_with_a_token(int userID) throws Throwable {
        try {
            /* Adding the token in the header */
            String value = "Bearer " + usersToken[userID].getToken();
            discussionsApi.getApiClient().addDefaultHeader("Authorization", value);

            user_POST_discussion_to_the_discussions_endpoint_without_a_token(userID);

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }

    @Then("^I can find the (\\d) more discussions$")
    public void i_can_find_discussions(int nbdiscussion){
        assertEquals(outputDiscussions.size(), lastNbDiscussion + nbdiscussion);
    }

    @Then("^There is a discussion for an article and user (\\d) is the author$")
    public void there_is_a_discussion_for_article(int idUser) throws Throwable {
        i_GET_it_to_the_discussions_id_endpoint(discussionId);
        user_is_the_author_of_the_discussion(idUser);

    }

    @Then("^Receive a (\\d+) discussion API status code$")
    public void receive_a_discussion_api_status_code(int code) throws Throwable {
        assertEquals(code, lastStatusCodeDiscussionsApi);
    }

   /* comments, votes and reports steps */

    @Given("^I have a comment (\\d) with full payload$")
    public void user_have_a_comment_with_full_payload(int commentID) throws Throwable {
        comments[commentID] = new io.pestakit.discussions.api.dto.InputComment();
        comments[commentID].setComment("Comment" + uid);
    }

    @Given("^I have a reply (\\d) comment with full payload$")
    public void user_have_a_reply_comment_with_full_payload(int commentID) throws Throwable{
        comments[commentID] = new io.pestakit.discussions.api.dto.InputComment();
        comments[commentID].setComment("Comment" + uid);
        comments[commentID].setFatherUrl("localhost/discussion/"+discussionId+"/comments"+String.valueOf(commentsId[0]));
    }

    @When("^User (\\d) POST comment (\\d) to the /discussions/id/comments endpoint without token$")
    public void user_POST_discussion_to_the_discussions_endpoint_without_a_token(int userID, int idComment) throws Throwable {
        try {
            lastApiResponseDiscussionsApi = discussionsApi.createCommentWithHttpInfo(discussionId,comments[idComment]);
            lastApiCallThrewExceptionDiscussionsApi = false;
            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

            discussionLocation = lastApiResponseDiscussionsApi.getHeaders().get("Location");
            String locationStr = discussionLocation.toString();
            String idStr = locationStr.substring(locationStr.lastIndexOf('/') + 1);
            idStr = idStr.substring(0, idStr.length() - 1);
            commentsId[idComment] = Integer.parseInt(idStr);

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }

    @When("^User (\\d) POST comment (\\d) to the /discussions/id/comments endpoint with token$")
    public void user_POST_a_comment_endpoint_with_a_token(int userID, int commentId) throws Throwable {
        try {
            String value = "Bearer " + usersToken[userID].getToken();
            discussionsApi.getApiClient().addDefaultHeader("Authorization", value);

            user_POST_discussion_to_the_discussions_endpoint_without_a_token(userID, commentId);

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }


    @Then("^There is a comment (\\d) for a discussion and user (\\d) is the author$")
    public void there_is_a_comment_for_a_discussion(int commentID, int idUser) throws Throwable{
        i_get_a_comment_by_id(commentID);
        user_is_the_author_of_the_comment(idUser);
    }













    @When("^I GET comment (\\d) to the /discussions/id/comments/idComment endpoint$")
    public void i_get_a_comment_by_id(int commentID) throws Throwable{
        try{
            lastApiResponseDiscussionsApi = discussionsApi.getCommentWithHttpInfo(discussionId,commentsId[commentID]);
            outputComment = (OutputComment) lastApiResponseDiscussionsApi.getData();
            lastApiCallThrewExceptionDiscussionsApi = false;
            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }









    @Given("^User (\\d) POST a discussion and comment$")
    public void user_post_correct_comment_playload(int code) throws Throwable {
        user_have_a_comment_with_full_payload(code);
        user_POST_a_comment_endpoint_with_a_token(code,0);

        //user_POST_a_comments_endpoint_with_a_token(code);
        receive_a_discussion_api_status_code(201);
    }

    @When("^User (\\d+) DEL in the /discussion/id/comments endpoint the discussion$")
    public void user_del_a_comment(int userID) {
        try {
            String value = "Bearer " + usersToken[userID].getToken();
            discussionsApi.getApiClient().addDefaultHeader("Authorization", value);

            lastApiResponseDiscussionsApi = discussionsApi.delCommentWithHttpInfo(discussionId, commentsId[0]);
            lastApiCallThrewExceptionDiscussionsApi = false;

            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }

    }






















    @When("^I GET it to the /discussions/(\\d) endpoint$")
    public void i_GET_it_to_the_discussions_id_endpoint(int idArticle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            lastApiResponseDiscussionsApi = discussionsApi.getDiscussionWithHttpInfo(idArticle);
            outputDiscussion = (OutputDiscussion) lastApiResponseDiscussionsApi.getData();
            lastApiCallThrewExceptionDiscussionsApi = false;
            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();

        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }

    @Then("^User (\\d) is the autor of the discussion$")
    public void user_is_the_author_of_the_discussion(int idUser) throws Throwable {
        assertEquals(outputDiscussion.getAuthor(), users[idUser].getUsername());
    }

    @Then("^User (\\d) is the autor of the comment")
    public void user_is_the_author_of_the_comment(int idUser) throws Throwable {
        assertEquals(outputComment.getAuthor(), users[idUser].getUsername());
    }





    @When("^I GET discussions to the /discussions endpoint$")
    public void i_GET_it_to_the_discussions_endpoint() throws Throwable {
        try{
            lastApiResponseDiscussionsApi = discussionsApi.getDiscussionsWithHttpInfo();
            outputDiscussions = (List<OutputDiscussion>) lastApiResponseDiscussionsApi.getData();
            lastApiCallThrewExceptionDiscussionsApi = false;
            lastApiExceptionDiscussionsApi = null;
            lastStatusCodeDiscussionsApi = lastApiResponseDiscussionsApi.getStatusCode();
        } catch (io.pestakit.discussions.ApiException e) {
            lastApiCallThrewExceptionDiscussionsApi = true;
            lastApiResponseDiscussionsApi = null;
            lastApiExceptionDiscussionsApi = e;
            lastStatusCodeDiscussionsApi = lastApiExceptionDiscussionsApi.getCode();
        }
    }




    @Given("^User (\\d) creat a discussion and post a comment$")
    public void user_creat_discussion_and_comment(int idUser) throws Throwable{
        creat_and_auth_user(idUser);
        user_post_correct_discussion_playload(idUser);
        user_post_correct_comment_playload(idUser);

    }



}
