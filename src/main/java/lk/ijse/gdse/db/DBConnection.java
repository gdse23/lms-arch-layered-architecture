package lk.ijse.gdse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;

    private final Connection connection;

    public DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/lms_gdse","root","KPsuneetha@123");

    }

    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection==null?dbConnection=new DBConnection():dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
