package com.blamejared.crafttweaker.gametest.test.api.item;

import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.ScriptBuilder;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.logging.appender.GameTestLoggerAppender;
import com.blamejared.crafttweaker.impl.script.scriptrun.GameTestScriptRunner;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@CraftTweakerGameTestHolder
public class MatchTests implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPartialNbtMatchingWorks(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file("item/ensure_partial_nbt_matching.zs");
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "false");
        log.assertOutput(1, "true");
        log.assertOutput(2, "true");
        log.assertOutput(3, "false");
        log.assertOutput(4, "true");
        log.assertOutput(5, "true");
        log.assertOutput(6, "false");
        log.assertOutput(7, "true");
        log.assertOutput(8, "false");
    }
    
}
