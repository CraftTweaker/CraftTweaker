package com.blamejared.crafttweaker.gametest.test.zencode.impl.util;

import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.zencode.helpers.ZenCodeGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zenscript.scriptingexample.tests.helpers.ScriptBuilder;

import java.util.ArrayList;
import java.util.List;

@CraftTweakerGameTestHolder
public class ZenCodePositionUtilTest extends ZenCodeGameTest {
    
    @Override
    public List<Class<?>> getRequiredClasses() {
        
        final ArrayList<Class<?>> classes = new ArrayList<>(super.getRequiredClasses());
        classes.add(ActionClass.class);
        return classes;
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void getPositionReturnsProperPosition(GameTestHelper helper) {
        
        setup();
        //Arrange
        final String fileName = "positionTestFile.zs";
        final ScriptBuilder scriptBuilder = ScriptBuilder.create()
                .startNewScript(fileName)
                .add("println(getCurrentFileFull());");
        
        //Act
        scriptBuilder.execute(this);
        
        //Assert
        logger.assertPrintOutputSize(1);
        
        final String expectedFileNameFull = String.format("%s:1:0", fileName);
        logger.assertPrintOutput(0, expectedFileNameFull);
        
        
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void getPositionReturnsFileNameOfTopMostFile(GameTestHelper helper) {
        
        setup();
        //Arrange
        final String fileNameCallee = "positionTestFileCallee.zs";
        final String fileNameCaller = "positionTestFileCaller.zs";
        final ScriptBuilder scriptBuilder = ScriptBuilder.create()
                .startNewScript(fileNameCallee)
                .add("public function doTheThing() as void {")
                .add("    println(getCurrentFileName());")
                .add("}")
                .startNewScript(fileNameCaller)
                .add("doTheThing();");
        
        //Act
        scriptBuilder.execute(this);
        
        //Assert
        logger.assertPrintOutputSize(1);
        logger.assertPrintOutput(0, fileNameCallee);
    }
    
    
    @ZenCodeType.Name(".ActionClass")
    public static final class ActionClass {
        
        @ZenCodeGlobals.Global
        public static String getCurrentFileName() {
            
            return PositionUtil.getZCScriptPositionFromStackTrace().getFilename();
        }
        
        @ZenCodeGlobals.Global
        public static String getCurrentFileFull() {
            
            return PositionUtil.getZCScriptPositionFromStackTrace().toString();
        }
        
    }
    
}
