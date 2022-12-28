package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class ByteArrayDataTest implements CraftTweakerGameTest {
    
    private String named(String name) {
        
        return "data/byte_array/%s.zs".formatted(name);
    }
    
    //    @GameTest(template = "crafttweaker:empty")
    //    @TestModifier(implicitSuccession = true)
    //    public void testToByteArray(GameTestHelper helper, ScriptBuilder builder) {
    //        //TODO add byte array tests when you can get a byte array instead of an sbyte array
    //        //
    //        //        builder.file("data/byte_array/caster/byte_array.zs");
    //        //
    //        //        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
    //        //        log.assertNoErrors();
    //        //        log.assertNoWarnings();
    //        //        IData iData = GameTestGlobals.data().get(0);
    //        //
    //        //        assertThat(iData.asBool() == true, is(true));
    //    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testConstructor(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("constructor"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData, is(new ByteArrayData(new byte[] {1, 2})));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToIntArray(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/int_array"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        log.assertOutput(1, "2");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(Arrays.equals(iData.asIntArray(), new int[] {1, 2}), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToList(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/list"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        log.assertOutput(1, "2");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asList(), is(List.of(new ByteData((byte) 1), new ByteData((byte) 2))));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testToLongArray(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("caster/long_array"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        log.assertOutput(1, "2");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(Arrays.equals(iData.asLongArray(), new long[] {1L, 2L}), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCompare(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/compare"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "true");
        
        IData first = GameTestGlobals.data().get(0);
        IData second = GameTestGlobals.data().get(1);
        
        assertThat(Arrays.compare(first.asByteArray(), second.asByteArray()), is(-2));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testIndexSet(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/indexSet"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "3");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asByteArray()[0], is((byte) 3));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testIndexGet(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("operator/indexGet"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asByteArray()[0], is((byte) 1));
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
        boolean contains = false;
        for(byte b : iData.asByteArray()) {
            if(b == 1) {
                contains = true;
            }
        }
        assertThat(contains, is(true));
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
        assertThat(Arrays.equals(iData.asByteArray(), new byte[] {1, 2}), is(true));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testLength(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("length"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "2");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.length(), is(2));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testEmpty(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("empty"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "false");
        
        IData iData = GameTestGlobals.data().get(0);
        
        assertThat(iData.asByteArray().length == 0, is(false));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testIterator(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("iterator"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "1");
        log.assertOutput(1, "2");
        
        IData iData = GameTestGlobals.data().get(0);
        Iterator<IData> iterator = iData.iterator();
        assertThat(iterator.next().asByte(), is((byte) 1));
        assertThat(iterator.next().asByte(), is((byte) 2));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testRemove(GameTestHelper helper, ScriptBuilder builder) {
        
        builder.file(named("remove"));
        
        GameTestLoggerAppender.QueryableLog log = GameTestScriptRunner.runScripts(helper, builder);
        log.assertNoErrors();
        log.assertNoWarnings();
        log.assertOutput(0, "false");
    }
    
}
