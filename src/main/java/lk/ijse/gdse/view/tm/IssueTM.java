package lk.ijse.gdse.view.tm;

import java.sql.Date;

public class IssueTM {
    private String issueId;
    private Date date;
    private Status status;

    public IssueTM() {
    }

    public IssueTM(String issueId, Date date, Status status) {
        this.issueId = issueId;
        this.date = date;
        this.status = status;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IssueTM{" +
                "issueId='" + issueId + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}


