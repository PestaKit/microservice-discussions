package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.InputComment;
import io.pestakit.discussions.api.model.InputDiscussion;
import io.pestakit.discussions.api.model.OutputComment;
import io.pestakit.discussions.api.model.OutputDiscussion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity(name = "DISCUSSIONS")
public class DiscussionEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idDiscussion;
  private int idArticle;
/*
  @OneToMany(
          mappedBy = "discussion",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  */
  @OneToMany(
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<CommentEntity> comments = new ArrayList<>();


  public DiscussionEntity(){
  }

  public DiscussionEntity(InputDiscussion discussion){
    this.idArticle = discussion.getIdArticle();
    for(InputComment InComment : discussion.getComments()){
      comments.add(new CommentEntity(InComment));
    }
  }

  public int getIdDiscussion() {
    return idDiscussion;
  }

  public int getIdArticle() {
    return idArticle;
  }

  public void setIdArticle(int idArticle) {
    this.idArticle = idArticle;
  }


  public List<CommentEntity> getComments() {
    return comments;
  }

  public void setComments(List<CommentEntity> comments){
    this.comments = comments;
  }

  public void addComment(CommentEntity comment){
      comments.add(comment);
  }

  public void removeComment(CommentEntity commentToRemove){
    comments.remove(commentToRemove);
  }

  public OutputDiscussion getOutputDiscussion(){
    OutputDiscussion outputDiscussion = new OutputDiscussion();
    outputDiscussion.setIdArticle(this.idArticle);
    outputDiscussion.setUrlDiscussion(String.valueOf(idDiscussion));

    List<OutputComment> outComents = new ArrayList<>();
    for(CommentEntity comms : this.comments){
      outComents.add(comms.getOutputComment());

    }
    outputDiscussion.setComments(outComents);
    return outputDiscussion;

  }

  public void removeAllComments(){
    for(CommentEntity comment : comments) {
      comments.remove(comment);
    }
  }

}
