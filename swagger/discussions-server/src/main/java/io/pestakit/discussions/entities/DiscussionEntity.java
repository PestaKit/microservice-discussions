/**
File which containts methods related to a discussion
*/
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

  //remove cascade 
  @OneToMany(
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<CommentEntity> comments = new ArrayList<>();


  //constructeur by default
  public DiscussionEntity(){
  }

  // constructeur by the id of the article and the author 
  public DiscussionEntity(InputDiscussion discussion, String author){
    this.idArticle = discussion.getIdArticle();
    this.author = author;
  }

  // getter of an ID of the discussion
  public int getIdDiscussion() {
    return idDiscussion;
  }
 // getter of an ID of the article
  public int getIdArticle() {
    return idArticle;
  }

   // setter of an ID of the article
  public void setIdArticle(int idArticle) {
    this.idArticle = idArticle;
  }


  // getter of all comments of the discussion
  public List<CommentEntity> getComments() {
    return comments;
  }
  

  // setter of all comment of the discussion
  public void setComments(List<CommentEntity> comments){
    this.comments = comments;
  }

  // add a comment to the discussion
  public void addComment(CommentEntity comment){
      comments.add(comment);
  }

  // delete a comment from the discussion
  public void removeComment(CommentEntity commentToRemove){
    comments.remove(commentToRemove);
  }

  // setter of the author's name 
  public void setAuthor(String authorUsername){
    this.author = authorUsername;
  }

  // getter of the author of the discussion
  public String getAuthor(){
    return this.author;
  }

  // getter of DTO discussion
  public OutputDiscussion getOutputDiscussion(){
    OutputDiscussion outputDiscussion = new OutputDiscussion();

    // new links to the discussion DTO , adding id of the discussion and id of the article
    OutputDiscussionLinks links = new OutputDiscussionLinks();
    links.setSelf("http://exemple.com/discussions/" + this.idDiscussion);
    links.setRelated("http://exemple.com/articles/" + this.idArticle);

    // set links to the discussion
    outputDiscussion.setLinks(links);

    //add comments to the discussion
    List<OutputComment> outComents = new ArrayList<>();
    for(CommentEntity comms : this.comments){
      outComents.add(comms.getOutputComment(this.idDiscussion));

    }
    // comment DTO
    outputDiscussion.setComments(outComents);
    return outputDiscussion;

  }

  // delete all comments of the discussion
  public void removeAllComments(){
    for(CommentEntity comment : comments) {
      comments.remove(comment);
    }
  }

}
