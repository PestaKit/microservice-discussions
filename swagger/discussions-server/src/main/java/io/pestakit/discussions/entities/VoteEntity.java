package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.InputVote;
import io.pestakit.discussions.api.model.OutputVote;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "VOTES")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVote;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private boolean vote;
    private String author;

    public VoteEntity(String author){
        this.date = new Date();
        this.author = author;
    }

    public VoteEntity(){
    }

    public VoteEntity(InputVote vote){
        this.vote = vote.getVote();
        this.date = new Date();
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setVote(boolean vote){
        this.vote  = vote;
    }

    public boolean getVote(){
        return vote;
    }

    public OutputVote getOutputVote(){
        OutputVote outVote = new OutputVote();
        outVote.setVote(this.vote);
        outVote.setDate(new DateTime(this.date));
        return outVote;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




}