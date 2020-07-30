package br.edu.ifpb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private PasswordField passwordField;
    @FXML
    protected Label infos;


    @FXML
    public void Login(ActionEvent event) throws IOException {
        if(user.getCharacters().toString().equals("admin") && passwordField.getCharacters().toString().equals("123")) {
            App.SelectScreen("Menu");
        }
        else {
            infos.setText("Informações incorretas");
        }
    }
}
