package lk.ijse.gdse.controller.book;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.controller.ManageBooksFormController;
import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.BookService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.view.tm.BookTM;


public class AddBookFormController {
    public TextField txtIsbn;
    public TextField txtTitle;
    public TextField txtAuthor;
    public TextField txtQty;
    public Button btnRegister;

    public TableView<BookTM> tblBooks;

    public BookService bookService;

    public ManageBooksFormController manageBooksController;

    public void init(TableView<BookTM> tblBooks, ManageBooksFormController manageBooksController){
        this.tblBooks=tblBooks;
        this.bookService= ServiceFactory.getInstance().getService(ServiceTypes.BOOK);
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
            new Alert(Alert.AlertType.ERROR,"Title Cannot be empty or invalid").show();
            txtTitle.selectAll();
            txtTitle.requestFocus();
            return;
        }else if (txtAuthor.getText().isBlank() || !txtAuthor.getText().matches("^[A-Za-z ]+$")){
            new Alert(Alert.AlertType.ERROR,"Author name invalid or null").show();
            txtAuthor.selectAll();
            txtAuthor.requestFocus();
            return;
        }else if (txtQty.getText().isBlank() || !txtQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").show();
            txtQty.selectAll();
            txtQty.requestFocus();
            return;
        }

        BookDTO book=new BookDTO(txtIsbn.getText(),txtTitle.getText(),txtAuthor.getText(),Integer.parseInt(txtQty.getText()));

        try {
            if(bookService.saveBook(book)==null) {
                new Alert(Alert.AlertType.ERROR,"Failed to Save the member !").show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Registered !").show();
            tblBooks.getItems().add(new BookTM(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQty()));
            txtIsbn.clear();
            txtTitle.clear();
            txtAuthor.clear();
            txtQty.clear();
            txtIsbn.requestFocus();
        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,"MemberDTO Already Exists").show();
            txtIsbn.selectAll();
            txtIsbn.requestFocus();
        }



    }
}
