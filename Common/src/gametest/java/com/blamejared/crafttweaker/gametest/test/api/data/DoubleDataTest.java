package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.ScriptBuilder;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.zencode.GameTestGlobals;
import com.blamejared.crafttweaker.gametest.logging.appender.GameTestLoggerAppender;
import com.blamejared.crafttweaker.impl.script.scriptrun.GameTestScriptRunner;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class DoubleDataTest implements CraftTweakerGameTest {
    
    public String scriptPath() {
        
        return "data/double";
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testAdd(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/add"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble() + 1, is(3.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testSub(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/sub"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble() - 1, is(1.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testMul(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/mul"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble() * 2, is(4.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testDiv(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/div"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble() / 2, is(2.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testMod(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/mod"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble() % 2, is(1.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testNeg(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/neg"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(-iData.asDouble(), is(-2.0));
    }
    
    //    @GameTest(template = "crafttweaker:empty")
    //    @TestModifier(implicitSuccession = true)
    //    public void testInvert(GameTestHelper helper, ScriptBuilder builder) {
    //
    //        builder.file(named("operator/invert"));
    //
    //        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
    //        log.assertNoErrors();
    //        log.assertNoWarnings();
    //        log.assertOutput(0, "true");
    //        IData iData = GameTestGlobals.data().get(0);
    //
    //        assertThat(~iData.asInt(), is(-3));
    //    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testConstructor(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("constructor"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble(), is(8.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToBool(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/bool"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asBool(), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToByte(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/byte"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asByte(), is((byte) 1));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToDouble(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/double"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asDouble(), is(1.0));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToFloat(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/float"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asFloat(), is(1.0f));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToInt(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/int"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asInt(), is(1));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToLong(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/long"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asLong(), is(1L));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToShort(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/short"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asShort(), is((short) 1));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCompare(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/compare"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(Double.compare(iData.asDouble(), 3.0) > 0, is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testContains(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/contains"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        
        IData first = GameTestGlobals.data().get(0);
        IData second = GameTestGlobals.data().get(1);
        assertThat(first.asDouble() == second.asDouble(), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testEquals(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/equals"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        
        IData first = GameTestGlobals.data().get(0);
        IData second = GameTestGlobals.data().get(1);
        assertThat(first.asDouble() == second.asDouble(), is(true));
    }
    
}
