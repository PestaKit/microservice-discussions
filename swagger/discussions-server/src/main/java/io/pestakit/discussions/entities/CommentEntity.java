package io.pestakit.discussions.entities;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idComment;
    private  Boolean report;
    private String comment;
    private DateTime date;
    private String author;
    private int like;
    private int dislike;
    private String fatherUrl;

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public boolean isReport() {
        return this.report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public String getFatherUrl() {
        return fatherUrl;
    }

    public void setFatherUrl(String fatherUrl) {
        this.fatherUrl = fatherUrl;
    }




}
