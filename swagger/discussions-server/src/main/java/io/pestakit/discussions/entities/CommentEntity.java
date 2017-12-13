package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.InputComment;
import io.pestakit.discussions.api.model.OutputComment;
import io.pestakit.discussions.api.model.OutputDiscussion;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity(name = "COMMENTS")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;

    private String author;
    private String comment;
    private String fatherUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();
    //private Boolean report = false;
    private ReportEntity reportEntity = new ReportEntity();
    private int upScore = 0;
    private int downScore = 0;


    public CommentEntity(InputComment comment){
        author = comment.getAuthor();
        this.comment = comment.getComment();
        fatherUrl = comment.getFatherUrl();
    }

    public CommentEntity(){
    }

    public CommentEntity(int idComment){
        this.idComment = idComment;
    }

    public void setIdComment(int idComment){
        this.idComment = idComment;
    }

    public int getIdComment() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ReportEntity getReport() {
        return this.reportEntity;
    }

    public void setReport(boolean report) {
        this.reportEntity.setReported(report);
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

    public OutputComment getOutputComment(){
        OutputComment commentOut = new OutputComment();
        commentOut.setAuthor(this.author);
        commentOut.setComment(this.comment);
        commentOut.setDate(new DateTime(this.date));
        commentOut.setDownScore(0);
        commentOut.setUpScore(0);
        commentOut.setFatherUrl(this.fatherUrl);
        commentOut.setUrlComment("test");
        commentOut.setReport(this.reportEntity.getReported());
        return commentOut;
    }


}
