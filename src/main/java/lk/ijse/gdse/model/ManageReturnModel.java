package lk.ijse.gdse.model;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.to.Return;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageReturnModel {
    public static List<Return> getAllReturns() {

        ReturnDAO returnDAO = DaoFactory.getInstance().getDAO(DBConnection.getDbConnection().getConnection(), DaoTypes.RETURN);

        return returnDAO.findAll().stream().map(returnItem -> new Return(returnItem.getIssueId(), returnItem.getDate())).collect(Collectors.toList());

    }

    public static boolean existById(int issueId) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Return` WHERE issue_id=?");
            stm.setInt(1,issueId);
            ResultSet rst = stm.executeQuery();
            return rst.next();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
    }

    public static void saveReturn(Return aReturn) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `Return`(issue_id, date) VALUES (?,?)");
            stm.setInt(1,aReturn.getIssueId());
            stm.setDate(2,aReturn.getDate());
            stm.executeUpdate();

        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
    }

    public static List<Return> findAllReturnsByMemberId(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT r.issue_id,r.date FROM `Return` r left join issue i on i.issue_id = r.issue_id LEFT JOIN Member M on i.memberId = M.id WHERE M.id=?");
            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            List<Return> returnList=new ArrayList<>();
            while (rst.next()){
                returnList.add(new Return(rst.getInt("issue_id"),rst.getDate("date")));
            }
            return returnList;

        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }
}
