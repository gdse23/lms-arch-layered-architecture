package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.BookTM;
import lk.ijse.gdse.view.tm.MemberTM;

import java.io.IOException;
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
    public Button btnIssueList;
    public TableView<BookTM> tblBooks;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnUpdate.setDisable(true);
        btnIssueList.setDisable(true);
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
                btnIssueList.setDisable(false);
            }

        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnAddBookOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
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

}
