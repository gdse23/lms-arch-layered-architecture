package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDAO;
import lk.ijse.gdse.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO extends SuperDAO<Book,String> {
    public List<Book> searchByText(String text) throws SQLException;
}
