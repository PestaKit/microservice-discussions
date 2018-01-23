package io.pestakit.discussions.entities;


import io.pestakit.discussions.api.model.InputReport;
import io.pestakit.discussions.api.model.OutputReport;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "REPORT")
public class ReportEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReport;

    private boolean isReported;
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();


    public ReportEntity(){
        isReported = false;
    }

    public ReportEntity(InputReport report, String author){
        isReported = report.getIsReported();
        this.author = author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor() {
        return author;
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

    public OutputReport getOutputReport(){
        OutputReport outReport = new OutputReport();
        outReport.setIsReported(this.isReported);
        outReport.setDate(new DateTime(this.date));
        outReport.setAuthor(this.author);
        return outReport;
    }
}
