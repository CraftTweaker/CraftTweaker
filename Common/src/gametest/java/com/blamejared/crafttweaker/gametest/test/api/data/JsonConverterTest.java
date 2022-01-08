package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.JSONConverter;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings({"ConstantConditions", "RedundantCast"})
@CraftTweakerGameTestHolder
public class JsonConverterTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullElementReturnsNull(GameTestHelper helper) {
        
        final IData convert = JSONConverter.convert((JsonElement) null);
        
        assertThat(convert).isNull();
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullObjectReturnsNull(GameTestHelper helper) {
        
        final IData convert = JSONConverter.convert((JsonObject) null);
        
        assertThat(convert).isNull();
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonNullReturnsNull(GameTestHelper helper) {
        
        final JsonElement jsonNull = JsonNull.INSTANCE;
        
        final IData convert = JSONConverter.convert(jsonNull);
        
        assertThat(convert).isNull();
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void numberTypeCreatesNumberData(GameTestHelper helper) {
        
        final int value = 10;
        final JsonPrimitive numberPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(numberPrimitive);
        
        assertThat(convert).isEqualTo(new IntData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void stringCreatesStringData(GameTestHelper helper) {
        
        final String value = "Hello world";
        final JsonPrimitive stringPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(stringPrimitive);
        
        assertThat(convert).isEqualTo(new StringData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void booleanCreatesStringData(GameTestHelper helper) {
        
        final boolean value = false;
        final JsonPrimitive booleanPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(booleanPrimitive);
        
        assertThat(convert).isEqualTo(new StringData(String.valueOf(value)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonArrayReturnsListData(GameTestHelper helper) {
        
        final JsonArray jsonArray = new JsonArray();
        jsonArray.add(1);
        jsonArray.add(2);
        jsonArray.add(3);
        
        final IData convert = JSONConverter.convert(jsonArray);
        
        expect().that(convert).isInstanceOf(ListData.class);
        ListData listData = (ListData) convert;
        assertThat(listData).at(0).isEqualTo(new IntData(1));
        assertThat(listData).at(1).isEqualTo(new IntData(2));
        assertThat(listData).at(2).isEqualTo(new IntData(3));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonObjectReturnsMapData(GameTestHelper helper) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("num", new JsonPrimitive(0));
        jsonObject.addProperty("greeting", "Hello World");
        
        final MapData convert = JSONConverter.convert(jsonObject);
        
        assertThat(convert).keySet().containsExactly("num", "greeting");
        assertThat(convert).at("num").isEqualTo(new IntData(0));
        assertThat(convert).at("greeting").isEqualTo(new StringData("Hello World"));
    }
    
    
}
