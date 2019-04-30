package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.buffs.*;
import model.cards.Hero;
import model.cards.HeroTargetType;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.cellaffects.FireCellAffect;
import model.cellaffects.HollyCellAffect;
import model.cellaffects.PoisonCellAffect;
import model.enumerations.*;
import model.items.Item;
import model.items.ManaUsableItem;
import model.items.SpellUsableItem;
import model.specialPower.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class DefaultCards {

    public static Minion getMinion(MinionName name) { // special powers = null
        Minion minion = gson.fromJson(minionHashMap.get(name), Minion.class);
        setMinionSpecialPower(minion, name);
        return minion;
    }

    public static Spell getSpell(SpellName name) { // cellaffects and buffs = null
        Spell spell = gson.fromJson(spellHashMap.get(name), Spell.class);
        setSpellCellAffectOrBuffs(spell, name);
        return spell;
    }

    public static Hero getHero(HeroName name) { // hero spell = null
        Hero hero = gson.fromJson(heroHashMap.get(name), Hero.class);
        setHeroBuffsOrCellAffect(hero, name);
        return hero;
    }

    public static Item getItem(ItemName name) {
        switch (name) {
            case TAJ_DANAYEE:
                return new ManaUsableItem();
            case NAMOOS_SEPAR:
                return new SpellUsableItem();
            case KAMAN_DAMOOL:
        }
    }


    private static void setMinionSpecialPower(Minion minion, MinionName name) {
        switch (name) {
            case FARS_KAMANDAR:
                minion.setSpecialPower(null);
                break;
            case FARS_SHAMSHIRZAN:
                StunBuff buff = new StunBuff(1, false, false);
                ArrayList<Buff> buffs = new ArrayList<>();
                buffs.add(buff);
                OnAttackSpecialPower specialPower =
                        new OnAttackSpecialPower(buffs, false, false);
                minion.setSpecialPower(specialPower);
                break;
            case FARS_NEYZEDAR:
                minion.setSpecialPower(null);
                break;
            case FARS_ASBSAVAR:
                minion.setSpecialPower(null);
                break;
            case FARS_PAHLAVAN:
                ArrayList<Buff> buffs1 = new ArrayList<>();
                WeaknessBuff buff1 = new WeaknessBuff(400, true,
                        false, 5, true, false, null);
                buffs1.add(buff1);
                minion.setSpecialPower(new OnAttackSpecialPower(buffs1, false, false));
                break;
            case FARS_SEPAHSALAR:
                minion.setSpecialPower(new ComboSpecialPower());
                break;
            case TOORANEE_KAMANDAR:
                minion.setSpecialPower(null);
                break;
            case TOORANEE_GHOLABSANG:
                minion.setSpecialPower(null);
                break;
            case TOORANEE_NEYZEDAR:
                minion.setSpecialPower(null);
                break;
            case TOORANE_JASOS:
                buffs = new ArrayList<>();
                DisarmBuff buff2 = new DisarmBuff(1, false, false);
                PoisonBuff buff3 = new PoisonBuff(4, false, false);
                buffs.add(buff2);
                buffs.add(buff3);
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case TOORANEE_GORZDAR:
                minion.setSpecialPower(null);
                break;
            case TOORANEE_SHAHZADE:
                minion.setSpecialPower(new ComboSpecialPower());
                break;
            case BLACK_DEEV:
                break;
            case SANGANDAZ_GHOLL:
                break;
            case EAGLE:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 10, true));
                minion.setSpecialPower(new PassiveSpecialPower(buffs, PassiveTargetType.CURRENT_CELL));
                break;
            case GORAZ_DEEV:
                break;
            case ONE_EYE_GHOOL:
                minion.setSpecialPower(new OnDeathSpecialPower(2, OnDeathTargetType.EIGHT_AROUND_MINIONS));
                break;
            case POISON_SNAKE:
                buffs = new ArrayList<>();
                buffs.add(new PoisonBuff(3, false, false));
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case DRAGON_FIRE:
                break;
            case DARANDE_SHIR:
                minion.setSpecialPower(new OnAttackSpecialPower(null, true, false));
                break;
            case GHOOL_SNAKE:
                HollyBuff buff4 = new HollyBuff(400, true, false, true);
                minion.setSpecialPower(new OnSpawnSpecialPower(buff4, OnSpawnTargetCell.TWO_DISTANCE_CELLS));
                break;
            case WHITE_WOLF:
                int[] powers = {6, 4};
                WeaknessBuff buff5 = new WeaknessBuff(3, false,
                        false, 0, true, true, powers);
                buffs = new ArrayList<>();
                buffs.add(buff5);
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case PALANG:
                int[] powers1 = {8};
                buff5 = new WeaknessBuff(2, false, false,
                        0, true, true, powers1);
                buffs = new ArrayList<>();
                buffs.add(buff5);
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case WOLF:
                int[] powers2 = {6};
                buff5 = new WeaknessBuff(2, false, false,
                        0, true, true, powers2);
                buffs = new ArrayList<>();
                buffs.add(buff5);
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case JADOOGAR:
                PowerBuff buff6 = new PowerBuff(1, false,
                        false, 2, false);
                WeaknessBuff buff7 = new WeaknessBuff(1, false, false,
                        1, true, false, null);
                buffs = new ArrayList<>();
                buffs.add(buff6);
                buffs.add(buff7);
                minion.setSpecialPower(new PassiveSpecialPower(buffs,
                        PassiveTargetType.CURRENT_AND_EIGHT_FRIENDLY_AROUND));
                break;
            case JADOOGAR_AZAM:
                buff6 = new PowerBuff(400, false,
                        true, 2, false);
                HollyBuff buff8 = new HollyBuff(400, false,
                        true, false);
                buffs = new ArrayList<>();
                buffs.add(buff6);
                buffs.add(buff8);
                minion.setSpecialPower(new PassiveSpecialPower(buffs, PassiveTargetType.CURRENT_AND_EIGHT_FRIENDLY_AROUND));
                break;
            case JEN:
                buff6 = new PowerBuff(400, false, true, 1, false);
                buffs = new ArrayList<>();
                buffs.add(buff6);
                minion.setSpecialPower(new PassiveSpecialPower(buffs, PassiveTargetType.ALL_FRIENDLY_MINIONS));
                break;
            case WILD_GORAZ:
                minion.setSpecialPower(new OnDefendSpecialPower(OnDefendType.BUFF, BuffName.DISARM));
                break;
            case PIRAN:
                minion.setSpecialPower(new OnDefendSpecialPower(OnDefendType.BUFF, BuffName.POISON));
                break;
            case GEEV:
                minion.setSpecialPower(new OnDefendSpecialPower(OnDefendType.NOT_NEGATIVE, null));
                break;
            case BAHMAN:
                WeaknessBuff buff9 = new WeaknessBuff(400, true,
                        false, 16, true, false, null);
                minion.setSpecialPower(new OnSpawnSpecialPower(buff9, OnSpawnTargetCell.A_RANDOM_ENEMY_MINION));
                break;
            case ASHKBOOS:
                minion.setSpecialPower(new OnDefendSpecialPower(OnDefendType.NOT_ATTACK_FROM_WEAK, null));
                break;
            case EERAG:
                break;
            case BIG_GHOOL:
                break;
            case DOSAR_GHOOL:
                minion.setSpecialPower(new OnAttackSpecialPower(null, false, true));
                break;
            case NANE_SARMA:
                StunBuff buff10 = new StunBuff(1, false, false);
                minion.setSpecialPower(new OnSpawnSpecialPower(buff10, OnSpawnTargetCell.EIGHT_AROUND));
                break;
            case FOOLAD_ZEREH:
                buffs = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    buffs.add(new HollyBuff(400, false, true, false));
                }
                minion.setSpecialPower(new PassiveSpecialPower(buffs, PassiveTargetType.CURRENT_CELL));
                break;
            case SIAVOSH:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        6, true, false, null));
                minion.setSpecialPower(new OnAttackSpecialPower(buffs, false, false));
                break;
            case SHAH_GOOL:
                minion.setSpecialPower(new ComboSpecialPower());
                break;
            case ARJANG_DEEV:
                minion.setSpecialPower(new ComboSpecialPower());
                break;
        }
    }

    private static void setSpellCellAffectOrBuffs(Spell spell, SpellName name) {
        ArrayList<Buff> buffs;
        switch (name) {
            case TOTAL_DISARM:
                DisarmBuff buff = new DisarmBuff(400, true, false);
                buffs = new ArrayList<>();
                buffs.add(buff);
                spell.setBuffs(buffs);
                break;
            case AREA_DISPEL:
                break;
            case EMPOWER:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 2, false));
                spell.setBuffs(buffs);
                break;
            case FIREBALL:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        4, true, false, null));
                spell.setBuffs(buffs);
                break;
            case GOD_STRENGTH:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 4, false));
                spell.setBuffs(buffs);
                break;
            case HELLFIRE:
                FireCellAffect cellAffect = new FireCellAffect(2);
                spell.setCellAffect(cellAffect);
                break;
            case LIGHTING_BOLT:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        8, true, false, null));
                spell.setBuffs(buffs);
                break;
            case POISON_LAKE:
                PoisonCellAffect cellAffect1 = new PoisonCellAffect(1);
                spell.setCellAffect(cellAffect1);
                break;
            case MADNESS:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(3, false, false, 4, false));
                buffs.add(new DisarmBuff(3, false, false));
                spell.setBuffs(buffs);
                break;
            case ALL_DISARM:
                buffs = new ArrayList<>();
                buffs.add(new DisarmBuff(1, false, false));
                spell.setBuffs(buffs);
                break;
            case ALL_POISON:
                buffs = new ArrayList<>();
                buffs.add(new PoisonBuff(4, false, false));
                spell.setBuffs(buffs);
                break;
            case DISPEL:
                break;
            case HEALTH_WITH_PROFIT:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        6, true, false, null));
                buffs.add(new HollyBuff(3, false, false, false));
                spell.setBuffs(buffs);
                break;
            case POWER_UP:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 6, false));
                spell.setBuffs(buffs);
                break;
            case ALL_POWER:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 2, false));
                spell.setBuffs(buffs);
                break;
            case ALL_ATTACK:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        6, true, false, null));
                spell.setBuffs(buffs);
                break;
            case WEAKENING:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        4, false, false, null));
                spell.setBuffs(buffs);
                spell.setBuffs(buffs);
                break;
            case SACRIFICE:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false,
                        6, true, false, null));
                buffs.add(new PowerBuff(400, true, false, 8, false));
                spell.setBuffs(buffs);
                break;
            case KINGS_GUARD:
                break;
            case SHOCK:
                buffs = new ArrayList<>();
                buffs.add(new StunBuff(2, false, false));
                spell.setBuffs(buffs);
                break;
        }


    }

    private static void setHeroBuffsOrCellAffect(Hero hero, HeroName name) {
        ArrayList<Buff> buffs;
        switch (name) {
            case WHITE_DEEV:
                buffs = new ArrayList<>();
                buffs.add(new PowerBuff(400, true, false, 4, false));
                hero.setBuffs(buffs);
                break;
            case SIMORGH:
                buffs = new ArrayList<>();
                buffs.add(new StunBuff(1, false, false));
                hero.setBuffs(buffs);
                break;
            case EJDEHA:
                buffs = new ArrayList<>();
                buffs.add(new DisarmBuff(400, true, false));
                hero.setBuffs(buffs);
                break;
            case RAKHSH:
                buffs = new ArrayList<>();
                buffs.add(new StunBuff(1, false, false));
                hero.setBuffs(buffs);
                break;
            case ZAHAK:
                buffs = new ArrayList<>();
                buffs.add(new PoisonBuff(3, false, false));
                hero.setBuffs(buffs);
                break;
            case KAVE:
                CellAffect cellAffect = new HollyCellAffect(3);
                hero.setCellAffect(cellAffect);
                break;
            case ARASH:
                buffs = new ArrayList<>();
                buffs.add(new WeaknessBuff(400, true, false, 4, true, false, null));
                hero.setBuffs(buffs);
                break;
            case AFSANE:
                break;
            case ESFANDIAR:
                buffs = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    buffs.add(new HollyBuff(400, false, true, false));
                }
                hero.setBuffs(buffs);
                break;
            case ROSTAM:
                break;
        }
    }

    private static HashMap<MinionName, String> minionHashMap = new HashMap<>();
    private static HashMap<HeroName, String> heroHashMap = new HashMap<>();
    private static HashMap<ItemName, String> itemHashMap = new HashMap<>();
    private static HashMap<SpellName, String> spellHashMap = new HashMap<>();


    //-------------------------------------minions-------------------------------------------------------
    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final String FARS_KAMANDAR = gson.toJson(new Minion("fars_kamandar", 300, 2, 6,
            4, MinionAttackType.RANGED, 7, null, CardType.MINION, 0, "",
            MinionName.FARS_KAMANDAR, true));


    private static final String FARS_SHAMSHIRZAN = gson.toJson(new Minion("fars_shamshirzan", 400, 2,
            6, 4, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "Stun opponent for 1 turn on attack",
            MinionName.FARS_SHAMSHIRZAN, true));
    private static final String FARS_NEYZEDAR = gson.toJson(new Minion("fars_NEYZEDAR", 500, 1,
            5, 3, MinionAttackType.HYBRID, 3, null, CardType.MINION, 0,
            "",
            MinionName.FARS_NEYZEDAR, true));
    private static final String FARS_ASBSAVAR = gson.toJson(new Minion("fars_asbsavar", 200, 4,
            10, 6, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "",
            MinionName.FARS_ASBSAVAR, true));


    private static final String FARS_PAHLAVAN = gson.toJson(new Minion("fars_pahlavan", 600, 9,
            24, 6, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "attack 5 more unit than that attack to a unit",
            MinionName.FARS_PAHLAVAN, true));


    private static final String FARS_SEPAHSALAR = gson.toJson(new Minion("fars_sepahsalar", 800, 7,
            12, 4, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "Combo",
            MinionName.FARS_SEPAHSALAR, true));
    private static final String TOORANEE_KAMANDAR = gson.toJson(new Minion("toorane_kamandar", 500, 1,
            3, 4, MinionAttackType.RANGED, 5, null, CardType.MINION, 0,
            "",
            MinionName.TOORANEE_KAMANDAR, false));
    private static final String TOORANEE_GHOLABSANG = gson.toJson(new Minion("toorane_gholabsang", 600, 1,
            4, 2, MinionAttackType.RANGED, 7, null, CardType.MINION, 0,
            "",
            MinionName.TOORANEE_GHOLABSANG, false));
    private static final String TOORANEE_NEYZEDAR = gson.toJson(new Minion("toorane_neyzedar", 600, 1,
            4, 4, MinionAttackType.HYBRID, 3, null, CardType.MINION, 0,
            "",
            MinionName.TOORANEE_NEYZEDAR, false));


    private static final String TOORANEE_JASOS = gson.toJson(new Minion("toorane_jasos", 700, 4,
            6, 6, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "Disarm enemy unit for 1 turn and posoined for 4 turn",
            MinionName.TOORANE_JASOS, false));
    private static final String TOORANEE_GORZDAR = gson.toJson(new Minion("toorane_gorzdar", 450, 2,
            3, 10, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "",
            MinionName.TOORANEE_GORZDAR, false));


    private static final String TOORANEE_SHAHZADE = gson.toJson(new Minion("toorane_shahzade", 800, 6,
            6, 10, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "Combo",
            MinionName.TOORANEE_SHAHZADE, false));
    private static final String BLACK_DEEV = gson.toJson(new Minion("black_deev", 300, 9,
            14, 10, MinionAttackType.HYBRID, 7, null, CardType.MINION, 0,
            "",
            MinionName.BLACK_DEEV, false));
    private static final String SANGANDAZ_GHOLL = gson.toJson(new Minion("sangandaz_gholl", 300, 9,
            12, 12, MinionAttackType.RANGED, 7, null, CardType.MINION, 0,
            "",
            MinionName.SANGANDAZ_GHOLL, false));

    private static final String TAJ_DANAYEE = gson.toJson(new Item(300, "taj_danayee",
            ItemName.TAJ_DANAYEE, "add one mana in first three turn"));

    private static final String EAGLE = gson.toJson(new Minion("eagle", 200, 2,
            0, 2, MinionAttackType.RANGED, 3, null, CardType.MINION, 0,
            "have 10 power buff passive",
            MinionName.EAGLE, false));    ///helth is a mystyry
    private static final String GORAZ_DEEV = gson.toJson(new Minion("goraz_deev", 300, 6,
            16, 8, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "",
            MinionName.GORAZ_DEEV, false));
    private static final String NAMOOS_SEPAR = gson.toJson(new Item(4000, "namoos_separ",
            ItemName.NAMOOS_SEPAR, "active 12 holy buff in friendly hero"));

    private static final String ONE_EYE_GHOOL = gson.toJson(new Minion("one_eye_ghool", 500, 7,
            12, 11, MinionAttackType.HYBRID, 3, null, CardType.MINION, 0,
            "Attack 2 unit to minions around him on death",
            MinionName.ONE_EYE_GHOOL, false));
    private static final String KAMAN_DAMOL = gson.toJson(new Item(30000, "kaman_damol",
            ItemName.KAMAN_DAMOOL, "Only for ranged and hybrids : friendly hero disarm unit" +
            "that attack it for onr turn"));

    private static final String POISON_SNAKE = gson.toJson(new Minion("poison_snake", 300, 4,
            5, 6, MinionAttackType.RANGED, 4, null, CardType.MINION, 0,
            "poison enemy unit 3 turn on attack",
            MinionName.POISON_SNAKE, false));
    private static final String DRAGON_FIRE = gson.toJson(new Minion("dragon_fire", 250, 5,
            9, 5, MinionAttackType.RANGED, 4, null, CardType.MINION, 0,
            "",
            MinionName.DRAGON_FIRE, false));


    private static final String DARANDE_SHIR = gson.toJson(new Minion("darande_shir", 600, 2,
            1, 8, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "holy buff doesn't have effect on this card on attack",
            MinionName.DARANDE_SHIR, false));
    private static final String NOOSH_DARO = gson.toJson(new Item(0, "noosh_daro",
            ItemName.NOOSH_DAROO, "add 6 unit to a random unit health"));

    private static final String GHOOL_SNAKE = gson.toJson(new Minion("ghool_snake", 500, 8,
            14, 7, MinionAttackType.RANGED, 5, null, CardType.MINION, 0,
            "Reverse holy buff on spawn on around minions",
            MinionName.GHOOL_SNAKE, false));
    private static final String TIR_DOSHAKH = gson.toJson(new Item(0, "tir_doshakh",
            ItemName.TIR_DOSHAKH, "add 2 unit to a random hybrid or ranged unit"));

    private static final String WHITE_WOLF = gson.toJson(new Minion("white_wolf", 400, 5,
            8, 2, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "when attack a minion reduce 6 unit in next turn and 4 unit in next 2 turn of health",
            MinionName.WHITE_WOLF, false));
    private static final String PAR_SIMORGH = gson.toJson(new Item(3500, "par_simorgh",
            ItemName.PAR_SIMORGH, "if enemy hero is hybrid or ranged reduce two unit of it attack power"));

    private static final String PALANG = gson.toJson(new Minion("palang", 400, 4,
            6, 2, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "when attack a minion next turn its health reduce 8 unit",
            MinionName.PALANG, false));
    private static final String EKSIR = gson.toJson(new Item(0, "eksir",
            ItemName.EKSIR, "add 3 unit to health power & give a powerbuff with 3 power" +
            "increase on random minion"));

    private static final String WOLF = gson.toJson(new Minion("wolf", 400, 3,
            6, 1, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "when attack a minion next turn its health reduce 6 unit",
            MinionName.WOLF, false));
    private static final String MAJOON_MANA = gson.toJson(new Item(0, "majoon_mana",
            ItemName.MAJOON_MANA, "increase 3 mana on next turn"));

    private static final String JADOOGAR = gson.toJson(new Minion("jadoogar", 550, 4,
            5, 4, MinionAttackType.RANGED, 3, null, CardType.MINION, 0,
            "give itself and around friendly minions one power with 2 strength and one weakness with 1 strength" +
                    "for one turn",
            MinionName.JADOOGAR, false));
    private static final String MAJOON_ROIEEN = gson.toJson(new Item(0, "majoon_roieen",
            ItemName.MAJOON_ROIEEN, "active 10 holy buffs in a random friendly unit for 2 turns"));

    private static final String JADOOGAR_AZAM = gson.toJson(new Minion("jadoogar_azam", 550, 6,
            6, 6, MinionAttackType.RANGED, 5, null, CardType.MINION, 0,
            "give itself and 8 around minion one power with 2 strength and one holy buff continus passively",
            MinionName.JADOOGAR_AZAM, false));


    private static final String JEN = gson.toJson(new Minion("jen", 500, 5,
            10, 4, MinionAttackType.RANGED, 4, null, CardType.MINION, 0,
            "give every friendly minion 1 power buff continus on turn",
            MinionName.JEN, false));
    private static final String NEFRIN_MARG = gson.toJson(new Item(0, "nefrin_marg",
            ItemName.NEFRIN_MARG, "give a minion an ability that can hit a near unit of it with 8 power" +
            " on death"));

    private static final String WILD_GORAZ = gson.toJson(new Minion("wild_goraz", 500, 6,
            10, 14, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "can't be disarm",
            MinionName.WILD_GORAZ, false));
    private static final String PIRAN = gson.toJson(new Minion("piran", 400, 8,
            20, 12, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "can't be poisened",
            MinionName.PIRAN, false));
    private static final String GEEV = gson.toJson(new Minion("geev", 450, 4,
            5, 7, MinionAttackType.RANGED, 5, null, CardType.MINION, 0,
            "dont take negative effects",
            MinionName.GEEV, false));
    private static final String BAHMAN = gson.toJson(new Minion("bahman", 450, 8,
            16, 9, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "randomly hit a enemy minion with 16 power on spawn",
            MinionName.BAHMAN, false));
    private static final String ASHKBOS = gson.toJson(new Minion("ashkbos", 400, 7,
            14, 8, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "units with lower attack power can't attack him",
            MinionName.ASHKBOOS, false));
    private static final String DOSAR_GHOOL = gson.toJson(new Minion("dosar_ghool", 550, 4,
            10, 4, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "when attack a unit remove every positive buff that is active on it",
            MinionName.DOSAR_GHOOL, false));
    private static final String NANE_SARMA = gson.toJson(new Minion("nane_sarma", 400, 3,
            3, 4, MinionAttackType.RANGED, 5, null, CardType.MINION, 0,
            "stun minions around her for 1 turn on spawn",
            MinionName.NANE_SARMA, false));
    private static final String FOOLAD_ZEREH = gson.toJson(new Minion("foolad_zereh", 650, 3,
            1, 1, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "have 12 holy buff passively",
            MinionName.FOOLAD_ZEREH, false));
    private static final String SIYAVOSH = gson.toJson(new Minion("siyavosh", 350, 4,
            8, 5, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "hit enemy hero with 6 strenght on death",
            MinionName.SIAVOSH, false));
    private static final String EERAG = gson.toJson(new Minion("eerag", 500, 4,
            6, 20, MinionAttackType.RANGED, 3, null, CardType.MINION, 0,
            "",
            MinionName.EERAG, false));
    private static final String BIG_GHOOL = gson.toJson(new Minion("big_ghool", 600, 9,
            30, 8, MinionAttackType.HYBRID, 2, null, CardType.MINION, 0,
            "",
            MinionName.BIG_GHOOL, false));
    private static final String SHAH_GHOOL = gson.toJson(new Minion("shah_ghool", 600, 5,
            10, 4, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "combo",
            MinionName.SHAH_GOOL, false));
    private static final String ARJANG_DEEV = gson.toJson(new Minion("arjang_deev", 600, 3,
            6, 6, MinionAttackType.MELEE, 0, null, CardType.MINION, 0,
            "combo",
            MinionName.ARJANG_DEEV, false));


    //-------------------------------------------HERO----------------------------------------------------------//
    private static final String WHITE_DEEV = gson.toJson(new Hero(HeroName.WHITE_DEEV, 8000, 50,
            4, MinionAttackType.MELEE, 0, null, 1, 2, 0,
            "white_deev",
            "active a power buff with 4 unit streghnt on him",
            false, HeroTargetType.ITSELF, null));
    private static final String SIMORG = gson.toJson(new Hero(HeroName.SIMORGH, 9000, 50,
            4, MinionAttackType.MELEE, 0, null, 5, 8, 0,
            "simorgh",
            "stun all of enemy units for one turn",
            false, HeroTargetType.ALL_ENEMY_POWERS, null));
    private static final String EJDEHA = gson.toJson(new Hero(HeroName.EJDEHA, 8000, 50,
            4, MinionAttackType.MELEE, 0, null, 0, 1, 0,
            "ejdeha_haft_sar",
            "disarm one unit",
            false, HeroTargetType.AN_ENEMY_POWER, null));
    private static final String RAKHSH = gson.toJson(new Hero(HeroName.RAKHSH, 8000, 50,
            4, MinionAttackType.MELEE, 0, null, 1, 2, 0,
            "rakhsh",
            "stun one enemy unit for one turn",
            false, HeroTargetType.AN_ENEMY_POWER, null));
    private static final String ZAHAK = gson.toJson(new Hero(HeroName.ZAHAK, 10000, 50,
            2, MinionAttackType.MELEE, 0, null, 0, 0, 0,
            "zahak",
            "when attack poisen unit for 3 turn",
            false, HeroTargetType.ON_ATTACK, null));
    private static final String KAVE = gson.toJson(new Hero(HeroName.KAVE, 8000, 50,
            4, MinionAttackType.MELEE, 0, null, 1, 3, 0,
            "kave",
            "make a cell hly for 3 turns",
            false, HeroTargetType.A_CELL, null));
    private static final String ARASH = gson.toJson(new Hero(HeroName.ARASH, 10000, 30,
            2, MinionAttackType.RANGED, 6, null, 2, 2, 0,
            "arash",
            "attack every enemy in hero row",
            false, HeroTargetType.ALL_POWERS_IN_ROW, null));
    private static final String AFSANE = gson.toJson(new Hero(HeroName.AFSANE, 11000, 40,
            3, MinionAttackType.RANGED, 3, null, 1, 2, 0,
            "afsane",
            "dispel an enemy",
            false, HeroTargetType.AN_ENEMY_POWER, null));
    private static final String ESFANDIAR = gson.toJson(new Hero(HeroName.ESFANDIAR, 12000, 35,
            3, MinionAttackType.HYBRID, 3, null, 0, 0, 0,
            "esfandiar",
            "have 3 hly buff continuously",
            false, HeroTargetType.ITSELF, null));
    private static final String ROSTAM = gson.toJson(new Hero(HeroName.ROSTAM, 8000, 55,
            7, MinionAttackType.HYBRID, 4, null, 0, 0, 0,
            "rostam",
            "",
            false, null, null));


    //-------------------------------------------------SPELL--------------------------------------------------//


    private static final String TOTAL_DISARM = gson.toJson(new Spell("total_disarm", 1000, 0,
            SpellTargetType.AN_ENEMY_POWER, 0,
            "disarm until last of match",
            null, null, SpellName.TOTAL_DISARM));
    private static final String AREA_DISPEL = gson.toJson(new Spell("area_dispel", 1500, 2,
            SpellTargetType.TWO_IN_TWO_SQUARE, 0,
            "remove positive buff of enemy units and negative buff of friendly units",
            null, null, SpellName.AREA_DISPEL));
    private static final String EMPOWER = gson.toJson(new Spell("empower", 250, 1,
            SpellTargetType.A_FRIENDLY_POWER, 0,
            "add 2 to a unit power",
            null, null, SpellName.EMPOWER));
    private static final String FIREBALL = gson.toJson(new Spell("fireball", 400, 1,
            SpellTargetType.AN_ENEMY_POWER, 0,
            "attack 4 unit to a unit",
            null, null, SpellName.FIREBALL));
    private static final String GOD_STRENGTH = gson.toJson(new Spell("god_strength", 450, 2,
            SpellTargetType.FRIENDLY_HERO, 0,
            "add 4 unit to hero health",
            null, null, SpellName.GOD_STRENGTH));
    private static final String HELLFIRE = gson.toJson(new Spell("hellfire", 600, 3,
            SpellTargetType.TWO_IN_TWO_SQUARE, 0,
            "make a cell on fire for two turns",
            null, null, SpellName.HELLFIRE));
    private static final String LIGHTING_BOLT = gson.toJson(new Spell("lighting_bolt", 1250, 2,
            SpellTargetType.ENEMY_HERO, 0,
            "attack 4 unit to enemy hero",
            null, null, SpellName.LIGHTING_BOLT));
    private static final String POISON_LAKE = gson.toJson(new Spell("poison_lake", 900, 5,
            SpellTargetType.THREE_IN_THREE_SQUARE, 0,
            "meke all target cells poisoned for one round",
            null, null, SpellName.POISON_LAKE));
    private static final String MADNESS = gson.toJson(new Spell("madness", 650, 0,
            SpellTargetType.A_FRIENDLY_POWER, 0,
            "add 4 unit to a unit power but disarm it",
            null, null, SpellName.MADNESS));
    private static final String ALL_DISARM = gson.toJson(new Spell("all_disarm", 2000, 9,
            SpellTargetType.ALL_ENEMY_POWERS, 0,
            "disarm all enemy units",
            null, null, SpellName.ALL_DISARM));
    private static final String ALL_POISON = gson.toJson(new Spell("all_poison", 1500, 8,
            SpellTargetType.ALL_ENEMY_POWERS, 0,
            "poison all enemy unit for 4 turns",
            null, null, SpellName.ALL_POISON));
    private static final String DISPEL = gson.toJson(new Spell("dispel", 2100, 0,
            SpellTargetType.A_POWER, 0,
            "remove positive buff of enemy or negative buff of friendly",
            null, null, SpellName.DISPEL));
    private static final String HEALTH_WITH_PROFIT = gson.toJson(new Spell("health_with_profit", 2250, 0,
            SpellTargetType.A_FRIENDLY_POWER, 0,
            "give a weakness buff with 6 unit health decrease but also give 2 holy buff for 3 turn",
            null, null, SpellName.HEALTH_WITH_PROFIT));
    private static final String POWER_UP = gson.toJson(new Spell("power_up", 2500, 2,
            SpellTargetType.A_FRIENDLY_POWER, 0,
            "give a power buff with 6 unit power increase",
            null, null, SpellName.POWER_UP));
    private static final String ALL_POWER = gson.toJson(new Spell("all_power", 2000, 4,
            SpellTargetType.ALL_FRIENDLY_POWERS, 0,
            "give a power buff with 2 power increase to all units for all time",
            null, null, SpellName.ALL_POWER));
    private static final String ALL_ATTACK = gson.toJson(new Spell("all_attack", 1500, 4,
            SpellTargetType.ALL_ENEMY_IN_COLUMN, 0,
            "hit all enemys in column with 6 power",
            null, null, SpellName.ALL_ATTACK));
    private static final String WEAKENING = gson.toJson(new Spell("weakening", 1000, 1,
            SpellTargetType.AN_ENEMY_MINION, 0,
            "active a weakness buff with 4 unit decrease in a unit",
            null, null, SpellName.WEAKENING));
    private static final String SACRIFICE = gson.toJson(new Spell("sacrifice", 1600, 2,
            SpellTargetType.A_FRIENDLY_MINION, 0,
            "add a weakness buff with 6 unit deacrease & a power buff with 8 unit increase",
            null, null, SpellName.SACRIFICE));
    private static final String KINGS_GUARD = gson.toJson(new Spell("kings_guard", 1750, 9,
            SpellTargetType.AN_ENEMY_MINION_IN_EIGHT_HERO, 0,
            "kill the enemy",
            null, null, SpellName.KINGS_GUARD));
    private static final String SHOCK = gson.toJson(new Spell("shock", 1200, 1,
            SpellTargetType.AN_ENEMY_POWER, 0,
            "stun for 2 turns",
            null, null, SpellName.SHOCK));


    //-----------------------------------------ITEM-------------------------------------------//
    private static final String RANDOM_DAMAGE = gson.toJson(new Item(0, "random_damage",
            ItemName.RANDOM_DAMAGE, "give a random unit 2 power unit"));
    private static final String TERROR_HOOD = gson.toJson(new Item(5000, "terror_hood",
            ItemName.TERROR_HOOD, "on attack give a random unit a weakness buff with 2 power unit" +
            "decrease"));
    private static final String BLADES_AGILITY = gson.toJson(new Item(0, "blades_agility",
            ItemName.BLADES_AGILITY, "give a random unit 6 power unit"));
    private static final String KING_WISDOM = gson.toJson(new Item(9000, "king_wisdom",
            ItemName.KING_WISDOM, "add one extra mana in every turn"));
    private static final String ASSASINATION_DAGGER = gson.toJson(new Item(15000, "assasination_dagger",
            ItemName.ASSASINATION_DAGGER, "on landing every unit hit enemy hero with one unit"));
    private static final String POISONOUS_DAGGER = gson.toJson(new Item(7000, "poisonous_dagger",
            ItemName.POISONOUS_DAGGER, "on a friendly attack active a poison buff on a random unit"));
    private static final String SHOCK_HAMMER = gson.toJson(new Item(15000, "shock_hammer",
            ItemName.SHOCK_HAMMER, "friendly hero disarm unit that attack for one turn"));
    private static final String SOUL_EATER = gson.toJson(new Item(25000, "soul_eater",
            ItemName.SOUL_EATER, "on death of every friendly unit give a power buff with 1 to a " +
            "friendly unit"));
    private static final String GHOSL = gson.toJson(new Item(20000, "ghosl_tamid",
            ItemName.GHOSL, "every minion get two holy buff on spawn"));
    private static final String CHINESE_SHAMSHIR = gson.toJson(new Item(0, "chinese_shamshir",
            ItemName.CHINESE_SHAMSHIR, "for meelees add 5 power unit"));


    //------------------------------hash map initializing------------------------------
    static {
        minionHashMap.put(MinionName.FARS_KAMANDAR, FARS_KAMANDAR);
        minionHashMap.put(MinionName.FARS_SHAMSHIRZAN, FARS_SHAMSHIRZAN);
        minionHashMap.put(MinionName.FARS_NEYZEDAR, FARS_NEYZEDAR);
        minionHashMap.put(MinionName.FARS_ASBSAVAR, FARS_ASBSAVAR);
        minionHashMap.put(MinionName.FARS_PAHLAVAN, FARS_PAHLAVAN);
        minionHashMap.put(MinionName.FARS_SEPAHSALAR, FARS_SEPAHSALAR);
        minionHashMap.put(MinionName.TOORANEE_KAMANDAR, TOORANEE_KAMANDAR);
        minionHashMap.put(MinionName.TOORANEE_GHOLABSANG, TOORANEE_GHOLABSANG);
        minionHashMap.put(MinionName.TOORANEE_NEYZEDAR, TOORANEE_NEYZEDAR);
        minionHashMap.put(MinionName.TOORANE_JASOS, TOORANEE_JASOS);
        minionHashMap.put(MinionName.TOORANEE_GORZDAR, TOORANEE_GORZDAR);
        minionHashMap.put(MinionName.TOORANEE_SHAHZADE, TOORANEE_SHAHZADE);
        minionHashMap.put(MinionName.BLACK_DEEV, BLACK_DEEV);
        minionHashMap.put(MinionName.SANGANDAZ_GHOLL, SANGANDAZ_GHOLL);
        minionHashMap.put(MinionName.EAGLE, EAGLE);
        minionHashMap.put(MinionName.GORAZ_DEEV, GORAZ_DEEV);
        minionHashMap.put(MinionName.ONE_EYE_GHOOL, ONE_EYE_GHOOL);
        minionHashMap.put(MinionName.POISON_SNAKE, POISON_SNAKE);
        minionHashMap.put(MinionName.DRAGON_FIRE, DRAGON_FIRE);
        minionHashMap.put(MinionName.DARANDE_SHIR, DARANDE_SHIR);
        minionHashMap.put(MinionName.GHOOL_SNAKE, GHOOL_SNAKE);
        minionHashMap.put(MinionName.WHITE_WOLF, WHITE_WOLF);
        minionHashMap.put(MinionName.PALANG, PALANG);
        minionHashMap.put(MinionName.WOLF, WOLF);
        minionHashMap.put(MinionName.JADOOGAR, JADOOGAR);
        minionHashMap.put(MinionName.JADOOGAR_AZAM, JADOOGAR_AZAM);
        minionHashMap.put(MinionName.JEN, JEN);
        minionHashMap.put(MinionName.WILD_GORAZ, WILD_GORAZ);
        minionHashMap.put(MinionName.PIRAN, PIRAN);
        minionHashMap.put(MinionName.GEEV, GEEV);
        minionHashMap.put(MinionName.BAHMAN, BAHMAN);
        minionHashMap.put(MinionName.ASHKBOOS, ASHKBOS);
        minionHashMap.put(MinionName.EERAG, EERAG);
        minionHashMap.put(MinionName.BIG_GHOOL, BIG_GHOOL);
        minionHashMap.put(MinionName.DOSAR_GHOOL, DOSAR_GHOOL);
        minionHashMap.put(MinionName.NANE_SARMA, NANE_SARMA);
        minionHashMap.put(MinionName.FOOLAD_ZEREH, FOOLAD_ZEREH);
        minionHashMap.put(MinionName.SIAVOSH, SIYAVOSH);
        minionHashMap.put(MinionName.SHAH_GOOL, SHAH_GHOOL);
        minionHashMap.put(MinionName.ARJANG_DEEV, ARJANG_DEEV);


        heroHashMap.put(HeroName.WHITE_DEEV, WHITE_DEEV);
        heroHashMap.put(HeroName.SIMORGH, SIMORG);
        heroHashMap.put(HeroName.EJDEHA, EJDEHA);
        heroHashMap.put(HeroName.RAKHSH, RAKHSH);
        heroHashMap.put(HeroName.ZAHAK, ZAHAK);
        heroHashMap.put(HeroName.KAVE, KAVE);
        heroHashMap.put(HeroName.ARASH, ARASH);
        heroHashMap.put(HeroName.AFSANE, AFSANE);
        heroHashMap.put(HeroName.ESFANDIAR, ESFANDIAR);
        heroHashMap.put(HeroName.ROSTAM, ROSTAM);


        itemHashMap.put(ItemName.TAJ_DANAYEE, TAJ_DANAYEE);
        itemHashMap.put(ItemName.NAMOOS_SEPAR, NAMOOS_SEPAR);
        itemHashMap.put(ItemName.KAMAN_DAMOOL, KAMAN_DAMOL);
        itemHashMap.put(ItemName.NOOSH_DAROO, NOOSH_DARO);
        itemHashMap.put(ItemName.TIR_DOSHAKH, TIR_DOSHAKH);
        itemHashMap.put(ItemName.PAR_SIMORGH, PAR_SIMORGH);
        itemHashMap.put(ItemName.EKSIR, EKSIR);
        itemHashMap.put(ItemName.MAJOON_MANA, MAJOON_MANA);
        itemHashMap.put(ItemName.MAJOON_ROIEEN, MAJOON_ROIEEN);
        itemHashMap.put(ItemName.NEFRIN_MARG, NEFRIN_MARG);
        itemHashMap.put(ItemName.RANDOM_DAMAGE, RANDOM_DAMAGE);
        itemHashMap.put(ItemName.TERROR_HOOD, TERROR_HOOD);
        itemHashMap.put(ItemName.BLADES_AGILITY, BLADES_AGILITY);
        itemHashMap.put(ItemName.KING_WISDOM, KING_WISDOM);
        itemHashMap.put(ItemName.ASSASINATION_DAGGER, ASSASINATION_DAGGER);
        itemHashMap.put(ItemName.POISONOUS_DAGGER, POISONOUS_DAGGER);
        itemHashMap.put(ItemName.SHOCK_HAMMER, SHOCK_HAMMER);
        itemHashMap.put(ItemName.SOUL_EATER, SOUL_EATER);
        itemHashMap.put(ItemName.GHOSL, GHOSL);
        itemHashMap.put(ItemName.CHINESE_SHAMSHIR, CHINESE_SHAMSHIR);


        spellHashMap.put(SpellName.TOTAL_DISARM, TOTAL_DISARM);
        spellHashMap.put(SpellName.AREA_DISPEL, AREA_DISPEL);
        spellHashMap.put(SpellName.EMPOWER, EMPOWER);
        spellHashMap.put(SpellName.FIREBALL, FIREBALL);
        spellHashMap.put(SpellName.GOD_STRENGTH, GOD_STRENGTH);
        spellHashMap.put(SpellName.HELLFIRE, HELLFIRE);
        spellHashMap.put(SpellName.LIGHTING_BOLT, LIGHTING_BOLT);
        spellHashMap.put(SpellName.POISON_LAKE, POISON_LAKE);
        spellHashMap.put(SpellName.MADNESS, MADNESS);
        spellHashMap.put(SpellName.ALL_DISARM, ALL_DISARM);
        spellHashMap.put(SpellName.ALL_POISON, ALL_POISON);
        spellHashMap.put(SpellName.DISPEL, DISPEL);
        spellHashMap.put(SpellName.HEALTH_WITH_PROFIT, HEALTH_WITH_PROFIT);
        spellHashMap.put(SpellName.POWER_UP, POWER_UP);
        spellHashMap.put(SpellName.ALL_ATTACK, ALL_ATTACK);
        spellHashMap.put(SpellName.WEAKENING, WEAKENING);
        spellHashMap.put(SpellName.SACRIFICE, SACRIFICE);
        spellHashMap.put(SpellName.KINGS_GUARD, KINGS_GUARD);
        spellHashMap.put(SpellName.SHOCK, SHOCK);
        spellHashMap.put(SpellName.ALL_POWER, ALL_POWER);
    }

}
