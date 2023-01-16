package lk.ijse.gdse.model;

import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.to.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageBookModel {
    public static  List<Book> searchBooks(String searchText) {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book WHERE isbn LIKE ? OR  title LIKE ? OR author LIKE ? ");
            searchText="%"+searchText+"%";
            stm.setString(1,searchText);
            stm.setString(2,searchText);
            stm.setString(3,searchText);
            ResultSet rst = stm.executeQuery();
            List<Book> bookList=new ArrayList<>();
            while (rst.next()){
                Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static  List<Book> getAllBooks() {
        try {
//            Connection connection  = DBConnection.getDbConnection().getConnection();
//            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book");
//            ResultSet rst = stm.executeQuery();
            List<Book> bookList =new ArrayList<>();

            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Book");
            while (rst.next()){
                Book book = new Book(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existBookByIsbn(String isbn) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book WHERE isbn=?");
            stm.setString(1,isbn);
            ResultSet rst = stm.executeQuery();
            return rst.next();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addBook(Book book) {
        try {
//            Connection connection = DBConnection.getDbConnection().getConnection();
//            PreparedStatement stm = connection.prepareStatement("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)");
//            stm.setString(1,book.getIsbn());
//            stm.setString(2,book.getTitle());
//            stm.setString(3,book.getAuthor());

            return DBUtil.executeUpdate("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)", book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQty());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean updateBook(Book updatedBook) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE Book SET title=? ,author=? ,qty=? WHERE isbn=?");
            stm.setString(1,updatedBook.getTitle());
            stm.setString(2,updatedBook.getAuthor());
            stm.setInt(3,updatedBook.getQty());
            stm.setString(4,updatedBook.getIsbn());

            return stm.executeUpdate()==1;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteBookByIsbn(String isbn) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE  FROM Book WHERE isbn=?");
            stm.setString(1,isbn);
            return stm.executeUpdate()==1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
