package lk.ijse.gdse.entity;

import java.io.Serializable;
import java.sql.Date;

public class Return implements SuperEntity {

    int IssueId;
    Date date;

    public Return() {
    }

    public Return(int issueId, Date date) {
        IssueId = issueId;
        this.date = date;
    }

    public int getIssueId() {
        return IssueId;
    }

    public void setIssueId(int issueId) {
        IssueId = issueId;
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
                "IssueId=" + IssueId +
                ", date=" + date +
                '}';
    }
}
