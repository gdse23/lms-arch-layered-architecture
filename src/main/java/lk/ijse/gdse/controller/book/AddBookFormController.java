package lk.ijse.gdse.controller.book;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.controller.ManageBooksFormController;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.model.ManageBookModel;
import lk.ijse.gdse.to.Book;
import lk.ijse.gdse.view.tm.BookTM;
import lk.ijse.gdse.view.tm.MemberTM;

public class AddBookFormController {
    public TextField txtIsbn;
    public TextField txtTitle;
    public TextField txtAuthor;
    public TextField txtQty;
    public Button btnRegister;

    public TableView<BookTM> tblBooks;

    public ManageBooksFormController manageBooksController;

    public void init(TableView<BookTM> tblBooks, ManageBooksFormController manageBooksController){
        this.tblBooks=tblBooks;
        this.manageBooksController=manageBooksController;
    }


    public void btnRegisterOnAction(ActionEvent actionEvent) {

        //Data validation
        if (txtIsbn.getText().isBlank() || !txtIsbn.getText().matches("\\d{13}")) {
            new Alert(Alert.AlertType.ERROR,"ISBN invalid or empty").show();
            txtIsbn.selectAll();
            txtIsbn.requestFocus();
            return;
        }
        else if (txtTitle.getText().isBlank() || !txtTitle.getText().matches("^[A-Za-z0-9 ]+$")){
            new Alert(Alert.AlertType.ERROR,"Name cannot be empty").show();
            txtTitle.selectAll();
            txtTitle.requestFocus();
            return;
        }else if (txtAuthor.getText().isBlank() || !txtAuthor.getText().matches("^[A-Za-z ]+$")){
            new Alert(Alert.AlertType.ERROR,"Address cannot be empty").show();
            txtAuthor.selectAll();
            txtAuthor.requestFocus();
            return;
        }else if (txtQty.getText().isBlank() || !txtQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Contact cannot be empty").show();
            txtQty.selectAll();
            txtQty.requestFocus();
            return;
        }

        //upto now all fields are validated
        //let's do some business validations here...
        else if (ManageBookModel.existBookByIsbn(txtIsbn.getText())) {
            new Alert(Alert.AlertType.ERROR,"Member Already Exists").show();
            txtIsbn.selectAll();
            txtIsbn.requestFocus();
            return;
        }
        Book book=new Book(txtIsbn.getText(),txtTitle.getText(),txtAuthor.getText(),Integer.parseInt(txtQty.getText()));

        if(ManageBookModel.addBook(book)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Registered !").show();
            tblBooks.getItems().add(new BookTM(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQty()));
            txtIsbn.clear();
            txtTitle.clear();
            txtAuthor.clear();
            txtQty.clear();
            txtIsbn.requestFocus();
        }else{
            new Alert(Alert.AlertType.ERROR,"Failed to Save the member !").show();
        }


    }
}
