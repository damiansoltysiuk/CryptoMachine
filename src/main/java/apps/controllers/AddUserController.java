package apps.controllers;

import apps.User;
import apps.utils.CSVUtils;
import apps.utils.DialogUtils;
import apps.utils.JDOMUtils;
import apps.utils.JSONUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    User user;
    @FXML
    private TextField loginTA;

    @FXML
    private PasswordField loginPF;

    @FXML
    private TextField emailTA;

    @FXML
    private PasswordField emailPF;

    @FXML
    private ToggleButton AtBashCipher;

    @FXML
    private ToggleGroup cipherMethod;

    @FXML
    private ToggleButton CesarCipher;

    @FXML
    private ToggleButton MatrixCipher;

    @FXML
    private ToggleButton Rot13Cipher;

    @FXML
    private ToggleButton VigenereCipher;

    @FXML
    void addUserJSON(ActionEvent event) {
        user = personalityUserSetting();
        JSONUtils.writeJSONData(user, "src/main/resources/dbfile/" + loginTA.getText() + ".txt");
    }

    @FXML
    void addUserXML() {
       user = personalityUserSetting();
       JDOMUtils.writeJDOMData(user, "src/main/resources/dbfile/" + loginTA.getText() + ".xml");
    }

    @FXML
    void addUserCSV() {
        user = personalityUserSetting();
        CSVUtils.writeCSVData(user, "src/main/resources/dbfile/" + loginTA.getText() + ".csv");
    }

    @FXML
    User personalityUserSetting() {
        String method = "";
        if(AtBashCipher.isSelected()) method = "AtBash";
        else if(CesarCipher.isSelected()) method = "Cesar";
        else if(MatrixCipher.isSelected()) method = "Matrix";
        else if(Rot13Cipher.isSelected()) method = "Rot13";
        else method = "Vigenere";
        return new User(loginTA.getText(), loginPF.getText(), emailTA.getText(), emailPF.getText(), method);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginTA.setPromptText("username");
        loginPF.setPromptText("user password");
        emailTA.setPromptText("email");
        emailPF.setPromptText("email password");
    }
}
