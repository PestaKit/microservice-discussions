/**
File which containts methods related to a report
*/
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

    // constructeur by author 
    public ReportEntity(InputReport report, String author){
        isReported = report.getIsReported();
        this.author = author;
    }

    // setter of the author
    public void setAuthor(String author){
        this.author = author;
    }

    // geter of the author's name
    public String getAuthor() {
        return author;
    }

    public void setReported(boolean reported) {
        isReported = reported;
    }
    
    // getter of a property "reported"
    public boolean getReported() {
        return isReported;
    }

    // getter of the date of report
    public Date getDate() {
        return date;
    }

    // setter of the date of the report
    public void setDate(Date date) {
        this.date = date;
    }

    // getter of DTo report
    public OutputReport getOutputReport(){
        OutputReport outReport = new OutputReport();
        outReport.setIsReported(this.isReported);
        outReport.setDate(new DateTime(this.date));
        outReport.setAuthor(this.author);
        return outReport;
    }
}
