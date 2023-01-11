package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.entity.Member;

import java.sql.SQLException;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public Member save(Member entity) throws SQLException {
        return null;
    }

    @Override
    public Member update(Member entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByPk(String pk) throws SQLException {

    }

    @Override
    public List<Member> findAll() throws SQLException {
        return null;
    }

    @Override
    public Member findByPk(String pk) throws SQLException {
        return null;
    }

    @Override
    public boolean existByPk(String pk) throws SQLException {
        return false;
    }

    @Override
    public long count() throws SQLException {
        return 0;
    }

}
