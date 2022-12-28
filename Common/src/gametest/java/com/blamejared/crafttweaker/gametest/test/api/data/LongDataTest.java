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
public class LongDataTest implements CraftTweakerGameTest {
    
    private String named(String name) {
        
        return "data/long/%s.zs".formatted(name);
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
        
        assertThat(iData.asLong() + 1, is(3L));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testSHL(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/shl"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "2");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asLong() << 1, is(2L));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testSHR(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/shr"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asLong() >> 1, is(1L));
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
        
        assertThat(iData.asLong() - 1, is(1L));
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
        
        assertThat(iData.asLong() * 2, is(4L));
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
        
        assertThat(iData.asLong() / 2L, is(2L));
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
        
        assertThat(iData.asLong() % 2, is(1L));
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
        
        assertThat(-iData.asLong(), is(-2L));
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
        
        assertThat(iData.asLong(), is(8L));
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
    public void testAnd(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/and"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asLong() & 1, is(0L));
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
        
        assertThat(Long.compare(iData.asLong(), 3L) > 0, is(true));
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
        assertThat(first.asLong() == second.asLong(), is(true));
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
        assertThat(first.asLong() == second.asLong(), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testOr(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/or"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "3");
        IData first = GameTestGlobals.data().get(0);
        IData second = GameTestGlobals.data().get(1);
        
        assertThat(first.asLong() | second.asLong(), is(3L));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testXor(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/xor"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "3");
        IData first = GameTestGlobals.data().get(0);
        IData second = GameTestGlobals.data().get(1);
        
        assertThat(first.asLong() ^ second.asLong(), is(3L));
    }
    
}
