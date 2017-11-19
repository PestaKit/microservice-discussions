package io.pestakit.discussions.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity(name = "COMMENTS")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;

    private String author;
    private String comment;
    private String fatherUrl;
    private DateTime date;
    private Boolean report;
    private int upScore;
    private int downScore;

    public Long getIdComment() {
        return idComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFatherUrl() {
        return fatherUrl;
    }

    public void setFatherUrl(String fatherUrl) {
        this.fatherUrl = fatherUrl;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public boolean getReport() {
        return this.report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public int getUpScore() {
        return upScore;
    }

    public void setUpScore(int upScore) {
        this.upScore = upScore;
    }

    public int getDownScore() {
        return downScore;
    }

    public void setDownScore(int downScore) {
        this.downScore = downScore;
    }

}
