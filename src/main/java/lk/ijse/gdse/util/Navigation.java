package lk.ijse.gdse.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class Navigation {

    public static AnchorPane pneContainer;

    public static void init(AnchorPane pneContainer){
        Navigation.pneContainer=pneContainer;
    }

    public static void navigate(Route route) throws IOException {
        pneContainer.getChildren().clear();
         Stage container = (Stage)pneContainer.getScene().getWindow();
        URL resource=null;
        switch (route){
            case WELCOME:
                resource= Navigation.class.getResource("/view/WelcomeForm.fxml");
                container.setTitle("Welcome to LMS v1.0.0");
                break;

            case MEMBER:
                resource= Navigation.class.getResource("/view/ManageMembersForm.fxml");
                container.setTitle("Manage Members");
                break;

            case BOOK:
                resource= Navigation.class.getResource("/view/ManageBooksForm.fxml");
                container.setTitle("Manage Books");
                break;

            case RETURN:
                resource= Navigation.class.getResource("/view/ManageReturnsForm.fxml");
                container.setTitle("Handle Returns");
                break;

            case ISSUE:
                resource= Navigation.class.getResource("/view/ManageIssuesForm.fxml");
                container.setTitle("Manage Issues");
                break;

            case MENU:
                resource= Navigation.class.getResource("/view/MenuForm.fxml");
                container.setTitle("Menu- LMS");
                break;

        }


        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        pneContainer.getChildren().addAll(load);
    }



}
