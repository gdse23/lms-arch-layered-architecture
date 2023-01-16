package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.entity.Book;
import lk.ijse.gdse.entity.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public Member save(Member member) throws SQLException, ClassNotFoundException {
         if(DBUtil.executeUpdate("INSERT INTO Member (id, name, address, contact) VALUES (?,?,?,?)",
                 member.getId(), member.getName(), member.getAddress(), member.getContact())){
             return member;
         }

         throw new RuntimeException("Failed to save the member");
    }

    @Override
    public Member update(Member member) throws SQLException, ClassNotFoundException {
        if(DBUtil.executeUpdate("UPDATE  Member SET name=? ,address=?, contact=? WHERE id =?",
                member.getName(), member.getAddress(), member.getContact(), member.getId())){

            return member;

        }
        throw new RuntimeException(" Failed to update the member");
    }

    @Override
    public void deleteByPk(String memberId) throws SQLException, ClassNotFoundException {
        if(!DBUtil.executeUpdate("DELETE FROM Member WHERE id=?",memberId)){
            throw new RuntimeException("Failed to delete the member!");
        }
    }

    @Override
    public List<Member> findAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member");
        return getMemberList(rst);
    }

    @Override
    public Optional<Member> findByPk(String memberId) throws SQLException, ClassNotFoundException {

        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member WHERE id=?", memberId);
        if (rst.next()){
             return Optional.of(new Member(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact")));
        }

        return Optional.empty();
    }

    @Override
    public boolean existByPk(String memberId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member WHERE id=?", memberId);
        return rst.next();
    }

    @Override
    public long count() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT COUNT(id) AS count FROM Member");
        return rst.getInt(1);
    }

    private List<Member> getMemberList(ResultSet rst) throws SQLException {
        List<Member> memberList= new ArrayList<>();
        while (rst.next()){
            Member member = new Member(rst.getString("memberId"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
            memberList.add(member);
        }
        return memberList;
    }

}
