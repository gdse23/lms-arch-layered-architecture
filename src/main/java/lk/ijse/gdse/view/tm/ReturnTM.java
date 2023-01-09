package lk.ijse.gdse.view.tm;

import java.sql.Date;

public class ReturnTM {
    private int issueId;
    private Date date;

    public ReturnTM() {
    }

    public ReturnTM(int issueId, Date date) {
        this.issueId = issueId;
        this.date = date;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReturnTM{" +
                "issueId=" + issueId +
                ", date=" + date +
                '}';
    }
}
