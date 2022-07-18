package com.blamejared.crafttweaker.gametest.framework.zencode.helpers;

import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import org.openzen.zenscript.scriptingexample.tests.helpers.ZenCodeTestLogger;

public class ZenCodeGameTestLogger extends ZenCodeTestLogger implements CraftTweakerGameTest {
    
    public ZenCodeGameTestLogger() {
        
        super(new ZenCodeGameTestLoggerOutput(), new ZenCodeGameTestLoggerOutput(), new ZenCodeGameTestLoggerOutput());
    }
    
    public void assertPrintOutput(int line, String content) {
        
        if(!isEngineComplete()) {
            fail("Trying to call an assertion before the engine ran, probably a fault in the test!");
        }
        printlnOutputs().assertLine(line, content);
    }
    
    public void assertPrintOutputSize(int size) {
        
        if(!isEngineComplete()) {
            fail("Trying to call an assertion before the engine ran, probably a fault in the test!");
        }
        printlnOutputs().assertSize(size);
    }
    
}
