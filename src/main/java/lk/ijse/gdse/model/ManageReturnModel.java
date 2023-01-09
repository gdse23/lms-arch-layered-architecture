package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.view.tm.ReturnTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManageReturnModel {
    public static List<ReturnTM> getAllReturns() {
        List<ReturnTM> returnList=new ArrayList<>();
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Return`");
            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                returnList.add(new ReturnTM(rst.getInt("issue_id"),rst.getDate("date")));
            }
            return returnList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean existById(int issueId) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Return` WHERE issue_id=?");
            stm.setInt(1,issueId);
            ResultSet rst = stm.executeQuery();
            return rst.next();
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static void saveReturn(ReturnTM returnTM) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `Return`(issue_id, date) VALUES (?,?)");
            stm.setInt(1,returnTM.getIssueId());
            stm.setDate(2,returnTM.getDate());
            stm.executeUpdate();

        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
