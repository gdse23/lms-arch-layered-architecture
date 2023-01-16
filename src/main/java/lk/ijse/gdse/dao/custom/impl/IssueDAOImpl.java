package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Issue;
import lk.ijse.gdse.entity.Return;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IssueDAOImpl implements IssueDAO {

    private final Connection connection = DBConnection.getDbConnection().getConnection();

    public IssueDAOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public Issue save(Issue issue) throws SQLException {
        PreparedStatement stm = connection.prepareStatement
                ("INSERT INTO issue SET  isbn=? ,memberId=?,date =?", Statement.RETURN_GENERATED_KEYS);
        stm.setString(1,issue.getIsbn());
        stm.setString(2,issue.getMemberId());
        stm.setDate(3, Date.valueOf(LocalDate.now()));
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        generatedKeys.next();
        int issueId = generatedKeys.getInt(1);
        issue.setIssueId(issueId);
        return issue;

    }

    @Override
    public Issue update(Issue issue) throws SQLException, ClassNotFoundException {
        if(DBUtil.executeUpdate("UPDATE  issue SET isbn =? , memberId=? WHERE issue_id =?",issue.getIsbn(),issue.getMemberId(),issue.getIssueId())){
            return issue;
        }
        throw new RuntimeException("Failed to update the issue!");
    }

    @Override
    public void deleteByPk(Integer issueId) throws SQLException, ClassNotFoundException {
        if(!DBUtil.executeUpdate("DELETE FROM issue WHERE issue_id=?",issueId)){
            throw new RuntimeException("Failed to delete the issue");
        }

    }

    @Override
    public List<Issue> findAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue");
        List<Issue> issueList = new ArrayList<>();
        while (rst.next()){
            issueList.add(new Issue(rst.getInt("issue_id"),rst.getString("isbn"),rst.getString("memberId"),rst.getDate("date")));
        }
        return issueList;
    }

    @Override
    public Optional<Issue> findByPk(Integer issueId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue WHERE issue_id =?", issueId);
        if(rst.next()){
            return Optional.of(new Issue(rst.getInt("issue_id"),rst.getString("isbn"),rst.getString("memberId"),rst.getDate("datet")));
        }

        return Optional.empty();

    }

    @Override
    public boolean existByPk(Integer issueId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue WHERE issue_id =?", issueId);
        return rst.next();
    }

    @Override
    public long count() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT COUNT(issue_id) AS count FROM issue");
        rst.next();
        return rst.getInt(1);
    }
}
