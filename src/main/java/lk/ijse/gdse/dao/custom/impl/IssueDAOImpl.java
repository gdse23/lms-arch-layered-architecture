package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.entity.Issue;

import java.sql.SQLException;
import java.util.List;

public class IssueDAOImpl implements IssueDAO {

    @Override
    public Issue save(Issue entity) throws SQLException {
        return null;
    }

    @Override
    public Issue update(Issue entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByPk(Integer pk) throws SQLException {

    }

    @Override
    public List<Issue> findAll() throws SQLException {
        return null;
    }

    @Override
    public Issue findByPk(Integer pk) throws SQLException {
        return null;
    }

    @Override
    public boolean existByPk(Integer pk) throws SQLException {
        return false;
    }

    @Override
    public long count() throws SQLException {
        return 0;
    }
}
