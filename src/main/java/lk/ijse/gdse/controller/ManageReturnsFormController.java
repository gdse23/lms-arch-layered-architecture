package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.model.ManageIssueModel;
import lk.ijse.gdse.model.ManageReturnModel;
import lk.ijse.gdse.to.Return;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.ReturnTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ManageReturnsFormController {
    public JFXButton btnBack;
    public TableView<ReturnTM> tblReturns;
    public TextField txtIssueId;
    public JFXButton btnReturn;

    public void initialize(){
        tblReturns.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblReturns.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        List<ReturnTM> returnTMList = ManageReturnModel.getAllReturns().stream().map(aReturn -> new ReturnTM(aReturn.getIssueId(), aReturn.getDate())).collect(Collectors.toList());
        tblReturns.getItems().addAll(FXCollections.observableArrayList(returnTMList));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        if (txtIssueId.getText().isBlank() || !txtIssueId.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.WARNING,"Invalid Issue ID!").show();
            txtIssueId.selectAll();
            txtIssueId.requestFocus();
            return;
        }else if (!ManageIssueModel.existById(txtIssueId.getText())){
            new Alert(Alert.AlertType.WARNING,"Invalid Issue ID").show();
            txtIssueId.selectAll();
            txtIssueId.requestFocus();
            return;

        } else if (ManageReturnModel.existById(Integer.parseInt(txtIssueId.getText()))) {
            new Alert(Alert.AlertType.WARNING,"This ID has already returned !").show();
            txtIssueId.selectAll();
            txtIssueId.requestFocus();
            return;
        }

        //upto now all validated
        // let's save  to the database
        ManageReturnModel.saveReturn(new Return(Integer.parseInt(txtIssueId.getText()), Date.valueOf(LocalDate.now())));
        new Alert(Alert.AlertType.INFORMATION,"Book return has been successfully completed!").show();
        txtIssueId.clear();
        txtIssueId.requestFocus();

    }
}
