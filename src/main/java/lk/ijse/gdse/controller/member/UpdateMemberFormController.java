package lk.ijse.gdse.controller.member;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.model.ManageMemberModel;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.to.Member;
import lk.ijse.gdse.view.tm.MemberTM;

import java.sql.SQLException;
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

    public MemberService memberService;


    public void init(MemberTM memberTM,ManageMembersFormController manageMembersFormController){

        this.memberService= ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);
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
        Member updatedMember = new Member(memberTM.getId(), txtName.getText(), txtAddress.getText(), txtContact.getText());

        try {
            memberService.updateMember(new MemberDTO(updatedMember.getId(), updatedMember.getName(), updatedMember.getAddress(), updatedMember.getContact()));
            int selectedIndex = manageMembersController.tblMembers.getSelectionModel()
                    .getSelectedIndex();
            manageMembersController.tblMembers.getItems()
                    .add(selectedIndex,new MemberTM(updatedMember.getId(), updatedMember.getName(), updatedMember.getAddress(), updatedMember.getContact()));
            manageMembersController.tblMembers.getItems().remove(selectedIndex+1);
            new Alert(Alert.AlertType.INFORMATION,"MemberDTO has been successfully updated!").show();

        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        try {

            Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete the member", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get()==ButtonType.YES){
                memberService.deleteMember(memberTM.getId());
                new Alert(Alert.AlertType.INFORMATION,"MemberDTO delete successful").show();
                manageMembersController.tblMembers.getItems().
                        removeAll(manageMembersController.tblMembers.getSelectionModel().getSelectedItem());
                btnDelete.getScene().getWindow().hide();
            }

        }catch (NotFoundException e){
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
            txtContact.getScene().getWindow().hide();
            return;
        }catch (InUseException e){
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
            txtContact.getScene().getWindow().hide();
            return;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
