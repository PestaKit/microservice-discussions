package io.pestakit.discussions.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "REPORT")
public class ReportEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private boolean isReported;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommentEntity> comments = new ArrayList<>();

    public ReportEntity(){
        isReported = false;
    }

    public void setReported(boolean reported) {
        isReported = reported;
    }

    public boolean getReported() {
        return isReported;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
