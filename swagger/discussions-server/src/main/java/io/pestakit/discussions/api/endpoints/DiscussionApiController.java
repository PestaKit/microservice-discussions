package io.pestakit.discussions.api.endpoints;

import io.pestakit.discussions.api.DiscussionsApi;

import io.pestakit.discussions.api.model.*;
import io.pestakit.discussions.entities.CommentEntity;
import io.pestakit.discussions.entities.DiscussionEntity;
import io.pestakit.discussions.entities.ReportEntity;
import io.pestakit.discussions.entities.VoteEntity;
import io.pestakit.discussions.repositories.CommentRepository;
import io.pestakit.discussions.repositories.DiscussionRepository;
import io.pestakit.discussions.repositories.ReportRepository;
import io.pestakit.discussions.repositories.VoteRepository;
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

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ReportRepository reportRepository;

    public ResponseEntity<Object> createComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                                @ApiParam(value = "" ,required=true ) @RequestBody InputComment comment) {

        CommentEntity newComment = new CommentEntity(comment);
        DiscussionEntity discussion = discussionRepository.findOne(id);

        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        discussion.addComment(newComment);
        commentRepository.save(newComment);

        int idComment = newComment.getIdComment();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(newComment.getIdComment()).toUri();

        return ResponseEntity.created(location).build();


    }

    public ResponseEntity<Object> createDiscussion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody InputDiscussion discussion) {

        DiscussionEntity discu = new DiscussionEntity(discussion);
        discussionRepository.save(discu);

        int id = discu.getIdDiscussion();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(discu.getIdDiscussion()).toUri();

        return ResponseEntity.created(location).build();

    }

    @Override
    public ResponseEntity<OutputComment> getComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                       @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment) {
        CommentEntity comment = commentRepository.findOne(idComment);

        if(comment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(comment.getOutputComment(id));
    }


    public ResponseEntity<OutputDiscussion> getDiscussion(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") Integer id){
        DiscussionEntity discussion = discussionRepository.findOne(id);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(discussion.getOutputDiscussion());
    }


    public ResponseEntity<List<OutputDiscussion>> getDiscussions() {
        List<OutputDiscussion> discussions = new ArrayList<>();
        for (DiscussionEntity discussion : discussionRepository.findAll()) {
            discussions.add(discussion.getOutputDiscussion());
        }
        return ResponseEntity.ok(discussions);
    }

    @Override
    public ResponseEntity<Object> reportComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                                @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment,
                                                @ApiParam(value = "" ,required=true ) @RequestBody InputReport report) {

        ReportEntity newReport = new ReportEntity(report);
        CommentEntity commentToBeReported = commentRepository.findOne(idComment);
        if(commentToBeReported == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentToBeReported.addReport(newReport);
        reportRepository.save(newReport);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> updateComment(@ApiParam(value = "id of the discussion", required = true) @PathVariable("id") Integer id,
                                              @ApiParam(value = "id of comment", required = true) @PathVariable("idComment") Integer idComment,
                                              @ApiParam(value = "comment to be updated", required = true) @RequestBody InputComment comment){
        CommentEntity commentToBeUpdated = commentRepository.findOne(idComment);
        if(commentToBeUpdated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentToBeUpdated.setComment(comment.getComment());
        commentRepository.save(commentToBeUpdated);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<OutputComment>> getComments(@ApiParam(value = "id of discussions",required=true ) @PathVariable("id") Integer id) {
        List<OutputComment> comments = new ArrayList<>();
        DiscussionEntity discussion = discussionRepository.findOne(id);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for(CommentEntity comment : discussion.getComments()){
            comments.add(comment.getOutputComment(id));
        }
        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<Void> delComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                              @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment){
        DiscussionEntity discussion = discussionRepository.findOne(id);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CommentEntity commentEntity = commentRepository.findOne(idComment);
        if(commentEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        discussion.removeComment(commentEntity);
        commentRepository.delete(idComment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> delComments(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id){
        DiscussionEntity discussion = discussionRepository.findOne(id);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CommentEntity> commentToRemove = new ArrayList<>(discussion.getComments());
        for(CommentEntity comment : commentToRemove){
            discussion.removeComment(comment);
            commentRepository.delete(comment.getIdComment());
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delDiscussion(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id){
        DiscussionEntity discussion = discussionRepository.findOne(id);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        discussionRepository.delete(discussion.getIdDiscussion());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> voteComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("id") Integer id,
                                              @ApiParam(value = "id of discussion",required=true ) @PathVariable("idComment") Integer idComment,
                                              @ApiParam(value = "" ,required=true ) @RequestBody InputVote vote){

        VoteEntity newVote = new VoteEntity(vote);
        CommentEntity commentToBeUpdated = commentRepository.findOne(idComment);

        if(commentToBeUpdated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentToBeUpdated.addVote(newVote);
        voteRepository.save(newVote);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(commentToBeUpdated.getIdComment()).toUri();

        return ResponseEntity.created(location).build();

    }


}