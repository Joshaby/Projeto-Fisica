package br.edu.ifpb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage stage;
    private static Scene loginScene;
    private static Scene menuScene;

    @Override
    public void start(Stage Primarystage) throws IOException {
        stage = Primarystage;

        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        loginScene = new Scene(loginParent, 258, 250);

        Parent menuParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        menuScene = new Scene(menuParent, 750, 400);


        Primarystage.setScene(loginScene);
        Primarystage.setResizable(false);
        Primarystage.show();
    }

    public static void SelectScreen(String fxml){
        switch (fxml){
            case "Login":
                stage.setScene(loginScene);
                stage.hide();
                stage.show();
                break;
            case "Menu":
                stage.setScene(menuScene);
                stage.hide();
                stage.show();
                break;
        }
    }


    public static void main(String[] args) {
        launch();
    }

}