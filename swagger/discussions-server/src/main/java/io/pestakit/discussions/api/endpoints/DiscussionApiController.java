package io.pestakit.discussions.api.endpoints;

import io.pestakit.discussions.api.model.Comment;
import io.pestakit.discussions.api.model.Discussion;
import io.pestakit.discussions.api.DiscussionsApi;

import io.pestakit.discussions.entities.CommentEntity;
import io.pestakit.discussions.entities.DiscussionEntity;
import io.pestakit.discussions.repositories.CommentRepository;
import io.pestakit.discussions.repositories.DiscussionRepository;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T13:37:16.495Z")

@Controller
public class DiscussionApiController implements DiscussionsApi {


    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    CommentRepository commentRepository;

    public ResponseEntity<Object> createComment(@ApiParam(value = "",required=true ) @PathVariable("id_discussion") String idDiscussion,
                                                @ApiParam(value = "" ,required=true )  @Valid @RequestBody Comment comment) {


        CommentEntity newCommentEntity = toCommentEntity(comment);
        commentRepository.save(newCommentEntity);
        String id = newCommentEntity.getIdComment();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newCommentEntity.getIdComment()).toUri();

        return ResponseEntity.created(location).build();


    }

    public ResponseEntity<Object> createDiscussion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Discussion discussion) {


        DiscussionEntity newDiscussionEntity = toDiscussionsEntity(discussion);
        discussionRepository.save(newDiscussionEntity);
        String id = newDiscussionEntity.getIdDiscussion();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDiscussionEntity.getIdDiscussion()).toUri();

        return ResponseEntity.created(location).build();

    }

    @Override
    public ResponseEntity<Object> getComment(@ApiParam(value = "id of discussion", required = true) @PathVariable("idDiscussion") String idDiscussion, @ApiParam(value = "id of comment", required = true) @PathVariable("idComment") String idComment) {
        return null;
    }


    public ResponseEntity<Discussion> getDiscussion(@ApiParam(value = "id of discussions", required = true) @PathVariable("id") String id) {
        DiscussionEntity discussionEntity = discussionRepository.findByIdDiscussion(id);
        return ResponseEntity.ok(toDiscussion(discussionEntity));
    }

    public ResponseEntity<List<Discussion>> getDiscussions() {
        List<Discussion> discussions = new ArrayList<>();
        for (DiscussionEntity discussionEntity : discussionRepository.findAll()) {
            discussions.add(toDiscussion(discussionEntity));
        }
        return ResponseEntity.ok(discussions);
    }

    public ResponseEntity<List<Comment>> getComments() {
        List<Comment> comments = new ArrayList<>();
        for (CommentEntity commentEntity : commentRepository.findAll()){
            comments.add(toComment(commentEntity));
        }
        return ResponseEntity.ok(comments);
    }


    private DiscussionEntity toDiscussionsEntity(Discussion discussion) {
        DiscussionEntity entity = new DiscussionEntity();
        entity.setIdArticle(discussion.getIdArticle());

        return entity;
    }

    private Discussion toDiscussion(DiscussionEntity entity) {
        Discussion discussion = new Discussion();
        discussion.setIdDiscussion(entity.getIdDiscussion());
        discussion.setIdArticle(entity.getIdArticle());

        return discussion;
    }


    private CommentEntity toCommentEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setAuthor(comment.getAuthor());
        entity.setDate(comment.getDate());
        entity.setDislike(comment.getDislike());
        entity.setFatherUrl(comment.getFatherUrl());
        entity.setIdComment(comment.getIdComment());
        entity.setLike(comment.getLike());
        entity.setReport(comment.getReport());
        entity.setComment(comment.getComment());

        return entity;
    }

    private Comment toComment(CommentEntity entity) {
        Comment comment = new Comment();
        comment.setIdComment(entity.getIdComment());
        comment.setFatherUrl(entity.getFatherUrl());
        comment.setAuthor(entity.getAuthor());
        comment.setComment(entity.getComment());
        comment.setDate(entity.getDate());
        comment.setDislike(entity.getDislike());
        comment.setLike(entity.getLike());
        comment.setReport(entity.isReport());



        return comment;
    }


}