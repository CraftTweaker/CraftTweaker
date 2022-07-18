package com.blamejared.crafttweaker.gametest.framework.zencode.helpers;

import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import org.openzen.zenscript.scriptingexample.tests.helpers.ZenCodeTestLoggerOutput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ZenCodeGameTestLoggerOutput extends ZenCodeTestLoggerOutput implements CraftTweakerGameTest {
    
    public void assertSize(int size) {
        
        assertThat(lines.size(), is(size));
    }
    
    public void assertLine(int line, String content) {
        
        assertThat(lines.get(line), is(content));
    }
    
    public void assertLineContains(int line, String content) {
        
        final String foundLine = lines.get(line);
        assertThat("Expected this line to contain %s but found %s!".formatted(content, foundLine), foundLine, containsStringIgnoringCase(content));
    }
    
}
