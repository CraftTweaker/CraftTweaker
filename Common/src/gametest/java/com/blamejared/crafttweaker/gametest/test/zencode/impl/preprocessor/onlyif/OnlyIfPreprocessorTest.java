package com.blamejared.crafttweaker.gametest.test.zencode.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.EndIfPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfPreprocessor;
import com.blamejared.crafttweaker.impl.script.scriptrun.GameTestScriptRunner;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.IOUtils;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@CraftTweakerGameTestHolder
public class OnlyIfPreprocessorTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nameIsOnlyIf(GameTestHelper helper) {
        
        assertThat(OnlyIfPreprocessor.INSTANCE.name()).isEqualTo("onlyif");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removedContentIsNoLongerInFileSingleLine(GameTestHelper helper) {
        
        final IScriptFile file = getFile("#onlyif false HelloWorld #endif");
        final List<String> fileContents = file.preprocessedContents();
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
        
        final IScriptFile file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.preprocessedContents();
        
        assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(3);
        assertThat(fileContents.get(0)).isEqualTo("             ");
        assertThat(fileContents.get(1)).isEqualTo("           ");
        assertThat(fileContents.get(2)).isEqualTo("      ");
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void onlyRemoveIfItShouldBeRemovedSingleLine(GameTestHelper helper) {
        
        final IScriptFile file = getFile("#onlyif true HelloWorld #endif");
        final List<String> fileContents = file.preprocessedContents();
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
        
        final IScriptFile file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.preprocessedContents();
        
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
        
        final IScriptFile file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.preprocessedContents();
        
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
        
        final IScriptFile file = getFile(stringJoiner.toString());
        final List<String> fileContents = file.preprocessedContents();
        
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
    
    private IScriptFile getFile(String contents) {
        
        return GameTestScriptRunner.getFile(contents, List.of(OnlyIfPreprocessor.INSTANCE, EndIfPreprocessor.INSTANCE));
    }
    
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void modloadedRemovesCorrectly(GameTestHelper helper) {
        
        final StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("#onlyif modloaded invalid-modid");
        stringJoiner.add("Hello World");
        stringJoiner.add("#endif");
        
        final IScriptFile file = getFile(stringJoiner.toString());
        Optional<SourceFile> sourceFile = file.toSourceFile();
        assertThat(sourceFile.isPresent()).isTrue();
        try {
            List<String> fileContents = IOUtils.readLines(sourceFile.get().open());
    
            assertWithMessage("File must remain the same structure").that(fileContents.size()).isEqualTo(3);
            assertThat(fileContents.get(0)).isEqualTo("                               ");
            assertThat(fileContents.get(1)).isEqualTo("           ");
            assertThat(fileContents.get(2)).isEqualTo("      ");
        }catch(IOException e){
            throw new GameTestAssertException(e.getMessage());
        }
    }
    
}