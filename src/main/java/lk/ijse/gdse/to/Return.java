package lk.ijse.gdse.to;

import java.sql.Date;

public class Return {
    private int issueId;
    private Date date;

    public Return() {
    }

    public Return(int issueId, Date date) {
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
        return "Return{" +
                "issueId=" + issueId +
                ", date=" + date +
                '}';
    }
}
