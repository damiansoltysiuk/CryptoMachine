package apps;

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
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private String method;
    private String cipherMethod;

    @FXML
    private RadioButton decode;

    @FXML
    void selectMethod(ActionEvent event) {
        if (decode.isSelected()) {
            method = "decode";
            System.out.println("Selected method: decode();");
        } else {
            method = "encode";
            System.out.println("Selected method: encode();");
        }
    }

    @FXML
    private TextArea textArea;

    @FXML
    void runAction(ActionEvent event) {
        String messege = textArea.getText();
        cipherMethod = listCipherMethod.getValue();
        ICipherMethod cipher = FactoryCipher.getInstance().makeCipher(cipherMethod);
        if(cipher instanceof Vigenere) {
            TextInputDialog dialog = new TextInputDialog("key");
            dialog.setTitle("Key");
            dialog.setHeaderText("Please write the key");
            dialog.setContentText("Key: ");
            Optional<String> result = dialog.showAndWait();
            cipher.setText(result.get());
        }
        cipher.setText(messege);
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
        if(cipher instanceof Vigenere) cipher.setText("key");

    }

    @FXML
    private TextField outputFilePath;

    @FXML
    void saveAction(ActionEvent event) {
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
                System.out.println(e);
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    @FXML
    private TextField inputFilePath;

    @FXML
    void loadAction(ActionEvent event) {
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
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    ObservableList<String> cipherList = FXCollections.observableArrayList("AtBash", "Cesar", "Matrix", "ROT-13", "Vigenere");
    @FXML
    private ChoiceBox<String> listCipherMethod;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listCipherMethod.setItems(cipherList);
        listCipherMethod.setValue("AtBash");
        method = "encode";
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void toPDF(ActionEvent event) {
        String path = "/home/damian/Pulpit/code.pdf";
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
    void sendEmail(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("example@ex.com");
        dialog.setTitle("Email");
        dialog.setHeaderText("Write correct mail");
        dialog.setContentText("Enter email: ");
        Optional<String> result = dialog.showAndWait();


        File file = new File("/home/damian/Pulpit/msg.crpt");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(textArea.getText());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("Problem with FileWriter");
        }
        String login = "zbyszekbogdanczyk";
        String password = "12345678";
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.wp.pl");
        email.setSmtpPort(465);
        email.setAuthentication(login, password);
        email.setSSLOnConnect(true);
        try {
            email.setFrom("zbyszekbogdanczyk@wp.pl");
        } catch (EmailException e) {
            System.out.println("Invalid sender email ");
        }
        email.setSubject("Message");
        try {
            email.setMsg("rrnsdklwoewh2%&^&");
        } catch (EmailException e) {
            System.out.println("Incorrect message content");
        }
        //add attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("/home/damian/Pulpit/msg.crpt");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("encode message");
        attachment.setName("msg");
        try {
            email.attach(attachment);
        } catch (EmailException e) {
            System.out.println("Problem with attachment");
        }

        try {
            email.addTo(result.get());
        } catch (EmailException e) {
            System.out.println("Invalid viewer email");
        }
        try {
            email.send();
            System.out.println("email sending");
        } catch (EmailException e) {
            System.out.println("Problems with sending email");
        }

        file.delete();
    }
}
