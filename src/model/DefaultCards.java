package model;

import com.google.gson.Gson;
import model.cards.Minion;
import model.enumerations.CardType;
import model.enumerations.MinionAttackType;
import model.enumerations.MinionName;

import java.util.HashMap;

public class DefaultCards {
    public static Minion getMinion(MinionName name) {
        return null;
    }

    HashMap<MinionName, Minion> minionHashMap = new HashMap<>();

    // minions
    private static Gson gson = new Gson();
    private static final String FARS_KAMANDAR = gson.toJson(new Minion("fars_kamandar",300,2,6,
            4, MinionAttackType.RANGED,7,null,CardType.MINION,0,"",
            MinionName.FARS_KAMANDAR,true));

    private static final String FARS_SHAMSHIRZAN = gson.toJson(new Minion("fars_shamshirzan",400,2,
            6,4, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "Stun opponent for 1 turn on attack",
            MinionName.FARS_SHAMSHIRZAN, true));
    private static final String FARS_NEYZEDAR = gson.toJson(new Minion("fars_NEYZEDAR",500,1,
            5,3, MinionAttackType.HYBRID,3,null,CardType.MINION,0,
            "",
            MinionName.FARS_NEYZEDAR, true));
    private static final String FARS_ASBSAVAR = gson.toJson(new Minion("fars_asbsavar",200,4,
            10,6, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "",
            MinionName.FARS_ASBSAVAR, true));
    private static final String FARS_PAHLAVAN = gson.toJson(new Minion("fars_pahlavan",600,9,
            24,6, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "attack 5 more unit than that attack to a unit",
            MinionName.FARS_PAHLAVAN, true));
    private static final String FARS_SEPAHSALAR = gson.toJson(new Minion("fars_sepahsalar",800,7,
            12,4, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "Combo",
            MinionName.FARS_SEPAHSALAR, true));
    private static final String TOORANEE_KAMANDAR = gson.toJson(new Minion("toorane_kamandar",500,1,
            3,4, MinionAttackType.RANGED,5,null,CardType.MINION,0,
            "",
            MinionName.TOORANEE_KAMANDAR, false));
    private static final String TOORANEE_GHOLABSANG = gson.toJson(new Minion("toorane_gholabsang",600,1,
            4,2, MinionAttackType.RANGED,7,null,CardType.MINION,0,
            "",
            MinionName.TOORANEE_GHOLABSANG, false));
    private static final String TOORANEE_NEYZEDAR = gson.toJson(new Minion("toorane_neyzedar",600,1,
            4,4, MinionAttackType.HYBRID,3,null,CardType.MINION,0,
            "",
            MinionName.TOORANEE_NEYZEDAR, false));
    private static final String TOORANEE_JASOS = gson.toJson(new Minion("toorane_jasos",700,4,
            6,6, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "Disarm enemy unit for 1 turn and posoined for 4 turn",
            MinionName.TOORANE_JASOS, false));
    private static final String TOORANEE_GORZDAR = gson.toJson(new Minion("toorane_gorzdar",450,2,
            3,10, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "",
            MinionName.TOORANEE_GORAZ, false));
    private static final String TOORANEE_SHAHZADE = gson.toJson(new Minion("toorane_shahzade",800,6,
            6,10, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "Combo",
            MinionName.TOORANEE_SHAHZADE, false));
    private static final String BLACK_DEEV = gson.toJson(new Minion("black_deev",300,9,
            14,10, MinionAttackType.HYBRID,7,null,CardType.MINION,0,
            "",
            MinionName.BLACK_DEEV, false));
    private static final String SANGANDAZ_GHOLL = gson.toJson(new Minion("sangandaz_gholl",300,9,
            12,12, MinionAttackType.RANGED,7,null,CardType.MINION,0,
            "",
            MinionName.SANGANDAZ_GHOLL, false));
    private static final String EAGLE = gson.toJson(new Minion("eagle",200,2,
            0,2, MinionAttackType.RANGED,3,null,CardType.MINION,0,
            "have 10 power buff passive",
            MinionName.EAGLE, false));    ///helth is a mystyry
    private static final String GORAZ_DEEV = gson.toJson(new Minion("goraz_deev",300,6,
            16,8, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "",
            MinionName.GORAZ_DEEV, false));
    private static final String ONE_EYE_GHOOL = gson.toJson(new Minion("one_eye_ghool",500,7,
            12,11, MinionAttackType.HYBRID,3,null,CardType.MINION,0,
            "Attack 2 unit to minions around him on death",
            MinionName.ONE_EYE_GHOOL, false));
    private static final String POISON_SNAKE = gson.toJson(new Minion("poison_snake",300,4,
            5,6, MinionAttackType.RANGED,4,null,CardType.MINION,0,
            "poison enemy unit 3 turn on attack",
            MinionName.POISON_SNAKE, false));
    private static final String DRAGON_FIRE = gson.toJson(new Minion("dragon_fire",250,5,
            9,5, MinionAttackType.RANGED,4,null,CardType.MINION,0,
            "",
            MinionName.DRAGON_FIRE, false));
    private static final String DARANDE_SHIR = gson.toJson(new Minion("darande_shir",600,2,
            1,8, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "holy buff doesn't have effect on this card on attack",
            MinionName.DARANDE_SHIR, false));
    private static final String GHOOL_SNAKE = gson.toJson(new Minion("ghool_snake",500,8,
            14,7, MinionAttackType.RANGED,5,null,CardType.MINION,0,
            "Reverse holy buff on spawn on around minions",
            MinionName.GHOOL_SNAKE, false));
    private static final String WHITE_WOLF = gson.toJson(new Minion("white_wolf",400,5,
            8,2, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "when attack a minion reduce 6 unit in next turn and 4 unit in next 2 turn of health",
            MinionName.WHITE_WOLF, false));
    private static final String PALANG = gson.toJson(new Minion("palang",400,4,
            6,2, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "when attack a minion next turn its health reduce 8 unit",
            MinionName.PALANG, false));
    private static final String WOLF = gson.toJson(new Minion("wolf",400,3,
            6,1, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "when attack a minion next turn its health reduce 6 unit",
            MinionName.WOLF, false));
    private static final String JADOOGAR = gson.toJson(new Minion("jadoogar",550,4,
            5,4, MinionAttackType.RANGED,3,null,CardType.MINION,0,
            "give itself and around friendly minions one power with 2 strength and one weakness with 1 strength" +
                    "for one turn",
            MinionName.JADOOGAR, false));
    private static final String JADOOGAR_AZAM = gson.toJson(new Minion("jadoogar_azam",550,6,
            6,6, MinionAttackType.RANGED,5,null,CardType.MINION,0,
            "give itself and 8 around minion one power with 2 strength and one holy buff continus passively",
            MinionName.JADOOGAR_AZAM, false));
    private static final String JEN = gson.toJson(new Minion("jen",500,5,
            10,4, MinionAttackType.RANGED,4,null,CardType.MINION,0,
            "give every friendly minion 1 power buff continus on turn",
            MinionName.JEN, false));
    private static final String WILD_GORAZ = gson.toJson(new Minion("wild_goraz",500,6,
            10,14, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "can't be disarm",
            MinionName.WILD_GORAZ, false));
    private static final String PIRAN = gson.toJson(new Minion("piran",400,8,
            20,12, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "can't be poisened",
            MinionName.PIRAN, false));
    private static final String GEEV = gson.toJson(new Minion("geev",450,4,
            5,7, MinionAttackType.RANGED,5,null,CardType.MINION,0,
            "",
            MinionName.GEEV, false));   //not complete
    private static final String BAHMAN = gson.toJson(new Minion("bahman",450,8,
            16,9, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "randomly hit a enemy minion with 16 power on spawn",
            MinionName.BAHMAN, false));
    private static final String ASHKBOS = gson.toJson(new Minion("ashkbos",400,7,
            14,8, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "units with lower attack power can't attack him",
            MinionName.ASHKBOOS, false));
    private static final String  EERAG = gson.toJson(new Minion("eerag",500,4,
            6,20, MinionAttackType.RANGED,3,null,CardType.MINION,0,
            "",
            MinionName.EERAG, false));
    private static final String BIG_GHOOL = gson.toJson(new Minion("big_ghool",600,9,
            30,8, MinionAttackType.HYBRID,2,null,CardType.MINION,0,
            "",
            MinionName.BIG_GHOOL, false));
    private static final String DOSAR_GHOOL = gson.toJson(new Minion("dosar_ghool",550,4,
            10,4, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "when attack a unit remove every positive buff that is active on it",
            MinionName.DOSAR_GHOOL, false));
    private static final String NANE_SARMA = gson.toJson(new Minion("nane_sarma",400,3,
            3,4, MinionAttackType.RANGED,5,null,CardType.MINION,0,
            "stun minions around her for 1 turn on spawn",
            MinionName.NANE_SARMA, false));
    private static final String FOOLAD_ZEREH = gson.toJson(new Minion("foolad_zereh",650,3,
            1,1, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "have 12 holy buff passively",
            MinionName.FOOLAD_ZEREH, false));
    private static final String SIYAVOSH = gson.toJson(new Minion("siyavosh",350,4,
            8,5, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "hit enemy hero with 6 strenght on death",
            MinionName.SIAVOSH, false));
    private static final String SHAH_GHOOL = gson.toJson(new Minion("shah_ghool",600,5,
            10,4, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "combo",
            MinionName.SHAH_GOOL, false));
    private static final String ARJANG_DEEV = gson.toJson(new Minion("arjang_deev",600,3,
            6,6, MinionAttackType.MELEE,0,null,CardType.MINION,0,
            "combo",
            MinionName.ARJANG_DEEV, false));



    //HERO

}
