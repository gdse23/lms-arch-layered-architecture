package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;

import java.io.IOException;

public class WelcomeFormController {
    public JFXButton btnMenu;

    public void btnMenuOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.MENU);
    }
}
