package io.pestakit.discussions.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.discussions.ApiException;
import io.pestakit.discussions.ApiResponse;
import io.pestakit.discussions.api.DefaultApi;
import io.pestakit.discussions.api.dto.Comment;
import io.pestakit.discussions.api.dto.Discussion;

import io.pestakit.discussions.api.spec.helpers.Environment;
import org.joda.time.DateTime;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    Discussion discussion;
    Comment comment;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }


    @Given("^there is a Discussions server$")
    public void there_is_a_Discussions_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a discussion payload$")
    public void i_have_a_discussion_payload() throws Throwable {
        int idAr = 1;
        int idDis = 1;
        discussion = new io.pestakit.discussions.api.dto.Discussion();
        discussion.setIdArticle(1);
        discussion.setIdDiscussion(1);
    }

    @When("^I POST it to the /discussions endpoint$")
    public void i_POST_it_to_the_discussions_endpoint() throws Throwable {
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

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, lastStatusCode);
    }

    @Given("^I have a comment payload$")
    public void i_have_a_comment_payload() throws Throwable {
        comment = new io.pestakit.discussions.api.dto.Comment();
        comment.setIdComment(1);
        comment.setIdDiscussion(1);
        comment.setAuthor("sas");
        comment.setComment("comment");
        comment.setDate(new DateTime());
        comment.setDownScore(0);
        comment.setUpScore(0);
        comment.setFatherUrl("/1");
        comment.setReport(false);
    }

    @When("^I POST it to the /discussions/id/comments endpoint$")
    public void i_POST_it_to_the_comments_endpoint() throws Throwable {

        try {
            lastApiResponse = api.createCommentWithHttpInfo(comment.getIdDiscussion(),comment);
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
}
