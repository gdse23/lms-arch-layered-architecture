package lk.ijse.gdse.controller.book;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.controller.ManageBooksFormController;
import lk.ijse.gdse.controller.ManageMembersFormController;
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
    }
}
