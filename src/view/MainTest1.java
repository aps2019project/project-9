package view;

import model.Account;
import model.JsonProcess;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainTest1 {
    public static void main(String[] args) {
        //JsonProcess.getSavedAccounts();
        //JsonProcess.saveAccount(new Account("mammad", "n"));
        LocalDateTime l = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String str = l.format(myFormatObj);
        System.out.println(l.format(myFormatObj));
    }
}
