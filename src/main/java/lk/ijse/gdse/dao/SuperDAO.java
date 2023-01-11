package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Member;
import lk.ijse.gdse.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface SuperDAO<T extends SuperEntity,ID extends Serializable> {

    T save(T entity) throws SQLException;

    T update(T entity) throws SQLException;

    void deleteByPk(ID pk) throws SQLException;

    List<T> findAll() throws SQLException;

    T findByPk(ID pk) throws SQLException;

    boolean existByPk(ID pk) throws SQLException;

    long count() throws SQLException;


}
