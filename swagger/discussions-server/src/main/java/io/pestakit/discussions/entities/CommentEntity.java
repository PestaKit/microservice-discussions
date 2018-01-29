/**
File which containts methods related to a comment
*/
package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.*;
import org.hibernate.result.Output;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    // having a one-to-many relationship, once "father" is removed, we delete in cascade its "child" and "orphans" should be remouved
    // as well. 
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReportEntity> reports = new ArrayList();


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VoteEntity> votes = new ArrayList<>();


    //constructeur by author and the contect od the comment 
    public CommentEntity(InputComment comment, String author){
        this.comment = comment.getComment();
        this.author = author;
    }

    // default constructeur
    public CommentEntity(){
    }

    // constructeur by id of the comment
    public CommentEntity(int idComment){
        this.idComment = idComment;
    }

    // setter id of the comment
    public void setIdComment(int idComment){
        this.idComment = idComment;
    }

    // getter id of the comment
    public int getIdComment() {
        return idComment;
    }
    // getter author of the comment
    public String getAuthor() {
        return author;
    }

    // setter author of the comment
    public void setAuthor(String author) {
        this.author = author;
    }

    // getter content of the comment
    public String getComment() {
        return comment;
    }

    // setter content of comment
    public void setComment(String comment) {
        this.comment = comment;
    }

    // getter of father's URL of the comment
    public String getFatherUrl() {
        return fatherUrl;
    }

    // setter of father's URL of the comment
    public void setFatherUrl(String fatherUrl) {
        this.fatherUrl = fatherUrl;
    }

    // getter of the date of publishment of the comment
    public Date getDate() {
        return date;
    }

    // setter of the date of publishment of the comment
    public void setDate(Date date) {
        this.date = date;
    }

    // getter of the all reports of the comment
    public List<ReportEntity> getReports() {
        return this.reports;
    }

    // setter of all reports of the comment
    public void setReports(List<ReportEntity>  reports) {
        this.reports = reports;
    }


    // getter of DTO of the comment 
    public OutputComment getOutputComment(int idDiscussion){
        OutputComment commentOut = new OutputComment();
        commentOut.setAuthor(this.author);
        commentOut.setComment(this.comment);
        commentOut.setDate(new DateTime(this.date));

        List<OutputVote> OutVotes = new ArrayList<>();
        for(VoteEntity vote : this.votes){
            OutVotes.add(vote.getOutputVote());
        }

        commentOut.setVotes(OutVotes);

        OutputLinks links = new OutputLinks();
        links.setSelf("http://exemple.com/discussions/" + idDiscussion +"/comments/"+ this.idComment);
        links.setRelated(this.fatherUrl);
        commentOut.setLinks(links);

        List<OutputReport> outReports = new ArrayList<>();
        for(ReportEntity report : this.reports){
            outReports.add(report.getOutputReport());
        }

        commentOut.setReports(outReports);

        return commentOut;
    }

    // add a vote (plus or minus) to a comment
    public void addVote(VoteEntity voteToAdd){
        votes.add(voteToAdd);
    }

    // remove a vote of the comment
    public void rmVote(VoteEntity voteToRm){
        votes.remove(voteToRm);
    }

    // getter of all votes
    public List<VoteEntity> getVotes() {
        return votes;
    }
    // add a report
    public void addReport(ReportEntity reportToAdd){
        reports.add(reportToAdd);
    }

    // remove a report
    public void rmReport(ReportEntity reportToRm){
        reports.remove(reportToRm);
    }


}
