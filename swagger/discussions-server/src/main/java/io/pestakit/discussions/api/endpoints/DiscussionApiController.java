/**
DiscussionApiControlleur
*/
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
import io.pestakit.users.security.UserProfile;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T13:37:16.495Z")

@Controller
public class DiscussionApiController implements DiscussionsApi {


    // no need setters on these parameters
    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ReportRepository reportRepository;

    // before proceed this operation (create a comment) user must be authentificated, without this condition, user will receive an error 
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                                @ApiParam(value = "" ,required=true ) @RequestBody InputComment comment) {

        // recuperation info about the authors : GetUserDetails
        UserProfile profile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getDetails();

        // creation of a new commentEntity 
        CommentEntity newComment = new CommentEntity(comment, profile.getUsername());

        /* If this property is empty or not set: it's a new comment
         * if  this property is not empty, this comment will be related to another comment
         */
        String fatherURL = comment.getFatherUrl();
        if(fatherURL== null || fatherURL.isEmpty()){
            newComment.setFatherUrl("localhost/discussions/" + idDiscussion);
        } else {
            newComment.setFatherUrl(fatherURL);
        }

        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);

        // if the discussion doesn't existe, it will give an error saying that the discussion wasn't found
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // add and save the comment
        discussion.addComment(newComment);
        commentRepository.save(newComment);

        int idComment = newComment.getIdComment();

        // construction of the URl of this comment using the ID f the comment
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(newComment.getIdComment()).toUri();

        System.out.print(location.toString());

        return ResponseEntity.created(location).build();


    }
    // chech if the user is authentificated in order to allod his to create a discussion
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createDiscussion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody InputDiscussion discussion) {

        // same logis as for creation of the comment
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();

        /* L'id de l'article doit être setter et doit être positif */
        if(discussion.getIdArticle() == null || discussion.getIdArticle() < 0 ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        /* Une seule discussion par article est autorisé */
        for (DiscussionEntity discussionInDb : discussionRepository.findAll()){
            if(discussionInDb.getIdArticle() == discussion.getIdArticle()){
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }
        }

        /* Création de l'entité sur la base de la DTO + le username de l'utilisateur (autheur) */
        DiscussionEntity discu = new DiscussionEntity(discussion,profile.getUsername());
        /* Enregistrement dans la bdd */
        discussionRepository.save(discu);

        /* Stockera l'id de la disucssion */
        int id = discu.getIdDiscussion();

        /* Permet de retourner l'url de l'objet discussion */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(discu.getIdDiscussion()).toUri();

        return ResponseEntity.created(location).build();

    }

    @Override
    public ResponseEntity<OutputComment> getComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                       @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment) {
        CommentEntity comment = commentRepository.findOne(idComment);

        // impossible to get a comment which doesn't exist       
        if(comment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(comment.getOutputComment(idDiscussion));
    }

    
    public ResponseEntity<OutputDiscussion> getDiscussion(@ApiParam(value = "id of discussions",required=true ) @PathVariable("idDiscussion") Integer idDiscussion){
        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);
        // impossible to get a discussion which doesn't exist       
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(discussion.getOutputDiscussion());
    }

    // if there is no discussion, user will recieve an empty table, no error message
    public ResponseEntity<List<OutputDiscussion>> getDiscussions() {
        List<OutputDiscussion> discussions = new ArrayList<>();
        for (DiscussionEntity discussion : discussionRepository.findAll()) {
            discussions.add(discussion.getOutputDiscussion());
        }
        return ResponseEntity.ok(discussions);
    }



    // need to check that the user which wants to update the comment is the autor of this comment
    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> updateComment(@ApiParam(value = "id of the discussion", required = true) @PathVariable("idDiscussion") Integer idDiscussion,
                                              @ApiParam(value = "id of comment", required = true) @PathVariable("idComment") Integer idComment,
                                              @ApiParam(value = "comment to be updated", required = true) @RequestBody InputComment comment){
        CommentEntity commentToBeUpdated = commentRepository.findOne(idComment);
        // cannot update a comment which doesn't exist
        if(commentToBeUpdated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /* Only the author can modify the comment */
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!profile.getUsername().equals(commentToBeUpdated.getAuthor())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // save a new comment
        commentToBeUpdated.setComment(comment.getComment());
        commentRepository.save(commentToBeUpdated);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> delComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                              @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment){
        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CommentEntity commentEntity = commentRepository.findOne(idComment);
        if(commentEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        /* Verification if the user is the author of this comment, he can delete it, if not --> error */
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!profile.getUsername().equals(commentEntity.getAuthor())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        for(CommentEntity reply : commentRepository.findAll()){

            if(reply.getFatherUrl().equals("localhost/" + discussion.getIdDiscussion() + "/comments/" + commentEntity.getIdComment())){
                discussion.removeComment(reply);
                commentRepository.delete(reply.getIdComment());
            }
        }
        discussion.removeComment(commentEntity);
        commentRepository.delete(idComment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> delDiscussion(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion){
        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);
        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /*Verification if the user is the author of this discussion, he can delete it , if not --> error * */
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!profile.getUsername().equals(discussion.getAuthor())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        discussionRepository.delete(discussion.getIdDiscussion());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Object> voteComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                              @ApiParam(value = "id of discussion",required=true ) @PathVariable("idComment") Integer idComment,
                                              @ApiParam(value = "" ,required=true ) @RequestBody InputVote vote){
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();

        VoteEntity newVote = new VoteEntity(vote,profile.getUsername());
        CommentEntity commentToBeVoted = commentRepository.findOne(idComment);

        if(commentToBeVoted == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        /* Verification: only one vote per comment per user*/
        for(VoteEntity voteAnalyse : commentToBeVoted.getVotes()){
            if(profile.getUsername().equals(voteAnalyse.getAuthor())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        // save this vote
        commentToBeVoted.addVote(newVote);
        voteRepository.save(newVote);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(commentToBeVoted.getIdComment()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Object> reportComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                                @ApiParam(value = "id of comment",required=true ) @PathVariable("idComment") Integer idComment,
                                                @ApiParam(value = "" ,required=true ) @RequestBody InputReport report) {

        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();

        ReportEntity newReport = new ReportEntity(report, profile.getUsername());
        CommentEntity commentToBeReported = commentRepository.findOne(idComment);

        if(commentToBeReported == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        /* Verification: only one report per comment per user*/
        for(ReportEntity reportAnalyse : commentToBeReported.getReports()){
            if(profile.getUsername().equals(reportAnalyse.getAuthor())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        commentToBeReported.addReport(newReport);
        reportRepository.save(newReport);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
