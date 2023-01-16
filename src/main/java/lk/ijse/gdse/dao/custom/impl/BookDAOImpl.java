package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {

    private Connection connection;

    public BookDAOImpl() throws SQLException, ClassNotFoundException {

        this.connection = DBConnection.getDbConnection().getConnection();
    }
    @Override
    public Book save(Book book) throws SQLException, ClassNotFoundException {

        if(DBUtil.executeUpdate("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)",
                book.getIsbn(),book.getTitle(),book.getAuthor(),book.getQty())){
            return book;
        }
        throw new RuntimeException("Failed to save the book");
    }
    @Override
    public Book update(Book book) throws SQLException, ClassNotFoundException {
        if(DBUtil.executeUpdate("UPDATE Book SET title=? ,author=? ,qty=? WHERE isbn=?",book.getTitle(),book.getAuthor(),book.getQty(),book.getIsbn())){
            return book;
        }
        throw new RuntimeException("Failed to update the book");

    }
    @Override
    public void deleteByPk(String isbn) throws SQLException, ClassNotFoundException {

        if(!DBUtil.executeUpdate("DELETE FROM Book WHERE isbn=?",isbn)) throw new RuntimeException("Failed to delete the book");
    }
    @Override
    public List<Book> findAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book");
        return getBookList(rst);
    }

    @Override
    public Optional<Book> findByPk(String pk) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn=?", pk);
        if(rst.next()){
            return Optional.of(new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty")));

        }
        return Optional.empty();

    }
    @Override
    public long count() throws SQLException, ClassNotFoundException {

        ResultSet rst = DBUtil.executeQuery("SELECT COUNT(isbn) AS count FROM Book");
        rst.next();
        return rst.getInt(1);
    }
    @Override
    public boolean existByPk(String isbn) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn=?", isbn);
        return rst.next();
    }
    @Override
    public List<Book> searchByText(String text) throws SQLException, ClassNotFoundException {
        text="%"+text+"%";
        ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn LIKE ? OR title LIKE ? OR author LIKE ? ", text, text, text, text);
        return getBookList(rst);

    }

    private List<Book> getBookList(ResultSet rst) throws SQLException {
        List<Book> bookList= new ArrayList<>();
        while (rst.next()){
            Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
            bookList.add(book);
        }
        return bookList;
    }

}
