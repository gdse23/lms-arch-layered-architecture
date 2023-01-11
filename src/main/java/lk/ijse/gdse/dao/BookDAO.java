package lk.ijse.gdse.dao;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection connection;

    public BookDAO() throws SQLException, ClassNotFoundException {

        this.connection = DBConnection.getDbConnection().getConnection();
    }

    public Book saveBook(Book book) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)");
        stm.setString(1,book.getIsbn());
        stm.setString(2,book.getTitle());
        stm.setString(3,book.getAuthor());
        stm.setInt(4,book.getQty());
        if (stm.executeUpdate()!=1) throw new RuntimeException("Failed to save the book");
        return book;
    }

    public Book updateBook(Book book) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("UPDATE Book SET title=? ,author=? ,qty=? WHERE isbn=?");
        stm.setString(1,book.getTitle());
        stm.setString(2,book.getAuthor());
        stm.setInt(3,book.getQty());
        stm.setString(4,book.getIsbn());

        if(stm.executeUpdate()!=1) throw new RuntimeException("Failed to update the book");
        return book;

    }

    public void deleteByIsbn(String isbn) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM Book WHERE isbn=?");
        stm.setString(1,isbn);
        if(stm.executeUpdate()!=1) throw new RuntimeException("Failed to delete the book");
    }

    public List<Book> findAll() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book");
        ResultSet rst = stm.executeQuery();
        List<Book> bookList= new ArrayList<>();
        while (rst.next()){
            Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
            bookList.add(book);
        }
        return bookList;
    }

    public long count() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT COUNT(isbn) AS count FROM Book;");
        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getInt(1);
    }

    public boolean existByIsbn(String isbn) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book WHERE isbn=?");
        ResultSet rst = stm.executeQuery();
        return rst.next();
    }

    public List<Book> searchByText(String text) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book WHERE isbn LIKE ? OR title LIKE ? OR author LIKE ? ");
        text="%"+text+"%";
        for (int i = 0; i < 3; i++) {
            stm.setString(i+1,text);
        }
        ResultSet rst = stm.executeQuery();
        List<Book> bookList= new ArrayList<>();
        while (rst.next()){
            Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
            bookList.add(book);
        }
        return bookList;

    }

}
