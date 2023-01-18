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
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.BookService;
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
import java.util.stream.Collectors;

public class ManageBooksFormController {
    public JFXButton btnBack;
    public Button btnAddBook;
    public TextField txtSearch;
    public Button btnUpdate;
    public TableView<BookTM> tblBooks;

    public BookService bookService;

    public void initialize() throws SQLException, ClassNotFoundException {

        this.bookService= ServiceFactory.getInstance().getService(ServiceTypes.BOOK);

        btnUpdate.setDisable(true);
        tblBooks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblBooks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBooks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBooks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        List<BookTM> bookTMList = bookService.findAllBooks().stream().map(b -> new BookTM(b.getIsbn(), b.getTitle(), b.getAuthor(), b.getQty())).collect(Collectors.toList());

        tblBooks.setItems(FXCollections.observableArrayList(bookTMList));

        txtSearch.textProperty().addListener((observableValue, pre, curr) ->{
            if (!Objects.equals(pre, curr)){
                tblBooks.getItems().clear();
                List<BookTM> bookList = bookService.searchBookByText(curr).stream().map(book -> new BookTM(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQty())).collect(Collectors.toList());
                tblBooks.setItems(FXCollections.observableArrayList(bookList));
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
        stage.setTitle("New BookDTO Registration Form");
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
        stage.setTitle("Update/Delete BookDTO details");
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
