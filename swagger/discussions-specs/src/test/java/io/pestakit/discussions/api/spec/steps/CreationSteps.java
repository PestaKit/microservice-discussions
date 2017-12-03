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
import io.pestakit.discussions.api.dto.OutputDiscussion;

import io.pestakit.discussions.api.spec.helpers.Environment;
import org.joda.time.DateTime;

import java.util.Date;
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
    private OutputComment outputcomment;
    private Object location;

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
            location = headers.get("Location").get(0);
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

    @When("^I GET it to the /discussions/id endpoint$")
    public void i_GET_it_to_the_discussions_endpoint() throws Throwable {
        try {
            lastApiResponse = api.getDiscussionsWithHttpInfo();
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

   /* @When("^I POST a correct discussion payload to the /discussions endpoint$")
    public void i_POST_a_correct_discussion_payload_to_the_discussions_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }*/

    @Given("^I have a discussion$")
    public void i_have_a_discussion() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        i_POST_it_to_the_discussions_endpoint();
        //i_receive_a_status_code(201);
    }

    @When("^I POST the InputComment payload to the /discussions/id/comments endpoint$")
    public void i_POST_the_InputComment_payload_to_the_discussions_id_comments_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            String tmp = location.toString();
            lastIdDiscussion = Integer.parseInt(tmp.substring(tmp.lastIndexOf('/') + 1));
            lastApiResponse = api.createCommentWithHttpInfo(lastIdDiscussion,comment);
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


    @When("^I GET the OutputComment payload to the /discussions/id/comments/idComment endpoint$")
    public void i_GET_the_OutputComment_payload_to_the_discussions_id_comments_idComment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            String tmp = location.toString();
            lastIdComment = Integer.parseInt(tmp.substring(tmp.lastIndexOf('/') + 1));
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

}
