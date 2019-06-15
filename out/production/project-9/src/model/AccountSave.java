package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class AccountSave {

    public static void save(Account account) {
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            try (FileWriter f = new FileWriter("src/data/new.json")) {
                gson.toJson(account, f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
