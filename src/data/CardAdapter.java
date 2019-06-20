package data;

import com.google.gson.*;
import model.Cell;
import model.buffs.Buff;
import model.buffs.DelayBuff;
import model.cards.Card;
import model.cellaffects.CellAffect;

import java.lang.reflect.Type;

public class CardAdapter implements JsonSerializer<Card>, JsonDeserializer<Card> {
    @Override
    public JsonElement serialize(Card card, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(card.getClass().getSimpleName()));
        result.add("properties", context.serialize(card, card.getClass()));
        return result;
    }

    @Override
    public Card deserialize(JsonElement json, Type t, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("model.cards." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
