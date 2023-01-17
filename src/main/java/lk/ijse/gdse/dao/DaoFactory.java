package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.dao.custom.impl.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {

    private static DaoFactory daoFactory ;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }

    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes daoType) {
        switch (daoType){
            case MEMBER:
                return (T)new MemberDAOImpl(connection);

            case BOOK:
                return (T)new BookDAOImpl(connection);

            case RETURN:
                return (T)new ReturnDAOImpl(connection);


            case ISSUE:
                return (T) new IssueDAOImpl(connection);

            case QUERY:
                return (T)new QueryDAOImpl(connection);

            default:
                return null;

        }

    }
}
