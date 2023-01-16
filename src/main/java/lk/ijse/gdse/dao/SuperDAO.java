package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Member;
import lk.ijse.gdse.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SuperDAO<T extends SuperEntity ,ID extends Serializable> {

    T save(T entity) throws SQLException, ClassNotFoundException;

    T update(T entity) throws SQLException, ClassNotFoundException;

    void deleteByPk(ID pk) throws SQLException, ClassNotFoundException;

    List<T> findAll() throws SQLException, ClassNotFoundException;

    Optional<T> findByPk(ID pk) throws SQLException, ClassNotFoundException;

    boolean existByPk(ID pk) throws SQLException, ClassNotFoundException;

    long count() throws SQLException, ClassNotFoundException;


}
