package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.entity.Return;
import lk.ijse.gdse.entity.SuperEntity;
import lk.ijse.gdse.view.tm.ReturnTM;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnDAOImpl implements ReturnDAO {


    @Override
    public Return save(Return returnEntity) throws SQLException, ClassNotFoundException {
        if(DBUtil.executeUpdate("INSERT INTO `Return` (issue_id, date) VALUES (?,?)",
                returnEntity.getIssueId(),returnEntity.getDate())){
            return returnEntity;
        }
        throw new RuntimeException("Failed to return the book!");
    }

    @Override
    public Return update(Return entity) throws SQLException, ClassNotFoundException {
        if( DBUtil.executeUpdate("UPDATE `Return` SET date=? WHERE issue_id=?", entity.getDate(), entity.getIssueId())){
            return entity;
        }

        throw new RuntimeException("Failed to update the return date!");
    }

    @Override
    public void deleteByPk(Integer issueId) throws SQLException, ClassNotFoundException {
        if(!DBUtil.executeUpdate("DELETE FROM `Return` WHERE issue_id =?",issueId)){
            throw new RuntimeException("Failed to delete the return record!");
        }
    }

    @Override
    public List<Return> findAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM issue");
        List<Return> returnList = new ArrayList<>();
        while (rst.next()){
            returnList.add(new Return(rst.getInt("issue_id"),rst.getDate("date")));
        }
        return returnList;

    }

    @Override
    public Optional<Return> findByPk(Integer issueId) throws SQLException, ClassNotFoundException {

        ResultSet rst = DBUtil.executeQuery("SELECT * FROM `Return` WHERE issue_id =?", issueId);
        if (rst.next()){
            return Optional.of(new Return(rst.getInt("issue_id"),rst.getDate("date")));
        }
        return Optional.empty();
    }

    @Override
    public boolean existByPk(Integer issueId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM `Return` WHERE issue_id= ?", issueId);
        return rst.next();
    }

    @Override
    public long count() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT COUNT(issue_id) AS count FROM `Return`");
        rst.next();
        return rst.getInt(1);
    }
}
