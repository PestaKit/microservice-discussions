package io.pestakit.discussions.api.endpoints;

import io.pestakit.discussions.api.model.Comment;
import io.pestakit.discussions.api.model.Discussion;
import io.pestakit.discussions.api.DiscussionsApi;

import io.pestakit.discussions.api.model.OutputDiscussion;
import io.pestakit.discussions.entities.CommentEntity;
import io.pestakit.discussions.entities.DiscussionEntity;
import io.pestakit.discussions.repositories.CommentRepository;
import io.pestakit.discussions.repositories.DiscussionRepository;
import io.swagger.annotations.*;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    public ResponseEntity<Object> createComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                                @ApiParam(value = "" ,required=true ) @RequestBody Comment comment) {

        CommentEntity newCommentEntity = toCommentEntity(comment);
        DiscussionEntity discussion = discussionRepository.findOne(id);
        discussion.addComment(newCommentEntity);
        newCommentEntity.setDiscussion(discussion);
        commentRepository.save(newCommentEntity);

        int idComment = newCommentEntity.getIdComment();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(newCommentEntity.getIdComment()).toUri();

        return ResponseEntity.created(location).build();


    }

    public ResponseEntity<Object> createDiscussion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Discussion discussion) {

        DiscussionEntity newDiscussionEntity = toDiscussionsEntity(discussion);
        for (Comment comment : discussion.getComments()){
            newDiscussionEntity.addComment(toCommentEntity(comment));
        }
        discussionRepository.save(newDiscussionEntity);

        int id = newDiscussionEntity.getIdDiscussion();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDiscussionEntity.getIdDiscussion()).toUri();

        return ResponseEntity.created(location).build();

    }

    @Override
    public ResponseEntity<Comment> getComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                       @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment) {
        CommentEntity commentEntity = commentRepository.findOne(idComment);
        return ResponseEntity.ok(toComment(commentEntity));
    }


    ResponseEntity<OutputDiscussion> getDiscussion(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") Integer id){
        OutputDiscussion discussionEntity = discussionRepository.findOne(id);
        return ResponseEntity.ok(toDiscussion(discussionEntity));
    }

    public ResponseEntity<List<OutputDiscussion>> getDiscussions() {
        List<OutputDiscussion> discussions = new ArrayList<>();
        for (DiscussionEntity discussionEntity : discussionRepository.findAll()) {
            discussions.add(toDiscussion(discussionEntity));
        }
        return ResponseEntity.ok(discussions);
    }

    public ResponseEntity<List<Comment>> getComments(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") Integer id) {
        List<Comment> comments = new ArrayList<>();
        DiscussionEntity discussion = discussionRepository.findOne(id);
        for(CommentEntity comment : discussion.getComments()){
            comments.add(toComment(comment));
        }
        return ResponseEntity.ok(comments);
    }



    private DiscussionEntity toDiscussionsEntity(Discussion discussion) {
        DiscussionEntity entity = new DiscussionEntity();
        entity.setIdArticle(discussion.getIdArticle());

        return entity;
    }

    @Override
    public ResponseEntity<Void> delComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                              @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment){
        DiscussionEntity discussion = discussionRepository.findOne(id);
        CommentEntity commentEntity = commentRepository.findOne(idComment);
        discussion.removeComment(commentEntity);
        commentRepository.delete(idComment);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delComments(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id){
        DiscussionEntity discussion = discussionRepository.findOne(id);

        for(CommentEntity comment : discussion.getComments()){
            delComment(discussion.getIdDiscussion(),comment.getIdComment());
            discussion.removeComment(comment);
        }
        return ResponseEntity.ok().build();
    }



    private Discussion toDiscussion(DiscussionEntity entity) {
        Discussion discussion = new Discussion();
        List<Comment> comments = new ArrayList<>();

        discussion.setIdDiscussion(entity.getIdDiscussion());
        discussion.setIdArticle(entity.getIdArticle());

        for(CommentEntity commentEntity : entity.getComments()){
            comments.add(toComment(commentEntity));
        }
        discussion.setComments(comments);

        return discussion;
    }


    private CommentEntity toCommentEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setAuthor(comment.getAuthor());
        entity.setDate(comment.getDate().toDate());
        entity.setDownScore(comment.getDownScore());
        entity.setFatherUrl(comment.getFatherUrl());
        entity.setUpScore(comment.getUpScore());
        entity.setReport(comment.getReport());
        entity.setComment(comment.getComment());
        //entity.setIdComment(comment.getIdComment());
        return entity;
    }

    private Comment toComment(CommentEntity entity) {
        Comment comment = new Comment();

        comment.setFatherUrl(entity.getFatherUrl());
        comment.setAuthor(entity.getAuthor());
        comment.setComment(entity.getComment());
        comment.setDate(new DateTime(entity.getDate()));
        comment.setDownScore(entity.getDownScore());
        comment.setUpScore(entity.getUpScore());
        comment.setReport(entity.getReport());
        comment.setIdDiscussion(entity.getDiscussion().getIdDiscussion());
        comment.setIdComment(entity.getIdComment());

        return comment;
    }



}