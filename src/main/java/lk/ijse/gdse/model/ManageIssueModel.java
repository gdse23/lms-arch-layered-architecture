package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.view.tm.IssueTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManageIssueModel {
    public static boolean existById(String issueId) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT issue_id FROM issue WHERE issue_id=?");
            ResultSet rst = stm.executeQuery();
            stm.setString(1,issueId);
            return rst.next();
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static IssueTM saveIssue(IssueTM issueTM) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO issue (isbn, memberId, date) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,issueTM.getIsbn());
            stm.setString(2,issueTM.getMemberId());
            stm.setDate(3,issueTM.getDate());
            stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            keys.next();
            int issueId = keys.getInt(1);
            issueTM.setIssueId(issueId);
            return issueTM;
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<IssueTM> findAllIssues() {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM issue");
            ResultSet rst = stm.executeQuery();
            List<IssueTM> issueList = new ArrayList<>();
            while (rst.next()){
                IssueTM issueTM = new IssueTM(rst.getInt("issue_id"), rst.getString("isbn"), rst.getString("memberId"), rst.getDate("date"));
                issueList.add(issueTM);
            }
            return issueList;

        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateIssue(IssueTM issueTM) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE issue SET isbn=? ,memberId=? where issue_id=?");
            stm.setString(1,issueTM.getIsbn());
            stm.setString(2,issueTM.getMemberId());
            stm.setInt(3,issueTM.getIssueId());
            stm.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
