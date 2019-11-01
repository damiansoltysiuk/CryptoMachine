package apps.controllers;

import apps.User;
import apps.utils.DialogUtils;
import apps.utils.EmailUtils;
import cipherMethod.FactoryCipher;
import cipherMethod.ICipherMethod;
import cipherMethod.Vigenere;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController implements Initializable {
    private String method;
    private String cipherMethod;
    private User loginUser;
    private Set<User> userSet;
    private DialogUtils dialogUtils;
    private String path = "/home/damian/Pulpit/code.pdf";
    private String pathCrpt = "/home/damian/Pulpit/msg.crpt";


    @FXML
    private RadioButton decode;

    @FXML
    void selectMethod(ActionEvent event) {
        method = decode.isSelected() ? "decode" : "encode";
    }

    @FXML
    private TextArea textArea;

    public String getTextArea() {
        return textArea.getText();
    }

    @FXML
    void runAction() {
        String message = textArea.getText();
        cipherMethod = listCipherMethod.getValue();
        ICipherMethod cipher = FactoryCipher.getInstance().makeCipher(cipherMethod);
        if (cipher instanceof Vigenere) {
            TextInputDialog dialog = new TextInputDialog("key");
            dialog.setTitle("Key");
            dialog.setHeaderText("Please write the key");
            dialog.setContentText("Key: ");
            Optional<String> result = dialog.showAndWait();
            cipher.setText(result.get());
        }
        cipher.setText(message);
        switch (method) {
            case "decode":
                textArea.setText(cipher.decode());
                break;
            case "encode":
                textArea.setText(cipher.encode());
                break;
            default: {
                textArea.setText("Method problem:\nError! Illegal argument exception");
                throw new IllegalArgumentException();
            }
        }
        if (cipher instanceof Vigenere) cipher.setText("key");

    }

    @FXML
    private TextField outputFilePath;

    @FXML
    void saveAction() {
        System.out.println("SaveAction");
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        fc.setInitialFileName("message.crpt");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CryptFile", "*.crpt"),
                new FileChooser.ExtensionFilter("TXT File", "*.txt"));
        File selectedFile = fc.showSaveDialog(null);

        if (selectedFile != null) {
            outputFilePath.setText(selectedFile.getAbsolutePath());
            fc.setInitialDirectory(selectedFile.getParentFile());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(selectedFile)))) {
                bw.write(textArea.getText());
                System.out.println("Save file successful");
            } catch (IOException e) {
                DialogUtils.errorDialog(e.getMessage());
            }
        } else {
            DialogUtils.errorDialog("File is not valid");
        }
    }

    @FXML
    private TextField inputFilePath;

    @FXML
    void loadAction() {
        System.out.println("LoadAction");
        FileChooser fc = new FileChooser();
        fc.setTitle("Load file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT File", "*.txt"),
                new FileChooser.ExtensionFilter("CryptFile", "*.crpt"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            inputFilePath.setText(selectedFile.getAbsolutePath());
            StringBuilder valueOfFile = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(selectedFile)))) {
                String line = br.readLine();
                while (line != null) {
                    valueOfFile.append(line).append("\n");
                    line = br.readLine();
                }
                textArea.setText(valueOfFile.toString());
                System.out.println("Load text successful!");
            } catch (FileNotFoundException e) {
                DialogUtils.errorDialog(e.getMessage());
            } catch (IOException e) {
                DialogUtils.errorDialog(e.getMessage());
            }
        } else {
            DialogUtils.errorDialog("File is not valid");
        }
    }

    ObservableList<String> cipherList = FXCollections.observableArrayList("AtBash", "Cesar", "Matrix", "ROT-13", "Vigenere");
    @FXML
    private ChoiceBox<String> listCipherMethod;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSet = new HashSet<>();
        userSet.add(new User.Builder().setLogin("user1").setUserPass("user1").setEmail("zbyszekbogdanczyk@wp.pl").setEmailPass("12345678").setCipherMethod("Matrix").build());
        userSet.add(new User.Builder().setLogin("user2").setUserPass("12345").setEmail("dsproduct@gmail.com").setEmailPass("1234qwer").setCipherMethod("Vigenere").build());

        listCipherMethod.setItems(cipherList);
        listCipherMethod.setValue("AtBash");
        method = "encode";
    }

    @FXML
    void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void toPDF() {
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(path);
        } catch (FileNotFoundException e) {
            System.out.println("error pdf write");
        }
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        String para1 = "SDA CRYPT MACHINE:\n";
        String para2 = textArea.getText();

        Paragraph paragraph1 = new Paragraph(para1);
        Paragraph paragraph2 = new Paragraph(para2);

        document.add(paragraph1);
        document.add(paragraph2);

        document.close();
        System.out.println("create pdf");
    }

    @FXML
    void sendEmail() {
        if (loginUser != null) EmailUtils.sendEmail(loginUser, pathCrpt);
        else EmailUtils.sendEmail(pathCrpt);
    }

    @FXML
    void loginAction() {
        dialogUtils = new DialogUtils();
        loginUser = dialogUtils.dialogLogin(userSet);
        listCipherMethod.setValue(loginUser.getCipherMethod());
    }

    @FXML
    void addUserAction() {
        dialogUtils = new DialogUtils();
        dialogUtils.addUserDialog();
    }
}
