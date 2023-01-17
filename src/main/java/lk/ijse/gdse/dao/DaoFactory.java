package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.dao.custom.impl.*;

import java.sql.SQLException;

public class DaoFactory {

    private static DaoFactory daoFactory ;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DaoTypes daoType) {
        switch (daoType){
            case MEMBER:
                return (T)new MemberDAOImpl();

            case BOOK:
                return (T)new BookDAOImpl();

            case RETURN:
                return (T)new ReturnDAOImpl();


            case ISSUE:
                return (T) new IssueDAOImpl();

            case QUERY:
                return (T)new QueryDAOImpl();

            default:
                return null;

        }

    }
}
