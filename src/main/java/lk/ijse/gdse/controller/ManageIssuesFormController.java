package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.model.ManageBookModel;
import lk.ijse.gdse.model.ManageIssueModel;
import lk.ijse.gdse.model.ManageMemberModel;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.IssueTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

public class ManageIssuesFormController {
    public JFXButton btnBack;
    public TableView<IssueTM> tblIssues;
    public TextField txtIsbn;
    public TextField txtMemberId;
    public JFXButton btnIssue;
    public JFXButton btnUpdate;

    public void initialize(){
        btnUpdate.setDisable(true);
        tblIssues.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblIssues.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblIssues.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblIssues.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));

        tblIssues.getItems().addAll(FXCollections.observableArrayList(ManageIssueModel.findAllIssues()));

        tblIssues.getSelectionModel().selectedItemProperty().addListener((observableValue, prev, curr)->{

            btnUpdate.setDisable(curr==null);
            if (curr==null){
                txtIsbn.clear();
                txtMemberId.clear();
            }
            if (prev!=curr && curr!=null){
                txtMemberId.setText(curr.getMemberId());
                txtIsbn.setText(curr.getIsbn());
            }
        });

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnIssueOnAction(ActionEvent actionEvent) {
        if (tblIssues.getSelectionModel().getSelectedItem()!=null) {
            tblIssues.getSelectionModel().clearSelection();
            return;
        }
        Optional<Alert> alert = validateFields(txtIsbn.getText(), txtMemberId.getText());
        if (alert.isPresent()){
            txtMemberId.requestFocus();
            alert.get().show();
            return;
        }
        IssueTM savedIssue = ManageIssueModel.saveIssue(new IssueTM(txtIsbn.getText(), txtMemberId.getText(), Date.valueOf(LocalDate.now())));
        new Alert(Alert.AlertType.INFORMATION,"Successfully issued !").show();
        txtIsbn.clear();
        txtMemberId.clear();
        txtMemberId.requestFocus();
        tblIssues.getItems().addAll(savedIssue);

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Optional<Alert> alert = validateFields(txtIsbn.getText(), txtMemberId.getText());
        if (alert.isPresent()) {
            alert.get().show();
            return;
        }
        IssueTM selectedItem = tblIssues.getSelectionModel().getSelectedItem();
        int selectedIndex = tblIssues.getSelectionModel().getSelectedIndex();
        selectedItem.setIsbn(txtIsbn.getText());
        selectedItem.setMemberId(txtMemberId.getText());
        ManageIssueModel.updateIssue(selectedItem);
        txtIsbn.clear();
        txtMemberId.clear();
        tblIssues.getSelectionModel().clearSelection();
        tblIssues.getItems().add(selectedIndex,selectedItem);
        tblIssues.getItems().remove(selectedIndex+1);
        tblIssues.refresh();

    }

    public Optional<Alert> validateFields(String isbn, String memberId){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (isbn.isBlank() || !isbn.matches("^\\d{13}$")) {
            alert.setHeaderText("Invalid isbn");
            return Optional.of(alert);
        }

        else if (memberId.isBlank()|| !memberId.matches("^M\\d{3}$")) {
            alert.setHeaderText("Invalid Member Id");
            return Optional.of(alert);
        }else if (!ManageMemberModel.existMemberById(txtMemberId.getText())) {
            alert.setHeaderText("Member does not exists!");
            return Optional.of(alert);
        } else if (!ManageBookModel.existBookByIsbn(txtIsbn.getText())) {
            alert.setHeaderText("Book does not found!");
            return Optional.of(alert);
        }

        return Optional.empty();
    }
}
