package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.AbstractAdapter;
import data.TestAbstract;
import model.buffs.Buff;
import model.buffs.PoisonBuff;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.enumerations.HeroName;
import model.enumerations.SpellName;
import model.enumerations.SpellTargetType;
import view.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class AccountSave {

    public static void save(Deck account) {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Buff.class, new AbstractAdapter());
        Gson gson = gsonBilder.create();
        ArrayList<Buff> buffs = new ArrayList<>();
        buffs.add(new PoisonBuff(10,false,false));
        Spell spell = new Spell("m", 10, 10, SpellTargetType.ENEMY_HERO, 20, "mohse", buffs
                , null, SpellName.SACRIFICE);
        String n = gson.toJson(spell);
        System.out.println(n);
        Card t = gson.fromJson(n,Card.class);

    }
}
