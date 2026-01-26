package po.samochog_gui;

import javafx.scene.Parent;
import symulator.Samochod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Stage creatorStage = new Stage();

        FXMLLoader creatorLoader = new FXMLLoader(HelloApplication.class.getResource("Car-Creator.fxml"));
        Parent creatorRoot = creatorLoader.load();

        creatorStage.setTitle("Kreator Samochodu");
        creatorStage.setScene(new Scene(creatorRoot));
        creatorStage.setMaximized(true);


        creatorStage.showAndWait();


        FXMLLoader mainLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent mainRoot = mainLoader.load();

        primaryStage.setTitle("Symulator - Program Główny");
        primaryStage.setScene(new Scene(mainRoot));
        primaryStage.setMaximized(true);

        // Pokazujemy główne okno
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}