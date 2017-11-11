package io.pestakit.discussions.entities;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DiscussionEntity
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-11T13:55:40.737Z")

public class DiscussionEntity {
  @JsonProperty("id_discussion")
  private String idDiscussion = null;

  @JsonProperty("id_article")
  private String idArticle = null;

  public DiscussionEntity idDiscussion(String idDiscussion) {
    this.idDiscussion = idDiscussion;
    return this;
  }

   /**
   * Get idDiscussion
   * @return idDiscussion
  **/
  @ApiModelProperty(value = "")


  public String getIdDiscussion() {
    return idDiscussion;
  }

  public void setIdDiscussion(String idDiscussion) {
    this.idDiscussion = idDiscussion;
  }

  public DiscussionEntity idArticle(String idArticle) {
    this.idArticle = idArticle;
    return this;
  }

   /**
   * Get idArticle
   * @return idArticle
  **/
  @ApiModelProperty(value = "")


  public String getIdArticle() {
    return idArticle;
  }

  public void setIdArticle(String idArticle) {
    this.idArticle = idArticle;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiscussionEntity discussionEntity = (DiscussionEntity) o;
    return Objects.equals(this.idDiscussion, discussionEntity.idDiscussion) &&
        Objects.equals(this.idArticle, discussionEntity.idArticle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDiscussion, idArticle);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiscussionEntity {\n");
    
    sb.append("    idDiscussion: ").append(toIndentedString(idDiscussion)).append("\n");
    sb.append("    idArticle: ").append(toIndentedString(idArticle)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

