package apps.utils;

import apps.User;
import apps.controllers.MainController;
import javafx.scene.control.TextInputDialog;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class EmailUtils {
    private static MainController mainController = new MainController();


    public static void email(String login, String password, String path) {
        TextInputDialog dialog = new TextInputDialog("example@ex.com");
        dialog.setTitle("Email");
        dialog.setHeaderText("Write correct mail");
        dialog.setContentText("Enter email: ");
        Optional<String> result = dialog.showAndWait();


        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(mainController.getTextArea());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("Problem with FileWriter");
        }
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.wp.pl");
        email.setSmtpPort(465);
        email.setAuthentication(login.substring(0, login.indexOf("@")), password);
        email.setSSLOnConnect(true);
        try {
            email.setFrom(login);
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
        attachment.setPath(path);
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


    public static void sendEmail(String path) {
        email("zbyszekbogdanczyk@wp.pl", "12345678", path);
    }

    public static void sendEmail(User user, String path) {
        email(user.getEmail(), user.getEmailPass(), path);
    }
}
