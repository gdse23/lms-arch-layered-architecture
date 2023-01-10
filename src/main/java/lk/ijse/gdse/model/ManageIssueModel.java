package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.to.Issue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageIssueModel {
    public static boolean existById(String issueId) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT issue_id FROM issue WHERE issue_id=?");
            stm.setString(1,issueId);
            ResultSet rst = stm.executeQuery();
            return rst.next();
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Issue saveIssue(Issue issue) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO issue (isbn, memberId, date) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,issue.getIsbn());
            stm.setString(2,issue.getMemberId());
            stm.setDate(3,issue.getDate());
            stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            keys.next();
            int issueId = keys.getInt(1);
            issue.setIssueId(issueId);
            return issue;
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Issue> findAllIssues() {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM issue");
            ResultSet rst = stm.executeQuery();
            List<Issue> issueList = new ArrayList<>();
            while (rst.next()){
                Issue issue = new Issue(rst.getInt("issue_id"), rst.getString("isbn"), rst.getString("memberId"), rst.getDate("date"));
                issueList.add(issue);
            }
            return issueList;

        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateIssue(Issue issue) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE issue SET isbn=? ,memberId=? where issue_id=?");
            stm.setString(1,issue.getIsbn());
            stm.setString(2,issue.getMemberId());
            stm.setInt(3,issue.getIssueId());
            stm.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
