package apps.utils;

import apps.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    static void CSVReaderEX() throws IOException {
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/dbfile/xxxusr.csv"), ',');
        List<User> userList = new ArrayList<User>();
        String[] record = null;
        reader.readNext();
        while((record = reader.readNext()) != null){
            User user = new User();
            user.setLogin(record[0]);
            user.setLoginPass(record[1]);
            user.setEmail(record[2]);
            user.setEmailPass(record[3]);
            user.setCipherMethod(record[4]);
            userList.add(user);
        }

        reader.close();

        System.out.println(userList);
    }


    public static void writeCSVData(User user, String filepath) {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(filepath);
            fileWriter.write("Login,LoginPassword,Email,EmailPassword,CipherMethod\n");
            fileWriter.write(user.getLogin() + ",");
            fileWriter.write(user.getLoginPass() + ",");
            fileWriter.write(user.getEmail() + ",");
            fileWriter.write(user.getEmailPass() + ",");
            fileWriter.write(user.getCipherMethod() + "\n");
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        } finally {
            if(fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    DialogUtils.errorDialog(e.getMessage());
                }

            }
        }
    }
}
