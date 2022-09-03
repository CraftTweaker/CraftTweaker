package com.blamejared.crafttweaker.api.data.converter;

import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONConverter {
    
    public static IData convert(JsonElement json) {
        
        if(json == null) {
            return null;
        }
        
        if(json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            if(primitive.isString() || primitive.isBoolean()) {
                return new StringData(primitive.getAsString());
            } else {
                
                Number number = primitive.getAsNumber();
                if(number == null) {
                    return null;
                }
                
                if(number instanceof Integer) {
                    return new IntData(number.intValue());
                } else if(number instanceof Byte) {
                    return new ByteData(number.byteValue());
                } else if(number instanceof Double) {
                    return new DoubleData(number.doubleValue());
                } else if(number instanceof Float) {
                    return new FloatData(number.floatValue());
                } else if(number instanceof Long) {
                    return new LongData(number.longValue());
                } else if(number instanceof Short) {
                    return new ShortData(number.shortValue());
                }
                
                return new DoubleData(number.doubleValue());
            }
            
        } else if(json.isJsonArray()) {
            final JsonArray asJsonArray = json.getAsJsonArray();
            final List<IData> dataList = new ArrayList<>(asJsonArray.size());
            for(JsonElement jsonElement : asJsonArray) {
                dataList.add(JSONConverter.convert(jsonElement));
            }
            return new ListData(dataList);
        } else if(json.isJsonObject()) {
            return convert(json.getAsJsonObject());
        } else {
            //Must be jsonNull
            //Otherwise, good as fallthrough I guess?
            return null;
        }
    }
    
    public static MapData convert(JsonObject jsonObject) {
        
        if(jsonObject == null) {
            return null;
        }
        
        final Map<String, IData> dataMap = new HashMap<>();
        for(Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
            dataMap.put(e.getKey(), JSONConverter.convert(e.getValue()));
        }
        return new MapData(dataMap);
    }
    
}
