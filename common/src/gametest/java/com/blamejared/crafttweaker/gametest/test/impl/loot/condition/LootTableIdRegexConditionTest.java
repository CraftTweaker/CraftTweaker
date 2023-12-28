package com.blamejared.crafttweaker.gametest.test.impl.loot.condition;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
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

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class LootTableIdRegexConditionTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        DataResult<JsonElement> encodeResult = encode(LootTableIdRegexCondition.CODEC, getSubject());
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult, is(getJson()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<LootTableIdRegexCondition, JsonElement>> decode = decode(LootTableIdRegexCondition.CODEC, getJson());
        Pair<LootTableIdRegexCondition, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        // You can't actually test pattern equality...
        assertThat(decodeResult.getFirst().regex().pattern(), is(getSubject().regex().pattern()));
    }
    
    private JsonElement getJson() {
        
        return parseJson(".*chest.*");
    }
    
    private LootTableIdRegexCondition getSubject() {
        
        return new LootTableIdRegexCondition(Pattern.compile(".*chest.*"));
    }
    
}
