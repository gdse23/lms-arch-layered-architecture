package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.entity.Return;
import lk.ijse.gdse.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class ReturnDAOImpl implements ReturnDAO {

    @Override
    public SuperEntity save(SuperEntity entity) throws SQLException {
        return null;
    }

    @Override
    public SuperEntity update(SuperEntity entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByPk(Serializable pk) throws SQLException {

    }

    @Override
    public List findAll() throws SQLException {
        return null;
    }

    @Override
    public SuperEntity findByPk(Serializable pk) throws SQLException {
        return null;
    }

    @Override
    public boolean existByPk(Serializable pk) throws SQLException {
        return false;
    }

    @Override
    public long count() throws SQLException {
        return 0;
    }

    @Override
    public Return saveReturn(Return returnEntity) throws SQLException {
        return null;
    }

    @Override
    public void deleteReturnById(int issueId) throws SQLException {

    }

    @Override
    public List<Return> findAllReturns() throws SQLException {
        return null;
    }

    @Override
    public Return findByIssueId(int issueId) throws SQLException {
        return null;
    }
}
