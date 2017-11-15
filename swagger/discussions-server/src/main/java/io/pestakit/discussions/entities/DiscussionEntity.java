package io.pestakit.discussions.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class DiscussionEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String idDiscussion;

  private String idArticle;


  public String getIdDiscussion() {
    return idDiscussion;
  }

  public void setIdDiscussion(String idDiscussion) {
    this.idDiscussion = idDiscussion;
  }

  public String getIdArticle() {
    return idArticle;
  }

  public void setIdArticle(String idArticle) {
    this.idArticle = idArticle;
  }
}
