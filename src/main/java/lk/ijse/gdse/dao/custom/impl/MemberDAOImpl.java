package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.dao.exception.ConstraintViolationException;
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
    public Member save(Member member) throws ConstraintViolationException {
         try {
             if(DBUtil.executeUpdate("INSERT INTO Member (id, name, address, contact) VALUES (?,?,?,?)",
                     member.getId(), member.getName(), member.getAddress(), member.getContact())){
                 return member;
             }

             throw new SQLException("Failed to save the member");
         } catch (SQLException e) {
             throw new ConstraintViolationException(e);
         }

    }

    @Override
    public Member update(Member member) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("UPDATE  Member SET name=? ,address=?, contact=? WHERE id =?",
                    member.getName(), member.getAddress(), member.getContact(), member.getId())){

                return member;

            }

            throw new SQLException("Failed to update the member!");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String memberId) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM Member WHERE id=?",memberId)){
                throw new RuntimeException("Failed to delete the member!");
            }

            throw new SQLException("Failed to delete the member!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> findAll()  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member");
            return getMemberList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Member> findByPk(String memberId)  {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member WHERE id=?", memberId);
            if (rst.next()){
                return Optional.of(new Member(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existByPk(String memberId)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Member WHERE id=?", memberId);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count()  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(id) AS count FROM Member");
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
