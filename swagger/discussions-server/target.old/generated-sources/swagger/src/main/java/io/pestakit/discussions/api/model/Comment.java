package io.pestakit.discussions.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.constraints.*;
/**
 * Comment
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-14T12:41:51.008+01:00")

public class Comment   {
  @JsonProperty("id_comment")
  private String idComment = null;

  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("like")
  private Integer like = null;

  @JsonProperty("dislike")
  private Integer dislike = null;

  @JsonProperty("date")
  private DateTime date = null;

  @JsonProperty("author")
  private String author = null;

  @JsonProperty("report")
  private Boolean report = null;

  public Comment idComment(String idComment) {
    this.idComment = idComment;
    return this;
  }

   /**
   * Get idComment
   * @return idComment
  **/
  @ApiModelProperty(value = "")
  public String getIdComment() {
    return idComment;
  }

  public void setIdComment(String idComment) {
    this.idComment = idComment;
  }

  public Comment comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(value = "")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Comment like(Integer like) {
    this.like = like;
    return this;
  }

   /**
   * Get like
   * @return like
  **/
  @ApiModelProperty(value = "")
  public Integer getLike() {
    return like;
  }

  public void setLike(Integer like) {
    this.like = like;
  }

  public Comment dislike(Integer dislike) {
    this.dislike = dislike;
    return this;
  }

   /**
   * Get dislike
   * @return dislike
  **/
  @ApiModelProperty(value = "")
  public Integer getDislike() {
    return dislike;
  }

  public void setDislike(Integer dislike) {
    this.dislike = dislike;
  }

  public Comment date(DateTime date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")
  public DateTime getDate() {
    return date;
  }

  public void setDate(DateTime date) {
    this.date = date;
  }

  public Comment author(String author) {
    this.author = author;
    return this;
  }

   /**
   * Get author
   * @return author
  **/
  @ApiModelProperty(value = "")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Comment report(Boolean report) {
    this.report = report;
    return this;
  }

   /**
   * Get report
   * @return report
  **/
  @ApiModelProperty(value = "")
  public Boolean getReport() {
    return report;
  }

  public void setReport(Boolean report) {
    this.report = report;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return Objects.equals(this.idComment, comment.idComment) &&
        Objects.equals(this.comment, comment.comment) &&
        Objects.equals(this.like, comment.like) &&
        Objects.equals(this.dislike, comment.dislike) &&
        Objects.equals(this.date, comment.date) &&
        Objects.equals(this.author, comment.author) &&
        Objects.equals(this.report, comment.report);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idComment, comment, like, dislike, date, author, report);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comment {\n");
    
    sb.append("    idComment: ").append(toIndentedString(idComment)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    like: ").append(toIndentedString(like)).append("\n");
    sb.append("    dislike: ").append(toIndentedString(dislike)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    report: ").append(toIndentedString(report)).append("\n");
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

