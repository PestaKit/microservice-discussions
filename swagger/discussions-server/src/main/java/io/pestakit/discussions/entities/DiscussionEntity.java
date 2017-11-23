package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.InputComment;
import io.pestakit.discussions.api.model.InputDiscussion;

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

  @OneToMany(
          mappedBy = "discussion",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<CommentEntity> comments = new ArrayList<>();


  public DiscussionEntity(){
  }

  public DiscussionEntity(InputDiscussion discussion){
    this.idArticle = discussion.getIdArticle();
    this.comments = discussion.getComments();
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
      comment.setDiscussion(this);
      comments.add(comment);
  }

  public void removeComment(CommentEntity commentToRemove){
    comments.remove(commentToRemove);
    commentToRemove.setDiscussion(null);
  }

}
