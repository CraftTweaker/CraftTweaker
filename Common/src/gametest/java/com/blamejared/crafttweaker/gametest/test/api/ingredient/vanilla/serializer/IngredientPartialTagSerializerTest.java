package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientPartialTag;
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
public class IngredientPartialTagSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        IngredientPartialTag subject = IngredientPartialTag.of(Items.APPLE.getDefaultInstance());
        DataResult<JsonElement> encodeResult = encodeWild(IngredientPartialTagSerializer.CODEC, subject);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(parseJson("""
                {
                  "id": "minecraft:apple",
                  "Count": 1
                }""")));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<IngredientPartialTag, JsonElement>> decode = decodeWild(IngredientPartialTagSerializer.CODEC, parseJson("""
                {
                  "id": "minecraft:apple",
                  "Count": 1
                }"""));
        Pair<IngredientPartialTag, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        IngredientPartialTag expected = IngredientPartialTag.of(Items.APPLE.getDefaultInstance());
        assertThat(decodeResult.getFirst(), is(expected));
    }
    
}
