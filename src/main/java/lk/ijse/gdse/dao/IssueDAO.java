package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Issue;

import java.sql.SQLException;
import java.util.List;

public interface IssueDAO {

    Issue saveIssue(Issue issue) throws SQLException;

    Issue updateIssue(Issue issue) throws SQLException;

    void deleteIssueByIssueId(int issueId) throws SQLException;

    List<Issue> findAllIssues() throws SQLException;

    boolean existByIssueId(int issueId) throws SQLException;

    Issue findIssueById(int issueId) throws SQLException;
}
