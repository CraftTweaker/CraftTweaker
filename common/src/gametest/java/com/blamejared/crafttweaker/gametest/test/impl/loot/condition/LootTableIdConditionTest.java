package com.blamejared.crafttweaker.gametest.test.impl.loot.condition;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdCondition;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class LootTableIdConditionTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        DataResult<JsonElement> encodeResult = encode(LootTableIdCondition.CODEC, getSubject());
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        assertThat(jsonResult.getAsJsonObject(), is(getJson()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<LootTableIdCondition, JsonElement>> decode = decode(LootTableIdCondition.CODEC, getJson());
        Pair<LootTableIdCondition, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(getSubject()));
    }
    
    private JsonElement getJson() {
        
        return parseJson("""
                {
                    "table_id": "crafttweaker:test"
                }
                """);
    }
    
    private LootTableIdCondition getSubject() {
        
        return new LootTableIdCondition(CraftTweakerConstants.rl("test"));
    }
    
}
