package io.pestakit.discussions.api.endpoints;

import io.pestakit.discussions.api.model.Comment;
import io.pestakit.discussions.api.CommentApi;

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

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-11T13:55:40.737Z")

@Controller
public class CommentApiController implements CommentApi {



    public ResponseEntity<Void> commentPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Comment comment) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
