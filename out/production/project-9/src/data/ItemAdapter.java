package data;

import com.google.gson.*;
import model.buffs.Buff;
import model.buffs.DelayBuff;
import model.items.Item;

import java.lang.reflect.Type;

public class ItemAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {
    @Override
    public JsonElement serialize(Item item, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(item.getClass().getSimpleName()));
        result.add("properties", context.serialize(item, item.getClass()));
        return result;
    }

    @Override
    public Item deserialize(JsonElement json, Type t, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("model.items." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
