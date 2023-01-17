package lk.ijse.gdse.controller.member;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.model.ManageMemberModel;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.to.Member;
import lk.ijse.gdse.view.tm.MemberTM;

import java.sql.SQLException;

public class AddMemberFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public Button btnRegister;

    public MemberService memberService;

    public TableView<MemberTM> tblMembers;

    public void init(TableView<MemberTM> tblMembers){
        this.tblMembers=tblMembers;
        this.memberService= ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);
    }


    public void btnRegisterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //Data validation
        if (txtID.getText().isBlank() || !txtID.getText().matches("M\\d{3}")) {
            new Alert(Alert.AlertType.ERROR,"MemberDTO ID empty or invalid").show();
            txtID.selectAll();
            txtID.requestFocus();
            return;
        }
        else if (txtName.getText().isBlank() || !txtName.getText().matches("^[A-Za-z ]+$")){
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

        MemberDTO member=new MemberDTO(txtID.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText());

        try {
            MemberDTO memberDTO = memberService.saveMember(member);
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Registered !").show();
            tblMembers.getItems().add(new MemberTM(memberDTO.getId(), memberDTO.getName(), memberDTO.getAddress(), memberDTO.getContact()));
            txtID.clear();
            txtName.clear();
            txtAddress.clear();
            txtContact.clear();
            txtID.requestFocus();
        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
}
