package lk.ijse.gdse.controller.member;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.ManageMembersFormController;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.service.ServiceFactory;
import lk.ijse.gdse.service.ServiceTypes;
import lk.ijse.gdse.service.custom.IssueService;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.view.tm.IssueTM;
import lk.ijse.gdse.view.tm.MemberTM;
import lk.ijse.gdse.view.tm.Status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShowIssueListFormController {
    
    public MemberTM selectedMember;
    
    public  ManageMembersFormController manageMemberController;
    public TableView<IssueTM> tblIssueList;
    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public MemberService memberService;


    public void init(MemberTM selectedItem) {

        memberService= ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);

        this.selectedMember=selectedItem;
        initFields(selectedMember);

        tblIssueList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblIssueList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblIssueList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));


        tblIssueList.getItems().addAll(FXCollections.observableArrayList(memberService.
                findAllIssuesByMemberId(selectedMember.getId()).stream().
                map(issueDTO ->new IssueTM(issueDTO.getIssueId(),issueDTO.getIsbn(), issueDTO.getMemberId(),issueDTO.getDate(),
                        Status.valueOf(String.valueOf(issueDTO.getStatus())))).collect(Collectors.toList())));

    }

    private void initFields(MemberTM selectedMember) {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        lblId.setText(selectedMember.getId());
        lblName.setText(selectedMember.getName());
    }
}
