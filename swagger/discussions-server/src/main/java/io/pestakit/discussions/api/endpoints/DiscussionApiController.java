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


    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ReportRepository reportRepository;

    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createComment(@ApiParam(value = "id of discussion",required=true ) @PathVariable("idDiscussion") Integer idDiscussion,
                                                @ApiParam(value = "" ,required=true ) @RequestBody InputComment comment) {

        UserProfile profile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getDetails();

        CommentEntity newComment = new CommentEntity(comment, profile.getUsername());

        /* Si le champs related est manquant ou vide : Réponse à la discusion
         * Si le champ n'est pas vide : Réponse à un commentaire */
        String fatherURL = comment.getFatherUrl();
        if(fatherURL== null || fatherURL.isEmpty()){
            newComment.setFatherUrl("localhost/discussions/" + idDiscussion);
        } else {
            newComment.setFatherUrl(fatherURL);
        }

        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);

        if(discussion == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        discussion.addComment(newComment);
        commentRepository.save(newComment);

        int idComment = newComment.getIdComment();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{idComment}")
                .buildAndExpand(newComment.getIdComment()).toUri();

        System.out.print(location.toString());

        return ResponseEntity.created(location).build();


    }
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createDiscussion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody InputDiscussion discussion) {

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

        if(comment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(comment.getOutputComment(idDiscussion));
    }

    public ResponseEntity<OutputDiscussion> getDiscussion(@ApiParam(value = "id of discussions",required=true ) @PathVariable("idDiscussion") Integer idDiscussion){
        DiscussionEntity discussion = discussionRepository.findOne(idDiscussion);
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



    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> updateComment(@ApiParam(value = "id of the discussion", required = true) @PathVariable("idDiscussion") Integer idDiscussion,
                                              @ApiParam(value = "id of comment", required = true) @PathVariable("idComment") Integer idComment,
                                              @ApiParam(value = "comment to be updated", required = true) @RequestBody InputComment comment){
        CommentEntity commentToBeUpdated = commentRepository.findOne(idComment);
        if(commentToBeUpdated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /* Vérification : Seul le user qui envoie le msg peut le modifier */
        UserProfile profile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!profile.getUsername().equals(commentToBeUpdated.getAuthor())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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

        /* Vérification : Seul le user qui envoie le msg peut le supprimer */
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
        /* Vérification : Seul le user qui créer la discusion peut supprimer tout les msgs */
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

        /* Vérification : Un seul vote par message et par personne */
        for(VoteEntity voteAnalyse : commentToBeVoted.getVotes()){
            if(profile.getUsername().equals(voteAnalyse.getAuthor())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

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

        /* Vérification : Un seul repport par message et par personne */
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