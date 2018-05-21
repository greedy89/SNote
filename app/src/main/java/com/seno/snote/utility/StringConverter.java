package com.seno.snote.utility;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class StringConverter implements JsonSerializer,JsonDeserializer {
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        if(src == null){
            return new JsonPrimitive("");
        }else{
            return new JsonPrimitive(src.toString());
        }

    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}
