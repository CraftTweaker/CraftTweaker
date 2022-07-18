package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.JSONConverter;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SuppressWarnings({"ConstantConditions", "RedundantCast"})
@CraftTweakerGameTestHolder
public class JsonConverterTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullElementReturnsNull(GameTestHelper helper) {
        
        final IData convert = JSONConverter.convert((JsonElement) null);
        assertThat(convert, is(nullValue()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullObjectReturnsNull(GameTestHelper helper) {
        
        final IData convert = JSONConverter.convert((JsonObject) null);
        
        assertThat(convert, is(nullValue()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonNullReturnsNull(GameTestHelper helper) {
        
        final JsonElement jsonNull = JsonNull.INSTANCE;
        
        final IData convert = JSONConverter.convert(jsonNull);
        
        assertThat(convert, is(nullValue()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void numberTypeCreatesNumberData(GameTestHelper helper) {
        
        final int value = 10;
        final JsonPrimitive numberPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(numberPrimitive);
        
        assertThat(convert, is(new IntData(value)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void stringCreatesStringData(GameTestHelper helper) {
        
        final String value = "Hello world";
        final JsonPrimitive stringPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(stringPrimitive);
        
        assertThat(convert, is(new StringData(value)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void booleanCreatesStringData(GameTestHelper helper) {
        
        final boolean value = false;
        final JsonPrimitive booleanPrimitive = new JsonPrimitive(value);
        
        final IData convert = JSONConverter.convert(booleanPrimitive);
        
        assertThat(convert, is(new StringData(String.valueOf(value))));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonArrayReturnsListData(GameTestHelper helper) {
        
        final JsonArray jsonArray = new JsonArray();
        jsonArray.add(1);
        jsonArray.add(2);
        jsonArray.add(3);
        
        final IData convert = JSONConverter.convert(jsonArray);
        
        
        assertThat(convert, is(instanceOf(ListData.class)));
        ListData listData = (ListData) convert;
        assertThat(listData.getAt(0), is(new IntData(1)));
        assertThat(listData.getAt(1), is(new IntData(2)));
        assertThat(listData.getAt(2), is(new IntData(3)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void jsonObjectReturnsMapData(GameTestHelper helper) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("num", new JsonPrimitive(0));
        jsonObject.addProperty("greeting", "Hello World");
        
        final MapData convert = JSONConverter.convert(jsonObject);
        
        assertThat(convert.getKeySet(), contains("num", "greeting"));
        assertThat(convert.getAt("num"), is(new IntData(0)));
        assertThat(convert.getAt("greeting"), is(new StringData("Hello World")));
    }
    
    
}
