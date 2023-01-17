package lk.ijse.gdse.service;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.SuperDAO;
import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Book;

import java.sql.SQLException;
import java.util.List;

public class BOLogics {

    public static void main(String[] args) {
        BookDAO bookDAO = DaoFactory.getInstance().getDAO(DBConnection.getDbConnection().getConnection(), DaoTypes.BOOK);


        List<Book> all = bookDAO.findAll();
        System.out.println(all);
    }
}
