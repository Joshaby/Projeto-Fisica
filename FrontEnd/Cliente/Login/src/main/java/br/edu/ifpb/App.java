package br.edu.ifpb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private Stage stage;

    private Scene scene;

    public App() throws IOException {
        Parent loginParent = new FXMLLoader(App.class.getResource("Login.fxml")).load();
        this.scene = new Scene(loginParent);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
