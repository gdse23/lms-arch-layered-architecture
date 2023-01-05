package lk.ijse.gdse.controller.member;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.view.tm.MemberTM;

import java.util.Optional;

public class UpdateMemberFormController {
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public Button btnUpdate;
    public Button btnDelete;
    public Label lblId;

    public MemberTM memberTM;
    public ManageMembersFormController manageMembersFormController;


    public void init(MemberTM memberTM,ManageMembersFormController manageMembersFormController){
        this.memberTM=memberTM;
        this.manageMembersFormController=manageMembersFormController;
        fillAllFields(memberTM);
    }
    private void fillAllFields(MemberTM memberTM){
        lblId.setText(memberTM.getId());
        txtName.setText(memberTM.getName());
        txtAddress.setText(memberTM.getAddress());
        txtContact.setText(memberTM.getContact());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (txtName.getText().isBlank() || !txtName.getText().matches("^[A-Za-z ]+$")){
            new Alert(Alert.AlertType.ERROR,"Name cannot be empty").show();
            txtName.selectAll();
            txtName.requestFocus();
            return;
        }else if (txtAddress.getText().isBlank() || !txtAddress.getText().matches("^[A-Za-z ]+$")){
            new Alert(Alert.AlertType.ERROR,"Address cannot be empty").show();
            txtAddress.selectAll();
            txtAddress.requestFocus();
            return;
        }else if (txtContact.getText().isBlank() || !txtContact.getText().matches("^\\d{3}-\\d{7}$")){
            new Alert(Alert.AlertType.ERROR,"Contact cannot be empty").show();
            txtContact.selectAll();
            txtContact.requestFocus();
            return;
        }

        //upto now all fields are validated
        MemberTM updatedMemberTM = new MemberTM(memberTM.getId(), txtName.getText(), txtAddress.getText(), txtContact.getText());
        if(manageMembersFormController.updateMember(updatedMemberTM)){
            manageMembersFormController.tblMembers.getItems()
                    .add(manageMembersFormController.tblMembers.getSelectionModel()
                            .getSelectedIndex(),new MemberTM(memberTM.getId(), txtName.getText(),txtAddress.getText(),txtContact.getText()));
            new Alert(Alert.AlertType.INFORMATION,"Member has been successfully updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Failed to update the Member,try again!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (manageMembersFormController.getIssuedBooksCountByMemberId(memberTM.getId())>0){
            new Alert(Alert.AlertType.WARNING,"Member already borrowed some books, Return items first and try again!").show();
            txtContact.getScene().getWindow().hide();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete the member", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.YES){
            if(manageMembersFormController.deleteMemberById(memberTM.getId())) {
                new Alert(Alert.AlertType.INFORMATION,"Member delete successful").show();
                manageMembersFormController.tblMembers.getItems().
                        removeAll(manageMembersFormController.tblMembers.getSelectionModel().getSelectedItem());
                btnDelete.getScene().getWindow().hide();
            }else new Alert(Alert.AlertType.ERROR,"Failed to delete the member ,try again !").show();
        }
    }
}
