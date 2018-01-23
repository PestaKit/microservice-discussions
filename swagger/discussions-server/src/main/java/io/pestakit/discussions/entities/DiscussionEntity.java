package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.*;

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
  private String author;

  @OneToMany(
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<CommentEntity> comments = new ArrayList<>();


  public DiscussionEntity(){
  }

  public DiscussionEntity(InputDiscussion discussion, String author){
    this.idArticle = discussion.getIdArticle();
    this.author = author;
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

  public void setAuthor(String authorUsername){
    this.author = authorUsername;
  }

  public String getAuthor(){
    return this.author;
  }

  public OutputDiscussion getOutputDiscussion(){
    OutputDiscussion outputDiscussion = new OutputDiscussion();

    OutputDiscussionLinks links = new OutputDiscussionLinks();
    links.setSelf("http://exemple.com/discussions/" + this.idDiscussion);
    links.setRelated("http://exemple.com/articles/" + this.idArticle);

    outputDiscussion.setLinks(links);

    List<OutputComment> outComents = new ArrayList<>();
    for(CommentEntity comms : this.comments){
      outComents.add(comms.getOutputComment(this.idDiscussion));

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
