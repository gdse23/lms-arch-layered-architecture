package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.entity.Return;
import lk.ijse.gdse.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReturnDAOImpl implements ReturnDAO {


    @Override
    public Return save(Return entity) throws SQLException {
        return null;
    }

    @Override
    public Return update(Return entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByPk(Integer pk) throws SQLException {

    }

    @Override
    public List<Return> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Return> findByPk(Integer pk) throws SQLException {
        return Optional.empty();
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
