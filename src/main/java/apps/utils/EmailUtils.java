package apps.utils;

import apps.controllers.MainController;
import apps.User;
import javafx.scene.control.TextInputDialog;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class EmailUtils {

    public static void sendEmail() {
        TextInputDialog dialog = new TextInputDialog("example@ex.com");
        dialog.setTitle("Email");
        dialog.setHeaderText("Write correct mail");
        dialog.setContentText("Enter email: ");
        Optional<String> result = dialog.showAndWait();


        File file = new File("/home/damian/Pulpit/msg.crpt");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(MainController.getTextArea());
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

    public static void sendEmail(User user) {
        TextInputDialog dialog = new TextInputDialog("example@ex.com");
        dialog.setTitle("Email");
        dialog.setHeaderText("Write correct mail");
        dialog.setContentText("Enter email: ");
        Optional<String> result = dialog.showAndWait();


        File file = new File("/home/damian/Pulpit/msg.crpt");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(MainController.getTextArea());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("Problem with FileWriter");
        }
        String[] emailUser = user.getEmail().split("@");
        String login = emailUser[0];
        String password = user.getEmailPass();
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp." + emailUser[1]);
        email.setSmtpPort(465);
        email.setAuthentication(login, password);
        email.setSSLOnConnect(true);
        try {
            email.setFrom(user.getEmail());
        } catch (EmailException e) {
            System.out.println("Invalid sender email ");
        }
        email.setSubject("Message");
        try {
            email.setMsg("Happy day!");
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
