package lk.ijse.gdse.controller;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.util.Navigation;
import lk.ijse.gdse.util.Route;

import java.io.IOException;

public class MainFormController {
    public AnchorPane pneContainer;
    public ImageView imgLogo;

    public void imgLogoOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Route.WELCOME);
    }
}
