package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.exception.ConstraintViolationException;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {

    private final Connection connection;

    public BookDAOImpl(Connection connection) {

        this.connection = connection;
    }
    @Override
    public Book save(Book book)  throws ConstraintViolationException{
        try {
            if(DBUtil.executeUpdate("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)",
                    book.getIsbn(),book.getTitle(),book.getAuthor(),book.getQty())){
                return book;
            }
            throw new SQLException("Failed to save the book");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }

    }
    @Override
    public Book update(Book book) throws ConstraintViolationException{
        try {
            if(DBUtil.executeUpdate("UPDATE Book SET title=? ,author=? ,qty=? WHERE isbn=?",book.getTitle(),book.getAuthor(),book.getQty(),book.getIsbn())){
                return book;
            }
            throw new SQLException("Failed to update the book");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }

    }
    @Override
    public void deleteByPk(String isbn) throws ConstraintViolationException{

        try {
            if(!DBUtil.executeUpdate("DELETE FROM Book WHERE isbn=?",isbn)) throw new SQLException("Failed to delete the book");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }
    @Override
    public List<Book> findAll() {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book");
            return getBookList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the book");
        }
    }

    @Override
    public Optional<Book> findByPk(String pk) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn=?", pk);
            if(rst.next()){
                return Optional.of(new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty")));

            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the book details");
        }

    }
    @Override
    public long count(){

        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(isbn) AS count FROM Book");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean existByPk(String isbn)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn=?", isbn);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Book> searchByText(String text){
        try{
            text="%"+text+"%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book WHERE isbn LIKE ? OR title LIKE ? OR author LIKE ? ", text, text, text, text);
            return getBookList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Book> getBookList(ResultSet rst)  {
        try {
            List<Book> bookList= new ArrayList<>();
            while (rst.next()){
                Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
