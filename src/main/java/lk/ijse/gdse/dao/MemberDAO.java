package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
    Member saveMember(Member member) throws SQLException;

    Member updateMember(Member member) throws SQLException;

    void deleteMemberById(String memberId) throws SQLException;

    List<Member> findAllMembers() throws SQLException;

    Member findByMemberId(String memberId) throws SQLException;

    boolean existMemberByMemberId(String memberId) throws SQLException;

}
