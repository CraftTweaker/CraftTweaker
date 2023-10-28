package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientList;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.platform.Services;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class IngredientListSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        List<Ingredient> ingredients = List.of(Ingredient.of(Items.APPLE), Ingredient.of(Items.ARROW), IngredientList.ingredient(List.of(Ingredient.of(Items.BARREL))));
        IngredientList subject = IngredientList.of(ingredients);
        DataResult<JsonElement> encodeResult = encode(IngredientListSerializer.CODEC, subject);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(parseJson("""
                {
                  "ingredients": [
                    {
                      "item": "minecraft:apple"
                    },
                    {
                      "item": "minecraft:arrow"
                    },
                    {
                      "%stype": "crafttweaker:list",
                      "value": {
                        "ingredients": [
                          {
                            "item": "minecraft:barrel"
                          }
                        ]
                      }
                    }
                  ]
                }""".formatted(Services.PLATFORM.getPlatformName().equals("Fabric") ? "fabric:" : ""))));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<IngredientList, JsonElement>> decode = decode(IngredientListSerializer.CODEC, parseJson("""
                {
                  "ingredients": [
                    {
                      "item": "minecraft:apple"
                    },
                    {
                      "item": "minecraft:arrow"
                    },
                    {
                      "%stype": "crafttweaker:list",
                      "value": {
                        "ingredients": [
                          {
                            "item": "minecraft:barrel"
                          }
                        ]
                      }
                    }
                  ]
                }""".formatted(Services.PLATFORM.getPlatformName().equals("Fabric") ? "fabric:" : "")));
        
        List<Ingredient> ingredients = List.of(Ingredient.of(Items.APPLE), Ingredient.of(Items.ARROW), IngredientList.ingredient(List.of(Ingredient.of(Items.BARREL))));
        IngredientList subject = IngredientList.of(ingredients);
        Pair<IngredientList, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(subject));
    }
    
}
