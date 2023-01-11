package lk.ijse.gdse.dao;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void deleteByIsbn(String isbn){

    }

    public List<Book> findAll(){
        return null;
    }

    public long count(){
        return 0;
    }

    public boolean existByIsbn(String isbn){
        return true;
    }

    public List<Book> searchByText(String text){
        return null;
    }

}
