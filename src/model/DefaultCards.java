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

    private static final String FARS_KAMANDAR = gson.toJson(new Minion(300, 2, CardType.MINION
            , "kamandar_fars",
            0, "", MinionName.FARS_KAMANDAR, MinionAttackType.RANGED,
            6, 4, 7, null, true));


}
