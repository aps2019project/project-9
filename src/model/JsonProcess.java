package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.*;
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

    /*public static void save(Deck account) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Buff.class, new BuffAdapter());
        gsonBuilder.registerTypeAdapter(CellAffect.class, new CellAffectAdapter());
        gsonBuilder.registerTypeAdapter(Card.class, new CardAdapter());
        gsonBuilder.registerTypeAdapter(Item.class, new ItemAdapter());
        gsonBuilder.registerTypeAdapter(SpecialPower.class, new SpecialPowerAdapter());
        Gson gson = gsonBuilder.create();
        *//*ArrayList<Buff> buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(2,false,
                false,5,true,false,new int[]{1,2}));
        buffs.add(new PoisonBuff(10,false,false));
        Spell spell = new Spell("m", 10, 10, SpellTargetType.ENEMY_HERO, 20, "mohse", buffs
                , new HollyCellAffect(5), SpellName.SACRIFICE);*//*

     *//*Minion c = DefaultCards.getMinion(MinionName.FARS_SEPAHSALAR);
        String n = gson.toJson(c, Card.class);
        System.out.println(n);
        Card t = gson.fromJson(n, Card.class);
        System.out.println(((Minion) t).getSpecialPower().getMinion());*//*
        try {
            Account account1 = new Account("m", "N");
            FileWriter f = new FileWriter("src/data/new.json");
            gson.toJson(account1, Account.class, f);
            f.close();
            FileReader f2 = new FileReader("src/data/new.json");
            Account t = gson.fromJson(f2, Account.class);
            System.out.println(t.getUserName());
            System.out.println(t.getMainDeck().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

            *//*Item item = DefaultCards.getItem(ItemName.NOOSH_DAROO);
            String n  = gson.toJson(item,Item.class);
            System.out.println(n);
            Item b = gson.fromJson(n,Item.class);
            System.out.println(b.getClass().getSimpleName());*//*

     *//*String n = gson.toJson(new PoisonBuff(2, false, false), Buff.class);
        System.out.println(n);
        Buff b = gson.fromJson(n, Buff.class);
        if (b instanceof PoisonBuff)
            System.out.println("yes");
        else
            System.out.println("no");*//*
    }*/

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
}
