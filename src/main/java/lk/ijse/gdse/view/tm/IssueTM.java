package lk.ijse.gdse.view.tm;

import java.sql.Date;

public class IssueTM {
    private int issueId;
    private String isbn;
    private String memberId;
    private Date date;
    private Status status;

    public IssueTM() {
    }

    public IssueTM(int issueId, String isbn, String memberId, Date date, Status status) {
        this.issueId = issueId;
        this.isbn = isbn;
        this.memberId = memberId;
        this.date = date;
        this.status = status;
    }

    public IssueTM(String isbn, String memberId, Date date) {
        this.isbn = isbn;
        this.memberId = memberId;
        this.date = date;
    }

    public IssueTM(String memberId, Date date, Status status) {
        this.memberId = memberId;
        this.date = date;
        this.status = status;
    }

    public IssueTM(int issueId, String isbn, String memberId, Date date) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IssueTM{" +
                "issueId=" + issueId +
                ", isbn='" + isbn + '\'' +
                ", memberId='" + memberId + '\'' +
                ", date=" + date +
                '}';
    }
}


