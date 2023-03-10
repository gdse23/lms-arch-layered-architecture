package lk.ijse.gdse.controller.book;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.gdse.controller.ManageBooksFormController;
import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.BookService;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.view.tm.BookTM;
import lk.ijse.gdse.view.tm.MemberTM;

import java.util.Optional;

public class UpdateBookFormController {
    public TextField txtTitle;
    public TextField txtAuthor;
    public TextField txtQty;
    public Button btnUpdate;
    public Button btnDelete;
    public Label lblIsbn;
    public BookTM bookTM;

    public BookService bookService;
    public ManageBooksFormController manageBooksController;


    public void init(BookTM bookTM,ManageBooksFormController manageBooksController){
        this.bookTM=bookTM;
        this.manageBooksController =manageBooksController;
        fillAllFields(bookTM);
        bookService= ServiceFactory.getInstance().getService(ServiceTypes.BOOK);
    }
    private void fillAllFields(BookTM bookTM){
        lblIsbn.setText(bookTM.getIsbn());
        txtTitle.setText(bookTM.getTitle());
        txtAuthor.setText(bookTM.getAuthor());
        txtQty.setText(bookTM.getQty()+"");
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete the member", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.YES){

            try {
                bookService.deleteBook(bookTM.getIsbn());
                new Alert(Alert.AlertType.INFORMATION,"Book delete successful").show();
                manageBooksController.tblBooks.getItems().
                        removeAll(manageBooksController.tblBooks.getSelectionModel().getSelectedItem());
                btnDelete.getScene().getWindow().hide();

            }catch (InUseException e){
                new Alert(Alert.AlertType.WARNING,"Book already issued some members, please return them before delete!").show();

            }catch (NotFoundException e){
                new Alert(Alert.AlertType.WARNING,"No book found for given isbn!").show();
            }

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //Data Validations

        if (txtTitle.getText().isBlank() || !txtTitle.getText().matches("^[A-Za-z0-9- ]+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid BookDTO title !").show();
            txtTitle.selectAll();
            txtTitle.requestFocus();
            return;
        }else if (txtAuthor.getText().isBlank() || !txtAuthor.getText().matches("^[A-Za-z ]+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Author's name").show();
            txtAuthor.selectAll();
            txtAuthor.requestFocus();
            return;
        }else if (txtQty.getText().isBlank() || !txtQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty").show();
            txtQty.selectAll();
            txtQty.requestFocus();
            return;
        }

        //Upto now all fields are validated

        BookDTO updatedBook = new BookDTO(bookTM.getIsbn(), txtTitle.getText(), txtAuthor.getText(), Integer.parseInt(txtQty.getText()));
        try {
            if(bookService.updateBook(updatedBook)!=null){
                int selectedIndex = manageBooksController.tblBooks.getSelectionModel()
                        .getSelectedIndex();
                manageBooksController.tblBooks.getItems()
                        .add(selectedIndex,new BookTM(updatedBook.getIsbn(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getQty()));
                manageBooksController.tblBooks.getItems().remove(selectedIndex+1);
                new Alert(Alert.AlertType.INFORMATION,"BookDTO has been successfully updated!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed to update the BookDTO details,try again!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.WARNING,"Book not found for given isbn!").show();
        }
    }
}
