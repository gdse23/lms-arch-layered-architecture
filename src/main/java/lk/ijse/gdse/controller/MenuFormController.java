package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;

import java.io.IOException;

public class MenuFormController {
    public JFXButton btnMembers;
    public JFXButton btnBooks;
    public JFXButton btnIssues;
    public JFXButton btnReturn;
    public AnchorPane pneContainer;

    public void btnMembersOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MEMBER);
    }

    public void btnBooksOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.BOOK);
    }

    public void btnIssuesOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.ISSUE);
    }

    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.RETURN);
    }
}
