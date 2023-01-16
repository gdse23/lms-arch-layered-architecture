package lk.ijse.gdse.dto;

import java.sql.Date;

public class ReturnDTO {
    private int issueId;
    private Date date;

    public ReturnDTO() {
    }

    public ReturnDTO(int issueId, Date date) {
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
        return "ReturnDTO{" +
                "issueId=" + issueId +
                ", date=" + date +
                '}';
    }
}
