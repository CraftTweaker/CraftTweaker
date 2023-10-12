package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReuse;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientTransformed;
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
public class IngredientTransformedSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        IngredientTransformed subject = IngredientTransformed.of(new IIngredientTransformed<>(immutableStack(Items.APPLE), TransformReuse.getInstance()));
        DataResult<JsonElement> encodeResult = encodeWild(IngredientTransformedSerializer.CODEC, subject);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(parseJson("""
                {
                  "base": {
                    "item": "minecraft:apple"
                  },
                  "transformer": {
                    "type": "crafttweaker:transform_reuse"
                  }
                }""")));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {

        DataResult<Pair<IngredientTransformed, JsonElement>> decode = decodeWild(IngredientTransformedSerializer.CODEC, parseJson("""
                {
                  "base": {
                    "item": "minecraft:apple"
                  },
                  "transformer": {
                    "type": "crafttweaker:transform_reuse"
                  }
                }"""));
        Pair<IngredientTransformed, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        IngredientTransformed expected = IngredientTransformed.of(new IIngredientTransformed<>(immutableStack(Items.APPLE), TransformReuse.getInstance()));
        assertThat(decodeResult.getFirst(), is(expected));
    }
    
}
