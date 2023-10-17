package com.blamejared.crafttweaker.gametest.test.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
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
public class CTShapelessRecipeSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        DataResult<JsonElement> encodeResult = encode(CTShapelessRecipeSerializer.CODEC, getSubject());
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(getJson()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<CTShapelessRecipe, JsonElement>> decode = decode(CTShapelessRecipeSerializer.CODEC, getJson());
        Pair<CTShapelessRecipe, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
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
                      {
                        "item": "minecraft:diamond"
                      },
                      {
                        "item": "minecraft:barrel"
                      }
                  ]
                }""");
    }
    
    private CTShapelessRecipe getSubject() {
        
        return new CTShapelessRecipe(immutableStack(Items.APPLE), new IIngredient[]{immutableStack(Items.DIAMOND), immutableStack(Items.BARREL)});
    }
}
