package com.blamejared.crafttweaker.gametest.test.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdCondition;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdRegexCondition;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class CTShapedRecipeSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        DataResult<JsonElement> encodeResult = encode(CTShapedRecipeSerializer.CODEC, getSubject());
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(getJson()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<CTShapedRecipe, JsonElement>> decode = decode(CTShapedRecipeSerializer.CODEC, getJson());
        Pair<CTShapedRecipe, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(getSubject()));
    }
    
    private JsonElement getJson() {
        
        return parseJson("""
                {
                  "output": {
                    "item": {
                      "id": "minecraft:apple",
                      "Count": 1
                    },
                    "mutable": false
                  },
                  "ingredients": [
                    [
                      {
                        "item": "minecraft:diamond"
                      },
                      {
                        "item": "minecraft:barrel"
                      }
                    ]
                  ],
                  "mirror_axis": "all"
                }""");
    }
    
    private CTShapedRecipe getSubject() {
        
        return new CTShapedRecipe(immutableStack(Items.APPLE), new IIngredient[][]{{immutableStack(Items.DIAMOND), immutableStack(Items.BARREL)}}, MirrorAxis.ALL);
    }
}
