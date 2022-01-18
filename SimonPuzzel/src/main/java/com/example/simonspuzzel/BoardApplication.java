package com.example.simonspuzzel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *Board Application : Application class for initializing the fxml file , creating scene and launch our application
 *
 * Created By: Mansoor
 *
 */
public class BoardApplication extends Application {

    /**
     * Start method of application to initialize the fxml file and create the scene
     *
     * @param stage - stage
     */
    @Override
    public void start(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader();
            //loads the BoardGrid.fxml file
            Parent root =
                    loader.load(getClass().getResource("BoardGrid.fxml").openStream());
            //Creates a new scene
            Scene scene = new Scene(root,900,700);
            //adds some css styles to the fonts
            scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Main method to launch the application
     *
     * @param args - args
     */
    public static void main(String[] args) {
        launch();
    }
}