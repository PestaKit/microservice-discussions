package io.pestakit.discussions.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.discussions.ApiException;
import io.pestakit.discussions.ApiResponse;
import io.pestakit.discussions.api.DefaultApi;
import io.pestakit.discussions.api.dto.InputComment;
import io.pestakit.discussions.api.dto.OutputComment;
import io.pestakit.discussions.api.dto.InputDiscussion;

import io.pestakit.discussions.api.spec.helpers.Environment;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    InputDiscussion discussion;
    InputComment comment;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private int lastIdDiscussion;
    private int lastIdComment;
    private final int INCORRECT_ID = 0;
    private OutputComment outputcomment;
    private Object discussionLocation;
    private Object commentLocation;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }


    @Given("^there is a Discussions server$")
    public void there_is_a_Discussions_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a InputDiscussion payload$")
    public void i_have_a_discussion_payload() throws Throwable {
        discussion = new io.pestakit.discussions.api.dto.InputDiscussion();
        discussion.setIdArticle(1);
    }

    @When("^I POST it to the /discussions endpoint$")
    public void i_POST_it_to_the_discussions_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createDiscussionWithHttpInfo(discussion);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
            Map<String, List<String>> headers = lastApiResponse.getHeaders();
            discussionLocation = headers.get("Location").get(0);
            String tmp = discussionLocation.toString();
            lastIdDiscussion = Integer.parseInt(tmp.substring(tmp.lastIndexOf('/') + 1));
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, lastStatusCode);
    }

    @Given("^I have a InputComment payload$")
    public void i_have_a_comment_payload() throws Throwable {
        comment = new io.pestakit.discussions.api.dto.InputComment();
        comment.setAuthor("sas");
        comment.setComment("comment");
        comment.setFatherUrl("/1");
    }




    @Given("^I'm using the API environnement$")
    public void i_m_using_api_environnement() throws Throwable {

    }



    @When("^I POST a correct discussion payload to the /discussions endpoint$")
    public void i_POST_a_correct_discussion_payload_to_the_discussions_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            lastApiResponse = api.createDiscussionWithHttpInfo(discussion);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Given("^I have a discussion$")
    public void i_have_a_discussion() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        i_POST_it_to_the_discussions_endpoint();
        //i_receive_a_status_code(201);
    }

    @Given("^I have a comment$")
    public void i_have_a_comment() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        i_POST_it_to_the_discussions_endpoint();
        i_POST_the_InputComment_payload_to_the_discussions_id_comments_endpoint();
    }


    @When("^I POST the InputComment payload to the /discussions/id/comments endpoint$")
    public void i_POST_the_InputComment_payload_to_the_discussions_id_comments_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            lastApiResponse = api.createCommentWithHttpInfo(lastIdDiscussion,comment);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
            Map<String, List<String>> headers = lastApiResponse.getHeaders();
            commentLocation = headers.get("Location").get(0);
            String tmp = commentLocation.toString();
            lastIdComment = Integer.parseInt(tmp.substring(tmp.lastIndexOf('/') + 1));
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I GET it to the /discussions endpoint$")
    public void i_GET_it_to_the_discussions_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getDiscussionsWithHttpInfo();
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I GET all discussion to the /discussion/id/comments endpoint$")
    public void i_GET_all_discussion_to_the_discussion_id_comments_endpoint() throws Throwable {
        try{
            lastApiResponse = api.getCommentsWithHttpInfo(lastIdDiscussion);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        i_receive_a_status_code(200);
    }

    @When("^I GET all discussion by incorrect discussion id to the /discussion/id/comments endpoint$")
    public void i_GET_all_discussion_by_incorrect_discussion_id_to_the_discussion_id_comments_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getCommentsWithHttpInfo(INCORRECT_ID);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        i_receive_a_status_code(404);
    }



    @When("^I GET it to the /discussions/id/comments/idComment endpoint$")
    public void i_GET_it_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getCommentWithHttpInfo(lastIdDiscussion,lastIdComment);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        i_receive_a_status_code(200);

    }

    @When("^I GET it to the /discussions/id endpoint$")
    public void i_GET_it_to_the_discussions_id_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getDiscussionWithHttpInfo(lastIdDiscussion);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I GET a discussion by incorrect id to the /discussions/id endpoint$")
    public void i_GET_a_discussion_by_incorrect_id_to_the_discussions_id_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getDiscussionWithHttpInfo(INCORRECT_ID);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I GET a comment which not exist to the /discussions/id/comments/idComment endpoint$")
    public void i_GET_a_comment_which_not_exist_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.getCommentWithHttpInfo(lastIdDiscussion,INCORRECT_ID);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I PUT a comment which not exist to the /discussions/id/comments/idComment endpoint$")
    public void i_PUT_a_comment_which_not_exist_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            comment.setComment("test");
            lastApiResponse = api.updateCommentWithHttpInfo(lastIdDiscussion,INCORRECT_ID,comment);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }


    @When("^I PUT it to the /discussions/id/comments/idComment endpoint$")
    public void i_PUT_it_payload_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            comment.setComment("test");
            lastApiResponse = api.updateCommentWithHttpInfo(lastIdDiscussion,lastIdComment,comment);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I DELETE it to the /discussions/id endpoint$")
    public void i_DELETE_it_to_the_discussions_id_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.delDiscussionWithHttpInfo(lastIdDiscussion);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        i_receive_a_status_code(204);
    }

    @When("^I DELETE it to the /discussions/id/comments/idComment endpoint$")
    public void i_DELETE_it_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse = api.delCommentWithHttpInfo(lastIdDiscussion,lastIdComment);
            assertNotNull(lastApiResponse);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        i_receive_a_status_code(204);
    }

}
