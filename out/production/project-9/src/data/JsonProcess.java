package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.*;
import model.Account;
import model.Deck;
import model.buffs.Buff;
import model.cards.Card;
import model.cellaffects.CellAffect;
import model.items.Item;
import model.specialPower.SpecialPower;

import java.io.*;
import java.util.ArrayList;


public class JsonProcess {

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(Buff.class, new BuffAdapter());
        gsonBuilder.registerTypeAdapter(CellAffect.class, new CellAffectAdapter());
        gsonBuilder.registerTypeAdapter(Card.class, new CardAdapter());
        gsonBuilder.registerTypeAdapter(Item.class, new ItemAdapter());
        gsonBuilder.registerTypeAdapter(SpecialPower.class, new SpecialPowerAdapter());
    }

    private static Gson gson = gsonBuilder.create();

    public static void saveAccount(Account account) {
        String userName = account.getUserName();
        File newFile = new File("src/data/accountJsons/" + userName + ".json");
        try {
            FileWriter writer = new FileWriter(newFile);
            gson.toJson(account, Account.class, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Account> getSavedAccounts() {
        File f = new File("src/data/accountJsons");
        File[] files = f.listFiles();
        ArrayList<Account> result = new ArrayList<>();
        for (File file : files) {
            try {
                FileReader reader = new FileReader("src/data/accountJsons/" + file.getName());
                result.add(gson.fromJson(reader, Account.class));
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void exportDeckFromAccount(String deckName, Account account) throws DeckAddException {
        Deck deck = account.findDeckByName(deckName);
        if (deck != null) {
            if (getSavedDecksNames().contains(deckName))
                throw new DeckAddException();
            else {
                File file = new File("src/data/decks/" + deck.getName() + ".json");
                try {
                    FileWriter writer = new FileWriter(file);
                    gson.toJson(deck, Deck.class, writer);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addSavedDeckToAccount(Account account, String deckName) {
        File file = new File("src/data/decks/" + deckName + ".json");
        try {
            FileReader reader = new FileReader(file);
            Deck deck = gson.fromJson(reader, Deck.class);
            reader.close();
            account.addDeck(deck);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getSavedDecksNames() {
        File file = new File("src/data/decks");
        File[] files = file.listFiles();
        ArrayList<String> result = new ArrayList<>();
        for (File file1 : files) {
            result.add(file1.getName().substring(0, file1.getName().indexOf(".")));
        }
        return result;
    }

    public static void saveCustomCard(Card card) {
        File file = new File("src/data/customCards/" + card.getName() + ".json");
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(card, Card.class, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Card> getSavedCustomCards() {
        File file = new File("src/data/customCards");
        ArrayList<Card> result = new ArrayList<>();
        for (File cardFile : file.listFiles()) {
            if (cardFile != null) {
                try {
                    FileReader reader = new FileReader("src/data/customCards/" + cardFile.getName());
                    result.add(gson.fromJson(reader, Card.class));
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
