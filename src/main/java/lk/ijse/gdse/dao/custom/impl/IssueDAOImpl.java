package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.exception.ConstraintViolationException;
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

    private final Connection connection;

    public IssueDAOImpl(Connection connection) {
        this.connection=connection;
    }

    @Override
    public Issue save(Issue issue) throws ConstraintViolationException {
        try {
            PreparedStatement stm = connection.prepareStatement
                    ("INSERT INTO issue SET  isbn=? ,memberId=?,date =?", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,issue.getIsbn());
            stm.setString(2,issue.getMemberId());
            stm.setDate(3, Date.valueOf(LocalDate.now()));
            if(stm.executeUpdate()!=1) throw new SQLException("Failed to create the book");
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int issueId = generatedKeys.getInt(1);
            issue.setIssueId(issueId);
            return issue;
        } catch (SQLException e) {
            throw new  ConstraintViolationException(e);
        }

    }

    @Override
    public Issue update(Issue issue) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("UPDATE  issue SET isbn =? , memberId=? WHERE issue_id =?",issue.getIsbn(),issue.getMemberId(),issue.getIssueId())){
                return issue;
            }
            throw new SQLException("Failed to update the issue!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByPk(Integer issueId) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM issue WHERE issue_id=?",issueId)){
                throw new SQLException("Failed to delete the issue");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }

    }

    @Override
    public List<Issue> findAll()  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue");
            List<Issue> issueList = new ArrayList<>();
            while (rst.next()){
                issueList.add(new Issue(rst.getInt("issue_id"),rst.getString("isbn"),rst.getString("memberId"),rst.getDate("date")));
            }
            return issueList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Issue> findByPk(Integer issueId)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue WHERE issue_id =?", issueId);
            if(rst.next()){
                return Optional.of(new Issue(rst.getInt("issue_id"),rst.getString("isbn"),rst.getString("memberId"),rst.getDate("datet")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existByPk(Integer issueId) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue WHERE issue_id =?", issueId);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(issue_id) AS count FROM issue");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
