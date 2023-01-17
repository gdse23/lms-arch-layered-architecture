package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.dao.exception.ConstraintViolationException;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.entity.Return;
import lk.ijse.gdse.entity.SuperEntity;
import lk.ijse.gdse.view.tm.ReturnTM;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnDAOImpl implements ReturnDAO {

    private final Connection connection;

    public ReturnDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Return save(Return returnEntity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO `Return` (issue_id, date) VALUES (?,?)",
                    returnEntity.getIssueId(),returnEntity.getDate())){
                return returnEntity;
            }
            throw new SQLException("Failed to return the book!");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Return update(Return entity) throws ConstraintViolationException {
        try {
            if( DBUtil.executeUpdate("UPDATE `Return` SET date=? WHERE issue_id=?", entity.getDate(), entity.getIssueId())){
                return entity;
            }

            throw new SQLException("Failed to update the return date!");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(Integer issueId) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM `Return` WHERE issue_id =?",issueId)){
                throw new SQLException("Failed to delete the return record!");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Return> findAll()  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM `Return`");
            List<Return> returnList = new ArrayList<>();
            while (rst.next()){
                returnList.add(new Return(rst.getInt("issue_id"),rst.getDate("date")));
            }
            return returnList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Return> findByPk(Integer issueId)  {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM `Return` WHERE issue_id =?", issueId);
            if (rst.next()){
                return Optional.of(new Return(rst.getInt("issue_id"),rst.getDate("date")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existByPk(Integer issueId)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM `Return` WHERE issue_id= ?", issueId);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count()  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(issue_id) AS count FROM `Return`");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
