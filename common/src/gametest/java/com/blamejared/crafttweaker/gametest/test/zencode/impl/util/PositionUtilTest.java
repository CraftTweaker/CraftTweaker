package com.blamejared.crafttweaker.gametest.test.zencode.impl.util;

import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import org.openzen.zencode.shared.CodePosition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

@CraftTweakerGameTestHolder
public class PositionUtilTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void getZCScriptPositionFromStackTraceReturnsUnknownForStackTraceFromJava(GameTestHelper helper) {
        
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        
        final CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace(stackTrace);
        
        assertThat(CodePosition.UNKNOWN, is(sameInstance(position)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void getZCScriptPositionFromStackTraceReturnsPositionForStackTraceFromZC(GameTestHelper helper) {
        
        final int lineNumber = 3;
        final String fileName = "test.zs";
        final String methodName = "methodName";
        final String declaringClass = "DeclaringClass";
        
        final StackTraceElement element = new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
        final StackTraceElement[] stackTrace = new StackTraceElement[] {element};
        
        final CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace(stackTrace);
        
        assertThat(CodePosition.UNKNOWN, is(not(sameInstance(position))));
        assertThat(lineNumber, is(position.fromLine));
        assertThat(lineNumber, is(position.toLine));
        assertThat(fileName, is(position.getFilename()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void getZCScriptPositionFromStackTraceReturnsFirstPositionForStackTraceFromZC(GameTestHelper helper) {
        
        final int lineNumber = 3;
        final String firstFileName = "test.zs";
        final String secondFileName = "other_file.zs";
        final String methodName = "methodName";
        final String declaringClass = "DeclaringClass";
        
        final StackTraceElement firstElement = new StackTraceElement(declaringClass, methodName, firstFileName, lineNumber);
        final StackTraceElement secondElement = new StackTraceElement(declaringClass, methodName, secondFileName, lineNumber);
        final StackTraceElement[] stackTrace = new StackTraceElement[] {firstElement, secondElement};
        
        final CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace(stackTrace);
        
        assertThat(CodePosition.UNKNOWN, is(not(sameInstance(position))));
        assertThat(firstFileName, is(position.getFilename()));
        assertThat(secondFileName, is(not(position.getFilename())));
    }
    
}