package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

class SnippingPreprocessorTest {
    
    public final SnippingPreprocessor preprocessorUnderTest = new SnippingPreprocessor();
    
    @BeforeEach
    void setUp() {
        CraftTweakerAPI.logger = new TestLogger();
        preprocessorUnderTest.addSnippingParameter(new SnippingParameterNoStart());
    }
    
    @Test
    void nameIsSnip() {
        Assertions.assertEquals("snip", preprocessorUnderTest.getName());
    }
    
    @Test
    void snippedContentIsNoLongerInFileSingleLine() {
        final FileAccessSingle file = getFile("#snip start HelloWorld #snip end");
        final List<String> fileContents = file.getFileContents();
        Assertions.assertEquals(1, fileContents.size(), "File content should remain one line");
        Assertions.assertEquals("                                ", fileContents.get(0));
    }
    
    @Test
    void snippedContentIsNoLongerInFileMultiLineEndingsExclusive() {
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#snip start");
        stringJoiner.add("Hello World");
        stringJoiner.add("#snip end");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        Assertions.assertEquals(3, fileContents.size(), "File must remain the same structure");
        Assertions.assertEquals("           ", fileContents.get(0));
        Assertions.assertEquals("           ", fileContents.get(1));
        Assertions.assertEquals("         ", fileContents.get(2));
    }
    
    @Test
    void onlySnipIfItShouldBeSnippedSingleLine() {
        final FileAccessSingle file = getFile("#snip nostart HelloWorld #snip end");
        final List<String> fileContents = file.getFileContents();
        Assertions.assertEquals(1, fileContents.size(), "File content should remain one line");
        Assertions.assertEquals("              HelloWorld          ", fileContents.get(0));
    }
    
    @Test
    void onlySnipIfItShouldBeSnippedMultiLineEndingsExclusive() {
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#snip nostart");
        stringJoiner.add("Hello World");
        stringJoiner.add("#snip end");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        Assertions.assertEquals(3, fileContents.size(), "File must remain the same structure");
        Assertions.assertEquals("             ", fileContents.get(0));
        Assertions.assertEquals("Hello World", fileContents.get(1));
        Assertions.assertEquals("         ", fileContents.get(2));
    }
    
    @Test
    void snipChildrenIfNotCompleteSnip() {
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#snip nostart");
        stringJoiner.add("Hello World");
        stringJoiner.add("#snip start");
        stringJoiner.add("Hello World");
        stringJoiner.add("#snip end");
        stringJoiner.add("#snip end");
    
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
    
        Assertions.assertEquals(6, fileContents.size(), "File must remain the same structure");
        Assertions.assertEquals("             ", fileContents.get(0));
        Assertions.assertEquals("Hello World", fileContents.get(1));
        Assertions.assertEquals("           ", fileContents.get(2));
        Assertions.assertEquals("           ", fileContents.get(3));
        Assertions.assertEquals("         ", fileContents.get(4));
        Assertions.assertEquals("         ", fileContents.get(5));
    }
    
    @Test
    void snipChildrenIfNotCompleteSnip_2() {
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#snip nostart");
        stringJoiner.add("#snip nostart");
        stringJoiner.add("var x = 13;");
        stringJoiner.add("#snip start");
        stringJoiner.add("var x = 14;");
        stringJoiner.add("#snip end");
        stringJoiner.add("#snip end");
        stringJoiner.add("#snip end");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        Assertions.assertEquals(8, fileContents.size(), "File must remain the same structure");
        Assertions.assertEquals("             ", fileContents.get(0));
        Assertions.assertEquals("             ", fileContents.get(1));
        Assertions.assertEquals("var x = 13;", fileContents.get(2));
        Assertions.assertEquals("           ", fileContents.get(3));
        Assertions.assertEquals("           ", fileContents.get(4));
        Assertions.assertEquals("         ", fileContents.get(5));
        Assertions.assertEquals("         ", fileContents.get(6));
        Assertions.assertEquals("         ", fileContents.get(7));
    }
    
    private FileAccessSingle getFile(String content) {
        return new FileAccessSingle("test.zs", new StringReader(content), new ScriptLoadingOptions()
                .execute()
                .setLoaderName("crafttweaker"), Collections.singletonList(preprocessorUnderTest));
    }
}