package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.Status;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.IssueService;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.ReturnTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.gdse.dto.Status.RETURNED;

public class ManageReturnsFormController {
    public JFXButton btnBack;
    public TableView<ReturnTM> tblReturns;
    public TextField txtIssueId;
    public JFXButton btnReturn;

    public IssueService issueService;

    public void initialize(){
        this.issueService= ServiceFactory.getInstance().getService(ServiceTypes.ISSUE);

        tblReturns.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblReturns.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        List<ReturnTM> returnTMList = issueService.findAllReturns().stream().map(aReturn -> new ReturnTM(aReturn.getIssueId(), aReturn.getDate())).collect(Collectors.toList());
        tblReturns.getItems().addAll(FXCollections.observableArrayList(returnTMList));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        if (txtIssueId.getText().isBlank() || !txtIssueId.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.WARNING,"Invalid IssueDTO ID!").show();
            txtIssueId.selectAll();
            txtIssueId.requestFocus();
            return;
        }
        IssueDTO issue = issueService.findByIssueId(Integer.parseInt(txtIssueId.getText()));

        if (issue==null) {
            new Alert(Alert.AlertType.WARNING,"No issue found for given issue id").show();
            txtIssueId.selectAll();
            txtIssueId.requestFocus();
            return;

        }

        //upto now all validated
        // let's save  to the database
        issueService.returnIssue(Integer.parseInt(txtIssueId.getText()));
        new Alert(Alert.AlertType.INFORMATION,"BookDTO return has been successfully completed!").show();
        txtIssueId.clear();
        txtIssueId.requestFocus();

    }
}
