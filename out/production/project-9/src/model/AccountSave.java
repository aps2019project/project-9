package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.BuffAdapter;
import data.CardAdapter;
import data.CellAffectAdapter;
import model.buffs.Buff;
import model.buffs.PoisonBuff;
import model.buffs.WeaknessBuff;
import model.cards.Card;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.cellaffects.HollyCellAffect;
import model.enumerations.MinionName;
import model.enumerations.SpellName;
import model.enumerations.SpellTargetType;

import java.util.ArrayList;


public class AccountSave {

    public static void save(Deck account) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Buff.class, new BuffAdapter());
        gsonBuilder.registerTypeAdapter(CellAffect.class, new CellAffectAdapter());
        gsonBuilder.registerTypeAdapter(Card.class, new CardAdapter());
        Gson gson = gsonBuilder.create();
        /*ArrayList<Buff> buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(2,false,
                false,5,true,false,new int[]{1,2}));
        buffs.add(new PoisonBuff(10,false,false));
        Spell spell = new Spell("m", 10, 10, SpellTargetType.ENEMY_HERO, 20, "mohse", buffs
                , new HollyCellAffect(5), SpellName.SACRIFICE);*/

        /*Card c = DefaultCards.getSpell(SpellName.SACRIFICE);
        System.out.println(c.getClass().getSimpleName());
        String n = gson.toJson(c);
        System.out.println(n);
        Card t = gson.fromJson(n,Card.class);*/
        String n = gson.toJson(new PoisonBuff(2, false, false), Buff.class);
        System.out.println(n);
        Buff b = gson.fromJson(n, Buff.class);
        if (b instanceof PoisonBuff)
            System.out.println("yes");
        else
            System.out.println("no");
        /*String n  = gson.toJson(new HollyCellAffect(2));
        System.out.println(n);
        CellAffect c = gson.fromJson(n,CellAffect.class);
        System.out.println((c instanceof HollyCellAffect)?("yes"):"no");*/
    }
}
