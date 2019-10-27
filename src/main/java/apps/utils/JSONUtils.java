package apps.utils;

import apps.User;

import javax.json.*;
import java.io.*;

public class JSONUtils {
    public static void readJSONData(String filePath) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
        JsonReader jsonReader = Json.createReader(fis);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        try {
            fis.close();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }

        User user = new User();
        user.setLogin(jsonObject.getString("login"));
        user.setLoginPass("loginPass");
        user.setEmail("email");
        user.setEmailPass("emailPass");
        user.setCipherMethod("cipherMethod");

        System.out.println(user);
    }

    public static void writeJSONData(User user, String filepath) {
        JsonObjectBuilder userBuilder = Json.createObjectBuilder();
        userBuilder.add("login", user.getLogin());
        userBuilder.add("loginPass", user.getLoginPass());
        userBuilder.add("email", user.getEmail());
        userBuilder.add("emailPass", user.getEmailPass());
        userBuilder.add("cipherMethod", user.getCipherMethod());

        JsonObject usrJsonObject = userBuilder.build();

        OutputStream os = null;
        try {
            os = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            DialogUtils.errorDialog(e.getMessage());
        }

        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeObject(usrJsonObject);
        jsonWriter.close();
    }
}
