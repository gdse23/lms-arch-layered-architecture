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
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.IssueService;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.IssueTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageIssuesFormController {
    public JFXButton btnBack;
    public TableView<IssueTM> tblIssues;
    public TextField txtIsbn;
    public TextField txtMemberId;
    public JFXButton btnIssue;
    public JFXButton btnUpdate;

    public IssueService issueService;

    public void initialize(){

        this.issueService= ServiceFactory.getInstance().getService(ServiceTypes.ISSUE);
        btnUpdate.setDisable(true);
        tblIssues.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblIssues.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblIssues.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblIssues.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        List<IssueTM> issueTMList = issueService.findAllIssues().stream().map(issue -> new IssueTM(issue.getIssueId(), issue.getIsbn(), issue.getMemberId(), issue.getDate())).collect(Collectors.toList());
        tblIssues.getItems().addAll(FXCollections.observableArrayList(issueTMList));
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
        IssueDTO savedIssue = issueService.createIssue(new IssueDTO(txtIsbn.getText(), txtMemberId.getText(), Date.valueOf(LocalDate.now())));
        new Alert(Alert.AlertType.INFORMATION,"Successfully issued !").show();
        txtIsbn.clear();
        txtMemberId.clear();
        txtMemberId.requestFocus();
        tblIssues.getItems().addAll(new IssueTM(savedIssue.getIssueId(), savedIssue.getIsbn(), savedIssue.getMemberId(),savedIssue.getDate()));

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

        try {
            issueService.updateIssue(new IssueDTO(selectedItem.getIssueId(),selectedItem.getIsbn(), selectedItem.getMemberId(), selectedItem.getDate()));
            txtIsbn.clear();
            txtMemberId.clear();
            tblIssues.getSelectionModel().clearSelection();
            tblIssues.getItems().add(selectedIndex,selectedItem);
            tblIssues.getItems().remove(selectedIndex+1);
            tblIssues.refresh();

        }catch (NotFoundException e){
            new Alert(Alert.AlertType.WARNING,"No issue found for given ID!").show();
        }





    }

    public Optional<Alert> validateFields(String isbn, String memberId){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (isbn.isBlank() || !isbn.matches("^\\d{13}$")) {
            alert.setHeaderText("Invalid isbn");
            return Optional.of(alert);
        }

        else if (memberId.isBlank()|| !memberId.matches("^M\\d{3}$")) {
            alert.setHeaderText("Invalid MemberDTO Id");
            return Optional.of(alert);
        }

        return Optional.empty();
    }
}
