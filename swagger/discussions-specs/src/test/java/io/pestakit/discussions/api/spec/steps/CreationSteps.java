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
        discussion = new io.pestakit.discussions.api.dto.Discussion();
    }

    @When("^I POST it to the /discussion endpoint$")
    public void i_POST_it_to_the_discussions_endpoint() throws Throwable {
        try {
            lastApiResponse = api.discussionIdDiscussionCommentPostWithHttpInfo(discussion.getIdDiscussion(),comment);
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
        assertEquals(201, lastStatusCode);
    }

}
