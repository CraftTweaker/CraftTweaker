package com.blamejared.crafttweaker.gametest.test.api.ingredient;


import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IngredientConverter;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.gametest.framework.ScriptBuilder;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.zencode.GameTestGlobals;
import com.blamejared.crafttweaker.gametest.logging.appender.GameTestLoggerAppender;
import com.blamejared.crafttweaker.impl.script.scriptrun.GameTestScriptRunner;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

@CraftTweakerGameTestHolder
public class IngredientArrayTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToData(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file("ingredient/ingredient_arrays_to_data.zs");
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        IData iData = GameTestGlobals.data().get(0);
        
        IData expected = new ListData();
        expected.add(new MapData(Map.of("count", new IntData(1), "item", new StringData("minecraft:tnt"))));
        expected.add(new MapData(Map.of("count", new IntData(1), "item", new StringData("minecraft:diamond"))));
        assertThat(iData, is(expected));
    }
}
