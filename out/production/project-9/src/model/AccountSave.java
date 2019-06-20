package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.*;
import model.buffs.Buff;
import model.buffs.PoisonBuff;
import model.buffs.WeaknessBuff;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.cellaffects.HollyCellAffect;
import model.enumerations.*;
import model.items.Item;
import model.specialPower.OnDeathSpecialPower;
import model.specialPower.OnDeathTargetType;
import model.specialPower.SpecialPower;

import java.util.ArrayList;


public class AccountSave {//TODO

    public static void save(Deck account) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Buff.class, new BuffAdapter());
        gsonBuilder.registerTypeAdapter(CellAffect.class, new CellAffectAdapter());
        gsonBuilder.registerTypeAdapter(Card.class, new CardAdapter());
        gsonBuilder.registerTypeAdapter(Item.class,new ItemAdapter());
        gsonBuilder.registerTypeAdapter(SpecialPower.class,new SpecialPowerAdapter());
        Gson gson = gsonBuilder.create();
        /*ArrayList<Buff> buffs = new ArrayList<>();
        buffs.add(new WeaknessBuff(2,false,
                false,5,true,false,new int[]{1,2}));
        buffs.add(new PoisonBuff(10,false,false));
        Spell spell = new Spell("m", 10, 10, SpellTargetType.ENEMY_HERO, 20, "mohse", buffs
                , new HollyCellAffect(5), SpellName.SACRIFICE);*/

        Minion c = DefaultCards.getMinion(MinionName.FARS_SEPAHSALAR);
        String n = gson.toJson(c, Card.class);
        System.out.println(n);
        Card t = gson.fromJson(n, Card.class);
        System.out.println(((Minion) t).getSpecialPower().getMinion());

            /*Item item = DefaultCards.getItem(ItemName.NOOSH_DAROO);
            String n  = gson.toJson(item,Item.class);
            System.out.println(n);
            Item b = gson.fromJson(n,Item.class);
            System.out.println(b.getClass().getSimpleName());*/

        /*String n = gson.toJson(new PoisonBuff(2, false, false), Buff.class);
        System.out.println(n);
        Buff b = gson.fromJson(n, Buff.class);
        if (b instanceof PoisonBuff)
            System.out.println("yes");
        else
            System.out.println("no");*/
    }
}
