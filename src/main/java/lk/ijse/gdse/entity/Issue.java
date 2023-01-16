package lk.ijse.gdse.entity;

import java.io.Serializable;
import java.sql.Date;

public class Issue implements SuperEntity {
    int issueId;
    String isbn;
    String memberId;
    Date date;

    public Issue() {
    }

    public Issue(int issueId, String isbn, String memberId, Date date) {
        this.issueId = issueId;
        this.isbn = isbn;
        this.memberId = memberId;
        this.date = date;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "IssueDTO{" +
                "issueId=" + issueId +
                ", isbn='" + isbn + '\'' +
                ", memberId='" + memberId + '\'' +
                ", date=" + date +
                '}';
    }
}
