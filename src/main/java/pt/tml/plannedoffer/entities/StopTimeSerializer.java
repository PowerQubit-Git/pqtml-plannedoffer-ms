package pt.tml.plannedoffer.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StopTimeSerializer implements JsonSerializer<StopTime>
{
    @Override
    public JsonElement serialize(StopTime src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context)
    {
        final JsonObject json = new JsonObject();


        json.addProperty("firstName", "000");
        json.addProperty("lastName", "000");
        return json;
    }
}
