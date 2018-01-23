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

    @OneToMany(
            orphanRemoval = true
    )
    private List<ReportEntity> reports = new ArrayList();


    @OneToMany(
            orphanRemoval = true
    )
    private List<VoteEntity> votes = new ArrayList<>();


    public CommentEntity(InputComment comment){
        this.comment = comment.getComment();
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

    public List<ReportEntity> getReports() {
        return this.reports;
    }

    public void setReports(List<ReportEntity>  reports) {
        this.reports = reports;
    }


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

    public void addVote(VoteEntity voteToAdd){
        votes.add(voteToAdd);
    }

    public void rmVote(VoteEntity voteToRm){
        votes.remove(voteToRm);
    }

    public List<VoteEntity> getVotes() {
        return votes;
    }
    public void addReport(ReportEntity reportToAdd){
        reports.add(reportToAdd);
    }

    public void rmReport(ReportEntity reportToRm){
        reports.remove(reportToRm);
    }


}
