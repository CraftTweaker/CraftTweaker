package com.blamejared.crafttweaker.gametest.test.zencode.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.EndIfPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfPreprocessor;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import java.io.StringReader;
import java.util.List;
import java.util.StringJoiner;

@CraftTweakerGameTestHolder
public class OnlyIfPreprocessorTest implements CraftTweakerGameTest {
    
    public final OnlyIfPreprocessor preprocessorUnderTest = new OnlyIfPreprocessor();
    public final EndIfPreprocessor preprocessorUnderTestEnder = new EndIfPreprocessor();
    
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nameIsOnlyIf(GameTestHelper helper) {
        
        assertThat(preprocessorUnderTest.getName()).isEqualTo("onlyif");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removedContentIsNoLongerInFileSingleLine(GameTestHelper helper) {
        
        final FileAccessSingle file = getFile("#onlyif false HelloWorld #endif");
        final List<String> fileContents = file.getFileContents();
        assertWithMessage("File contents should remain one line").that(fileContents.size()).isEqualTo(1);
        assertThat(fileContents.get(0)).isEqualTo("                               ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removedContentIsNoLongerInFileMultiLineEndingsExclusive(GameTestHelper helper) {
        
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#onlyif false");
        stringJoiner.add("Hello World");
        stringJoiner.add("#endif");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(3);
        assertThat(fileContents.get(0)).isEqualTo("             ");
        assertThat(fileContents.get(1)).isEqualTo("           ");
        assertThat(fileContents.get(2)).isEqualTo("      ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void onlyRemoveIfItShouldBeRemovedSingleLine(GameTestHelper helper) {
        
        final FileAccessSingle file = getFile("#onlyif true HelloWorld #endif");
        final List<String> fileContents = file.getFileContents();
        assertWithMessage("File content should remain one line").that(fileContents.size()).isEqualTo(1);
        assertThat(fileContents.get(0)).isEqualTo("             HelloWorld       ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void onlyRemoveIfItShouldBeRemovedMultiLineEndingsExclusive(GameTestHelper helper) {
        
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#onlyif true");
        stringJoiner.add("Hello World");
        stringJoiner.add("#endif");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(3);
        assertThat(fileContents.get(0)).isEqualTo("            ");
        assertThat(fileContents.get(1)).isEqualTo("Hello World");
        assertThat(fileContents.get(2)).isEqualTo("      ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removeChildrenIfNotCompleteRemove(GameTestHelper helper) {
        
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#onlyif true");
        stringJoiner.add("Hello World");
        stringJoiner.add("#onlyif false");
        stringJoiner.add("Hello World");
        stringJoiner.add("#endif");
        stringJoiner.add("#endif");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(6);
        assertThat(fileContents.get(0)).isEqualTo("            ");
        assertThat(fileContents.get(1)).isEqualTo("Hello World");
        assertThat(fileContents.get(2)).isEqualTo("             ");
        assertThat(fileContents.get(3)).isEqualTo("           ");
        assertThat(fileContents.get(4)).isEqualTo("      ");
        assertThat(fileContents.get(5)).isEqualTo("      ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removeChildrenIfNotCompleteRemove_2(GameTestHelper helper) {
        
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#onlyif true");
        stringJoiner.add("#onlyif true");
        stringJoiner.add("var x = 13;");
        stringJoiner.add("#onlyif false");
        stringJoiner.add("var x = 14;");
        stringJoiner.add("#endif");
        stringJoiner.add("#endif");
        stringJoiner.add("#endif");
        
        final FileAccessSingle file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.getFileContents();
        
        assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(8);
        assertWithMessage("Line 0").that(fileContents.get(0)).isEqualTo("            ");
        assertWithMessage("Line 1").that(fileContents.get(1)).isEqualTo("            ");
        assertWithMessage("Line 2").that(fileContents.get(2)).isEqualTo("var x = 13;");
        assertWithMessage("Line 3").that(fileContents.get(3)).isEqualTo("             ");
        assertWithMessage("Line 4").that(fileContents.get(4)).isEqualTo("           ");
        assertWithMessage("Line 5").that(fileContents.get(5)).isEqualTo("      ");
        assertWithMessage("Line 6").that(fileContents.get(6)).isEqualTo("      ");
        assertWithMessage("Line 7").that(fileContents.get(7)).isEqualTo("      ");
    }
    
    private FileAccessSingle getFile(String content) {
        
        return new FileAccessSingle("test.zs", new StringReader(content), new ScriptLoadingOptions()
                .execute()
                .setLoaderName("crafttweaker"), List.of(preprocessorUnderTest, preprocessorUnderTestEnder));
    }
    
}