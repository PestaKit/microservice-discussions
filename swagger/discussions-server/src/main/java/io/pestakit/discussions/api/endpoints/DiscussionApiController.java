package io.pestakit.discussions.api.endpoints;

import io.pestakit.discussions.api.model.Comment;
import io.pestakit.discussions.api.model.Discussion;
import io.pestakit.discussions.api.DiscussionApi;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T13:37:16.495Z")

@Controller
public class DiscussionApiController implements DiscussionApi {



    public ResponseEntity<Object> discussionIdDiscussionCommentIdCommentGet(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id_discussion") String idDiscussion,
                                                                            @ApiParam(value = "id of comment",required=true ) @PathVariable("id_comment") String idComment) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<Void> discussionIdDiscussionCommentPost(@ApiParam(value = "",required=true ) @PathVariable("id_discussion") String idDiscussion,
                                                                  @ApiParam(value = "" ,required=true )  @Valid @RequestBody Comment comment) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Comment>> discussionIdGet(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") String id) {
        // do some magic!
        return new ResponseEntity<List<Comment>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> discussionPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Discussion comment) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}