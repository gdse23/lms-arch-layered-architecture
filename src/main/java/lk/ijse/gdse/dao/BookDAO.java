package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    Book saveBook(Book book) throws SQLException;
    Book updateBook(Book book) throws SQLException;

    void deleteByIsbn(String isbn) throws SQLException;

    List<Book> findAll() throws SQLException;

    long count() throws SQLException;

    boolean existByIsbn(String isbn) throws SQLException;

    List<Book> searchByText(String text) throws SQLException;
}
