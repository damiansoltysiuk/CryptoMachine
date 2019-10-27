package apps.utils;

import apps.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class DialogUtils {
    public User dialogLogin(Set<User> usersSet) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Write your username and password");

        ImageView loginImage = new ImageView(this.getClass().getResource("/icons/login.png").toString());
        loginImage.setFitWidth(30);
        loginImage.setFitHeight(30);
        dialog.setGraphic(loginImage);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        while (true) {
            Platform.runLater(() -> username.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });
//
            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(usernamePassword -> {
                System.out.println(usernamePassword.getKey() + " " + usernamePassword.getValue());
            });

            User loggerUser = SearchUserInUS(result.get().getKey(), result.get().getValue(), usersSet);
            if (loggerUser != null) return loggerUser;

            dialogIncorrectData();
        }
    }

    public User SearchUserInUS(String name, String pass, Set<User> userList) {
        for (User u : userList) {
            if (name.equals(u.getLogin()) && pass.equals(u.getLoginPass())) return u;
        }
        return null;
    }

    public static void dialogIncorrectData() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Incorrect username or password");
        alert.showAndWait();
    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error!");
        errorAlert.setHeaderText("Exception problem");

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public void addUserDialog() {
        Alert addUserAlert = new Alert(Alert.AlertType.INFORMATION);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AddUser.fxml"));
        VBox vBox;
        try {
            vBox = loader.load();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
            vBox = null;
        }
        addUserAlert.getDialogPane().setContent(vBox);
        addUserAlert.showAndWait();

    }
}
