package lk.ijse.gdse.dao.impl;

import lk.ijse.gdse.dao.ReturnDAO;
import lk.ijse.gdse.entity.Return;

import java.sql.SQLException;
import java.util.List;

public class ReturnDAOImpl implements ReturnDAO {
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
