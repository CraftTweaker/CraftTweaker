package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.BoolData;
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
public class BoolDataTest implements CraftTweakerGameTest {
    
    private String named(String name) {
        
        return "data/bool/%s.zs".formatted(name);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testConstructor(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("constructor"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData, is(BoolData.TRUE));
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
        
        assertThat(iData.asBool() == true, is(true));
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
        
        assertThat(iData.asByte() == 1, is(true));
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
        
        assertThat(iData.asDouble() == 1.0, is(true));
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
        
        assertThat(iData.asFloat() == 1.0f, is(true));
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
        
        assertThat(iData.asInt() == 1, is(true));
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
        
        assertThat(iData.asLong() == 1L, is(true));
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
        
        assertThat(iData.asShort() == 1, is(true));
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
        
        assertThat(iData.and(BoolData.FALSE).asBool() == false, is(true));
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
        
        assertThat(iData.compareTo(BoolData.FALSE) == 1, is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testContains(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/contains"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.contains(BoolData.TRUE), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testEquals(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/equals"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.equalTo(BoolData.TRUE), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testNot(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/not"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "false");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.not().asBool(), is(false));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testOr(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/or"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.or(BoolData.FALSE).asBool(), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testXor(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/xor"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "false");
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.xor(BoolData.TRUE).asBool(), is(false));
    }
    
}
