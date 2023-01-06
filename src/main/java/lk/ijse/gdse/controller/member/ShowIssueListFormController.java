package lk.ijse.gdse.controller.member;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.view.tm.IssueTM;
import lk.ijse.gdse.view.tm.MemberTM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowIssueListFormController {
    
    public MemberTM selectedMember;
    
    public  ManageMembersFormController manageMemberController;
    public TableView<IssueTM> tblIssueList;
    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public void init(MemberTM selectedItem, ManageMembersFormController manageMemberController) {
        this.selectedMember=selectedItem;
        this.manageMemberController=manageMemberController;
        initFields(selectedMember);

        tblIssueList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblIssueList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblIssueList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));

        tblIssueList.getItems().addAll(FXCollections.observableArrayList(manageMemberController.findAllIssuesById(selectedMember.getId())));

        
    }

    private void initFields(MemberTM selectedMember) {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        lblId.setText(selectedMember.getId());
        lblName.setText(selectedMember.getName());
    }
}
