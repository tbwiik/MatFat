package matFat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MatFatApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("MatFat App");
        // primaryStage.setScene(new
        // Scene(FXMLLoader.load(getClass().getResource("MainPage.fxml"))));
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("EditMeal.fxml"))));
        primaryStage.show();
    }

}
