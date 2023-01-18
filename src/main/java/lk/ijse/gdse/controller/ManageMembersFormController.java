package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.controller.member.AddMemberFormController;
import lk.ijse.gdse.controller.member.ShowIssueListFormController;
import lk.ijse.gdse.controller.member.UpdateMemberFormController;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.IssueTM;
import lk.ijse.gdse.view.tm.MemberTM;
import lk.ijse.gdse.view.tm.Status;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageMembersFormController {
    public JFXButton btnBack;
    public Button btnAddMember;
    public TextField txtSearchMember;
    public TableView<MemberTM> tblMembers;
    public Button btnUpdateDelete;
    public Button btnIssueList;

    public MemberService memberService;

    public void initialize() throws SQLException, ClassNotFoundException {
        this.memberService= ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);
        btnUpdateDelete.setDisable(true);
        btnIssueList.setDisable(true);
        tblMembers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMembers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMembers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblMembers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        List<MemberTM> memberTMList = memberService.findAllMembers().stream().map(member -> new MemberTM(member.getId(), member.getName(), member.getAddress(), member.getContact())).collect(Collectors.toList());

        tblMembers.setItems(FXCollections.observableArrayList(memberTMList));

        txtSearchMember.textProperty().addListener((observableValue, pre, curr) ->{
            if (!Objects.equals(pre, curr)){
                tblMembers.getItems().clear();
                List<MemberTM> searchResult = memberService.searchMembersByText(curr).stream().map(member -> new MemberTM(member.getId(), member.getName(), member.getAddress(), member.getContact())).collect(Collectors.toList());
                tblMembers.setItems(FXCollections.observableArrayList(searchResult));
            }

        } );

        tblMembers.getSelectionModel().selectedItemProperty().addListener((observableValue, pre, curr) -> {
            if (curr!=pre || curr!=null){
                btnUpdateDelete.setDisable(false);
                btnIssueList.setDisable(false);
            }

        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }

    public void btnUpdateDeleteOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/member/UpdateMemberForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        UpdateMemberFormController controller = fxmlLoader.getController();
        controller.init(tblMembers.getSelectionModel().getSelectedItem(),this);
        Stage stage = new Stage();
        stage.setTitle("Update/Delete MemberDTO details");
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnIssuedListOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/member/ShowIssueListForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        ShowIssueListFormController controller = fxmlLoader.getController();
        controller.init(tblMembers.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setTitle("Issued books list");
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnAddMemberOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/member/AddMemberForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        AddMemberFormController AddMemberController = fxmlLoader.getController();
        AddMemberController.init(tblMembers);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New User Registration Form");
        stage.centerOnScreen();
        stage.show();
    }


}
