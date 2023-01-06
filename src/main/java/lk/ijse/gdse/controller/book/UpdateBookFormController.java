package lk.ijse.gdse.controller.book;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.gdse.controller.ManageBooksFormController;
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
    public ManageBooksFormController manageBooksController;


    public void init(BookTM bookTM,ManageBooksFormController manageBooksController){
        this.bookTM=bookTM;
        this.manageBooksController =manageBooksController;
        fillAllFields(bookTM);
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
            if(manageBooksController.deleteBookByIsbn(bookTM.getIsbn())) {
                new Alert(Alert.AlertType.INFORMATION,"Member delete successful").show();
                manageBooksController.tblBooks.getItems().
                        removeAll(manageBooksController.tblBooks.getSelectionModel().getSelectedItem());
                btnDelete.getScene().getWindow().hide();
            }else new Alert(Alert.AlertType.ERROR,"Failed to delete the book ,try again !").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //Data Validations

        if (txtTitle.getText().isBlank() || !txtTitle.getText().matches("^[A-Za-z0-9- ]+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Book title !").show();
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

        BookTM updatedBook = new BookTM(bookTM.getIsbn(), txtTitle.getText(), txtAuthor.getText(), Integer.parseInt(txtQty.getText()));
        if(manageBooksController.updateBook(updatedBook)){
            int selectedIndex = manageBooksController.tblBooks.getSelectionModel()
                    .getSelectedIndex();
            manageBooksController.tblBooks.getItems()
                    .add(selectedIndex,updatedBook);
            manageBooksController.tblBooks.getItems().remove(selectedIndex+1);
            new Alert(Alert.AlertType.INFORMATION,"Book has been successfully updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Failed to update the Book details,try again!").show();
        }
    }
}
