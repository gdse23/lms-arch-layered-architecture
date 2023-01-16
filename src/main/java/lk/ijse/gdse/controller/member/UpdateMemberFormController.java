package lk.ijse.gdse.controller.member;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.model.ManageMemberModel;
import lk.ijse.gdse.to.Member;
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
    public ManageMembersFormController manageMembersController;


    public void init(MemberTM memberTM,ManageMembersFormController manageMembersFormController){
        this.memberTM=memberTM;
        this.manageMembersController =manageMembersFormController;
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
        Member updatedMember = new Member(memberTM.getId(), txtName.getText(), txtAddress.getText(), txtContact.getText());
        if(ManageMemberModel.updateMember(updatedMember)){
            int selectedIndex = manageMembersController.tblMembers.getSelectionModel()
                    .getSelectedIndex();
            manageMembersController.tblMembers.getItems()
                    .add(selectedIndex,new MemberTM(updatedMember.getId(), updatedMember.getName(), updatedMember.getAddress(), updatedMember.getContact()));
            manageMembersController.tblMembers.getItems().remove(selectedIndex+1);
            new Alert(Alert.AlertType.INFORMATION,"MemberDTO has been successfully updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Failed to update the MemberDTO,try again!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (ManageMemberModel.getIssuedBooksCountByMemberId(memberTM.getId())>0){
            new Alert(Alert.AlertType.WARNING,"MemberDTO already borrowed some books, ReturnDTO items first and try again!").show();
            txtContact.getScene().getWindow().hide();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete the member", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.YES){
            ManageMemberModel.deleteMemberById(memberTM.getId());
            new Alert(Alert.AlertType.INFORMATION,"MemberDTO delete successful").show();
            manageMembersController.tblMembers.getItems().
                    removeAll(manageMembersController.tblMembers.getSelectionModel().getSelectedItem());
            btnDelete.getScene().getWindow().hide();
        }
    }
}
