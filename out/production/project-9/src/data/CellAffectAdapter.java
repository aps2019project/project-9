package data;

import com.google.gson.*;
import model.Cell;
import model.buffs.Buff;
import model.buffs.DelayBuff;
import model.cellaffects.CellAffect;

import java.lang.reflect.Type;

public class CellAffectAdapter implements JsonSerializer<CellAffect>, JsonDeserializer<CellAffect> {
    @Override
    public JsonElement serialize(CellAffect buff, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(buff.getClass().getSimpleName()));
        result.add("properties", context.serialize(buff, buff.getClass()));
        return result;
    }

    @Override
    public CellAffect deserialize(JsonElement json, Type t, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("model.cellaffects." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
