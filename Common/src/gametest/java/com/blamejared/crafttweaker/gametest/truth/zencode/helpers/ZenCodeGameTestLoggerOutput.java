package com.blamejared.crafttweaker.gametest.truth.zencode.helpers;

import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import org.openzen.zenscript.scriptingexample.tests.helpers.ZenCodeTestLoggerOutput;

public class ZenCodeGameTestLoggerOutput extends ZenCodeTestLoggerOutput implements CraftTweakerGameTest {
    
    public void assertSize(int size) {
        
        assertThat(lines.size()).isEqualTo(size);
    }
    
    public void assertLine(int line, String content) {
        
        assertThat(lines.get(line)).isEqualTo(content);
    }
    
    public void assertLineContains(int line, String content) {
        
        final String foundLine = lines.get(line);
        assertWithMessage("Expected this line to contain {0} but found {1}!", content, foundLine)
                .that(foundLine)
                .contains(content);
    }
    
}
