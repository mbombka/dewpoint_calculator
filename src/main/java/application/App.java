package application;

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

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        MainView mainView = new MainView();

        // 1. Create the main view and add the high level layout
         Scene view = new Scene(mainView.getView(), 800, 600);
   
         // 2. Show the application
         stage.setScene(view);
         stage.setTitle("Savings calculator");
         stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}