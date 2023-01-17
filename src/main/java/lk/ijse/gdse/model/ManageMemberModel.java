package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.to.Member;
import lk.ijse.gdse.to.Return;
import lk.ijse.gdse.view.tm.IssueTM;
import lk.ijse.gdse.view.tm.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageMemberModel {

    public static List<Member> getAllMembers() throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member");
        ResultSet rst = stm.executeQuery();
        List<Member> memberList =new ArrayList<>();
        while (rst.next()){
            Member member = new Member(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
            memberList.add(member);
        }
        return memberList;
    }

    public static List<Member> searchMembers(String searchText){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id LIKE ? OR  name LIKE ? OR address LIKE ? OR contact LIKE ?");
            searchText="%"+searchText+"%";
            stm.setString(1,searchText);
            stm.setString(2,searchText);
            stm.setString(3,searchText);
            stm.setString(4,searchText);
            ResultSet rst = stm.executeQuery();
            List<Member> memberList=new ArrayList<>();
            while (rst.next()){
                Member member = new Member(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
                memberList.add(member);
            }
            return memberList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addMember(Member member) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Member (id, name, address, contact) VALUES (?,?,?,?)");
        stm.setString(1,member.getId());
        stm.setString(2,member.getName());
        stm.setString(3,member.getAddress());
        stm.setString(4,member.getContact());
        return stm.executeUpdate()==1;
    }


    public static boolean existMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id=?");
            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            return rst.next();

        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean updateMember(Member member){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE Member SET name=? ,address=? ,contact=? WHERE id=?");
            stm.setString(1,member.getName());
            stm.setString(2,member.getAddress());
            stm.setString(3,member.getContact());
            stm.setString(4,member.getId());

            return stm.executeUpdate()==1;


        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();

            //let's do within the transaction context
            connection.setAutoCommit(false);

            try {
                PreparedStatement stm = connection.prepareStatement("DELETE  FROM Member WHERE id=?");
                PreparedStatement stm1 = connection.prepareStatement("SELECT r.issue_id,r.date FROM `Return` r left join issue i on i.issue_id = r.issue_id LEFT JOIN Member M on i.memberId = M.id WHERE M.id=?");
                PreparedStatement stm2 = connection.prepareStatement("DELETE FROM `Return` WHERE issue_id=?");
                PreparedStatement stm3 = connection.prepareStatement("SELECT issue_id FROM issue i LEFT JOIN Member M on i.memberId = M.id WHERE M.id=?");
                PreparedStatement stm4 = connection.prepareStatement("DELETE FROM issue WHERE issue_id=?");

                //deleting returns attached with the member
                stm1.setString(1,memberId);
                ResultSet rst = stm1.executeQuery();
                List<Return> returnList=new ArrayList<>();
                while (rst.next()){
                    stm2.setInt(1,rst.getInt("issue_id"));
                    if (stm2.executeUpdate()!=1) throw new  SQLException();
                }
                //deleting issues attached with the member
                stm3.setString(1,memberId);
                ResultSet rstIssue = stm3.executeQuery();
                while (rstIssue.next()){
                    stm4.setInt(1,rstIssue.getInt("issue_id"));
                    if (stm4.executeUpdate()!=1) throw new  SQLException();
                }

                //deleting member from the member table
                stm.setString(1,memberId);
                if(stm.executeUpdate()!=1) throw new SQLException();

                connection.commit();
            }catch (Throwable t){
                connection.rollback();
                throw new RuntimeException(t);

            }finally {
                connection.setAutoCommit(true);
            }



        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }


    //let's try some methods which has join queries
    public static int getIssuedBooksCountByMemberId(String memberId){
        try {
            Connection connection= DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT (COUNT(i.issue_id)-COUNT(R.issue_id))  AS borrowedItems FROM Member m LEFT JOIN issue i on m.id = i.memberId\n" +
                    "LEFT JOIN `Return` R on i.issue_id = R.issue_id\n" +
                    "WHERE m.id=?\n" +
                    "GROUP BY m.id");

            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getInt("borrowedItems");
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }


    public static List<IssueTM> findAllIssuesById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            List<IssueTM> issueTMList =new ArrayList<>();

            PreparedStatement stm =
                    connection.prepareStatement("SELECT M.id AS memberId,i.issue_id AS issueId,i.date AS issuedDate,(R.date IS NULL ) AS returnStatus FROM issue i LEFT JOIN `Return` R on i.issue_id = R.issue_id LEFT JOIN  Member M on i.memberId = M.id WHERE M.id=?");
            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                IssueTM issueTM = new IssueTM(rst.getInt("issueId"), rst.getDate("issuedDate"), rst.getBoolean("returnStatus") ? Status.NOT_RETURNED : Status.RETURNED);
                issueTMList.add(issueTM);
            }
            return issueTMList;
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }
}
