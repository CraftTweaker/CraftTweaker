package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionAnyDamage;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientConditioned;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class IngredientConditionedSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        IngredientConditioned subject = IngredientConditioned.of(new IIngredientConditioned<>(immutableStack(Items.APPLE), ConditionAnyDamage.getInstance()));
        DataResult<JsonElement> encodeResult = encode(IngredientConditionedSerializer.CODEC, subject);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(parseJson("""
                {
                  "base": {
                    "item": "minecraft:apple"
                  },
                  "condition": {
                    "type": "crafttweaker:any_damage"
                  }
                }""")));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<IngredientConditioned, JsonElement>> decode = decodeWild(IngredientConditionedSerializer.CODEC, parseJson("""
                {
                  "base": {
                    "item": "minecraft:apple"
                  },
                  "condition": {
                    "type": "crafttweaker:any_damage"
                  }
                }"""));
        Pair<IngredientConditioned, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        IngredientConditioned expected = IngredientConditioned.of(new IIngredientConditioned<>(immutableStack(Items.APPLE), ConditionAnyDamage.getInstance()));
        assertThat(decodeResult.getFirst(), is(expected));
    }
    
}
