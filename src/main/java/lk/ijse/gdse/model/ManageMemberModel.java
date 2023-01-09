package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.view.tm.IssueTM;
import lk.ijse.gdse.view.tm.MemberTM;
import lk.ijse.gdse.view.tm.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageMemberModel {

    public static List<MemberTM> getAllMembers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member");
        ResultSet rst = stm.executeQuery();
        List<MemberTM> memberList =new ArrayList<>();
        while (rst.next()){
            MemberTM memberTM = new MemberTM(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
            memberList.add(memberTM);
        }
        return memberList;
    }

    public static List<MemberTM> searchMembers(String searchText){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id LIKE ? OR  name LIKE ? OR address LIKE ? OR contact LIKE ?");
            searchText="%"+searchText+"%";
            stm.setString(1,searchText);
            stm.setString(2,searchText);
            stm.setString(3,searchText);
            stm.setString(4,searchText);
            ResultSet rst = stm.executeQuery();
            List<MemberTM> memberList=new ArrayList<>();
            while (rst.next()){
                MemberTM memberTM = new MemberTM(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
                memberList.add(memberTM);
            }
            return memberList;
        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addMember(MemberTM memberTM) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Member (id, name, address, contact) VALUES (?,?,?,?)");
        stm.setString(1,memberTM.getId());
        stm.setString(2,memberTM.getName());
        stm.setString(3,memberTM.getAddress());
        stm.setString(4,memberTM.getContact());
        return stm.executeUpdate()==1;
    }


    public static boolean existMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id=?");
            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            return rst.next();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean updateMember(MemberTM memberTM){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE Member SET name=? ,address=? ,contact=? WHERE id=?");
            stm.setString(1,memberTM.getName());
            stm.setString(2,memberTM.getAddress());
            stm.setString(3,memberTM.getContact());
            stm.setString(4,memberTM.getId());

            return stm.executeUpdate()==1;


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE  FROM Member WHERE id=?");
            stm.setString(1,memberId);
            return stm.executeUpdate()==1;
        } catch (SQLException | ClassNotFoundException e) {
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
        } catch (SQLException | ClassNotFoundException e) {
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
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
