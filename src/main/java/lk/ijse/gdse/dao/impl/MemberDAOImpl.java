package lk.ijse.gdse.dao.impl;

import lk.ijse.gdse.dao.MemberDAO;
import lk.ijse.gdse.entity.Member;

import java.sql.SQLException;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public Member saveMember(Member member) throws SQLException {
        return null;
    }

    @Override
    public Member updateMember(Member member) throws SQLException {
        return null;
    }

    @Override
    public void deleteMemberById(String memberId) throws SQLException {

    }

    @Override
    public List<Member> findAllMembers() throws SQLException {
        return null;
    }

    @Override
    public Member findByMemberId(String memberId) throws SQLException {
        return null;
    }

    @Override
    public boolean existMemberByMemberId(String memberId) throws SQLException {
        return false;
    }
}
