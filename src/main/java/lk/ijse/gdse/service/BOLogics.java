package lk.ijse.gdse.service;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.SuperDAO;
import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.custom.MemberDAO;

import java.sql.SQLException;

public class BOLogics {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        BookDAO bookDAO = DaoFactory.getInstance().getDAO(DaoTypes.BOOK);

        long count =bookDAO.count();
        System.out.println(count);
    }
}
