package data;

import com.google.gson.*;
import model.buffs.Buff;
import model.buffs.DelayBuff;
import model.specialPower.SpecialPower;

import java.lang.reflect.Type;

public class SpecialPowerAdapter implements JsonSerializer<SpecialPower>, JsonDeserializer<SpecialPower> {
    @Override
    public JsonElement serialize(SpecialPower specialPower, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(specialPower.getClass().getSimpleName()));
        result.add("properties", context.serialize(specialPower, specialPower.getClass()));
        return result;
    }

    @Override
    public SpecialPower deserialize(JsonElement json, Type t, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("model.specialPower." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
