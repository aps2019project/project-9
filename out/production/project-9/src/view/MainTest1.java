package view;

import model.Account;
import model.AccountSave;
import model.Deck;

public class MainTest1 {
    public static void main(String[] args) {
        AccountSave.save(new Deck("first_level"));
    }
}
