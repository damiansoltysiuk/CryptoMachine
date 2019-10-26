package apps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("SDA Enigma Machine");
        stage.setScene(scene);
        stage.show();
    }

    //TODO
    // wyskakujące okienko z podaniem adresu email,
    // klucz w metodzie vigenere
    public static void main(String[] args) {
        launch(args);
    }
}
