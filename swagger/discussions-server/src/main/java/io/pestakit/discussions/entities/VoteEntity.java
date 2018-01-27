/**
File which containts methods related to a vote
*/

package io.pestakit.discussions.entities;

import io.pestakit.discussions.api.model.InputVote;
import io.pestakit.discussions.api.model.OutputVote;
import org.joda.time.DateTime;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

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

    public VoteEntity(){
    }

    // constructeur of the vote by author 
    public VoteEntity(InputVote vote, String author){
        this.vote = vote.getVote();
        this.date = new Date();
        this.author = author;
    }

    // setter of the author of the vote
    public void setAuthor(String author){
        this.author = author;
    }

    // getter of the author of the vote
    public String getAuthor(){
        return this.author;
    }

    // setter of the parameter "vote"
    public void setVote(boolean vote){
        this.vote  = vote;
    }

    // getter of a parametre "vote"
    public boolean getVote(){
        return vote;
    }

    // getter DTO vote
    public OutputVote getOutputVote(){
        OutputVote outVote = new OutputVote();
        outVote.setVote(this.vote);
        outVote.setDate(new DateTime(this.date));
        outVote.setAuthor(this.author);
        return outVote;
    }

    // getter of the date of vote
    public Date getDate() {
        return date;
    }

    // setter the date of the vote
    public void setDate(Date date) {
        this.date = date;
    }

}
