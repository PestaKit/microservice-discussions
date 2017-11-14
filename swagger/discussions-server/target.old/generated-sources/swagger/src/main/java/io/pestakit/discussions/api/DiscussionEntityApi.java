package io.pestakit.discussions.api;

import io.pestakit.discussions.api.model.Comment;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-14T12:41:51.008+01:00")

@Api(value = "discussionEntity", description = "the discussionEntity API")
public interface DiscussionEntityApi {

    @ApiOperation(value = "Get a comment", notes = "", response = Object.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "comment", response = Object.class),
        @ApiResponse(code = 404, message = "comment id doesn't exist", response = Object.class) })
    @RequestMapping(value = "/discussionEntity/{id_discussion}/comment/{id_comment}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> discussionEntityIdDiscussionCommentIdCommentGet(@ApiParam(value = "id of discussionEntity",required=true ) @PathVariable("idDiscussion") String idDiscussion,
        @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") String idComment);


    @ApiOperation(value = "Get a discussionEntity", notes = "", response = Comment.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "list if discussionEntity", response = Comment.class),
        @ApiResponse(code = 404, message = "discussionEntity id doesn't exist", response = Comment.class) })
    @RequestMapping(value = "/discussionEntity/{id}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Comment>> discussionEntityIdGet(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") String id);

}
