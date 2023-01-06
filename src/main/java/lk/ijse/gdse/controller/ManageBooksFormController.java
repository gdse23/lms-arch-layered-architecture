package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.controller.book.AddBookFormController;
import lk.ijse.gdse.controller.book.UpdateBookFormController;
import lk.ijse.gdse.controller.member.AddMemberFormController;
import lk.ijse.gdse.controller.member.UpdateMemberFormController;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.BookTM;
import lk.ijse.gdse.view.tm.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageBooksFormController {
    public JFXButton btnBack;
    public Button btnAddBook;
    public TextField txtSearch;
    public Button btnUpdate;
    public TableView<BookTM> tblBooks;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnUpdate.setDisable(true);
        tblBooks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblBooks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBooks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBooks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        tblBooks.setItems(FXCollections.observableArrayList(getAllBooks()));

        txtSearch.textProperty().addListener((observableValue, pre, curr) ->{
            if (!Objects.equals(pre, curr)){
                tblBooks.getItems().clear();
                tblBooks.setItems(FXCollections.observableArrayList(searchBooks(curr)));
            }

        } );

        tblBooks.getSelectionModel().selectedItemProperty().addListener((observableValue, pre, curr) -> {
            if (curr!=pre || curr!=null){
                btnUpdate.setDisable(false);
            }

        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnAddBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/book/AddBookForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        AddBookFormController addBookFormController = fxmlLoader.getController();
        addBookFormController.init(tblBooks,this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Book Registration Form");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/book/UpdateBookForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        UpdateBookFormController controller = fxmlLoader.getController();
        controller.init(tblBooks.getSelectionModel().getSelectedItem(),this);
        Stage stage = new Stage();
        stage.setTitle("Update/Delete Book details");
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnIssuedListOnAction(ActionEvent actionEvent) {
    }


    //business logics and data logics

    private List<BookTM> searchBooks(String searchText) {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book WHERE isbn LIKE ? OR  title LIKE ? OR author LIKE ? ");
            searchText="%"+searchText+"%";
            stm.setString(1,searchText);
            stm.setString(2,searchText);
            stm.setString(3,searchText);
            ResultSet rst = stm.executeQuery();
            List<BookTM> bookList=new ArrayList<>();
            while (rst.next()){
                BookTM book = new BookTM(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<BookTM> getAllBooks() {
        try {
            Connection connection  = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Book");
            ResultSet rst = stm.executeQuery();
            List<BookTM> bookList =new ArrayList<>();
            while (rst.next()){
                BookTM book = new BookTM(rst.getString("isbn"), rst.getString("title"), rst.getString("author"), rst.getInt("qty"));
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existBookByIsbn(String isbn) {
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

    public boolean addBook(BookTM book) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Book (isbn, title, author,qty) VALUES (?,?,?,?)");
            stm.setString(1,book.getIsbn());
            stm.setString(2,book.getTitle());
            stm.setString(3,book.getAuthor());
            stm.setInt(4,book.getQty());
            return stm.executeUpdate()==1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean updateBook(BookTM updatedBook) {
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

    public boolean deleteBookByIsbn(String isbn) {
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
