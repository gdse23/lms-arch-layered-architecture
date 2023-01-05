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
import lk.ijse.gdse.controller.member.UpdateMemberFormController;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;
import lk.ijse.gdse.view.tm.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ManageMembersFormController {
    public JFXButton btnBack;
    public Button btnAddMember;
    public TextField txtSearchMember;
    public TableView<MemberTM> tblMembers;
    public Button btnUpdateDelete;
    public Button btnIssueList;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnUpdateDelete.setDisable(true);
        btnIssueList.setDisable(true);
        tblMembers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMembers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMembers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblMembers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblMembers.setItems(FXCollections.observableArrayList(getAllMembers()));

        txtSearchMember.textProperty().addListener((observableValue, pre, curr) ->{
            if (!Objects.equals(pre, curr)){
                tblMembers.getItems().clear();
                tblMembers.setItems(FXCollections.observableArrayList(searchMembers(curr)));
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
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnIssuedListOnAction(ActionEvent actionEvent) {
    }

    public void btnAddMemberOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/member/AddMemberForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent load = fxmlLoader.load();
        AddMemberFormController AddMemberController = fxmlLoader.getController();
        AddMemberController.init(tblMembers,this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New User Registration Form");
        stage.centerOnScreen();
        stage.show();
    }



    //Business Logics and data

    public List<MemberTM> getAllMembers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member");
        ResultSet rst = stm.executeQuery();
        List<MemberTM> memberList =new ArrayList<>();
        while (rst.next()){
            MemberTM memberTM = new MemberTM(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
            memberList.add(memberTM);
        }
        return memberList;
    }

    public List<MemberTM> searchMembers(String searchText){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id LIKE ? OR  name LIKE ? OR address LIKE ? OR contact LIKE ?");
            searchText="%"+searchText+"%";
            stm.setString(1,searchText);
            stm.setString(2,searchText);
            stm.setString(3,searchText);
            stm.setString(4,searchText);
            ResultSet rst = stm.executeQuery();
            List<MemberTM> memberList=new ArrayList<>();
            while (rst.next()){
                MemberTM memberTM = new MemberTM(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
                memberList.add(memberTM);
            }
            return memberList;
        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addMember(MemberTM memberTM) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Member (id, name, address, contact) VALUES (?,?,?,?)");
        stm.setString(1,memberTM.getId());
        stm.setString(2,memberTM.getName());
        stm.setString(3,memberTM.getAddress());
        stm.setString(4,memberTM.getContact());
        return stm.executeUpdate()==1;
    }


    public boolean existMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Member WHERE id=?");
            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            return rst.next();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean updateMember(MemberTM memberTM){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE Member SET name=? ,address=? ,contact=? WHERE id=?");
            stm.setString(1,memberTM.getName());
            stm.setString(2,memberTM.getAddress());
            stm.setString(3,memberTM.getContact());
            stm.setString(4,memberTM.getId());

            return stm.executeUpdate()==1;


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteMemberById(String memberId){
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE  FROM Member WHERE id=?");
            stm.setString(1,memberId);
            return stm.executeUpdate()==1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //let's try some methods which has join queries
    public int getIssuedBooksCountByMemberId(String memberId){
        try {
            Connection connection= DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT (COUNT(i.issue_id)-COUNT(R.issue_id))  AS borrowedItems FROM Member m LEFT JOIN issue i on m.id = i.memberId\n" +
                    "LEFT JOIN `Return` R on i.issue_id = R.issue_id\n" +
                    "WHERE m.id=?\n" +
                    "GROUP BY m.id");

            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getInt("borrowedItems");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
