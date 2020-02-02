package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static ui.Controller.displayUserGuide;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("study-buddy.fxml"));
        primaryStage.setTitle("Study Buddy");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
        displayUserGuide();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
