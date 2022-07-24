package com.blamejared.crafttweaker.gametest.test.zencode.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

@CraftTweakerGameTestHolder
public class OnlyIfPreprocessorTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nameIsOnlyIf(GameTestHelper helper) {
        
        assertThat(OnlyIfPreprocessor.INSTANCE.name(), is("onlyif"));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void removedContentIsNoLongerInFileSingleLine(GameTestHelper helper) {
        
        final IScriptFile file = getFile("#onlyif false HelloWorld #endif");
        final List<String> fileContents = file.preprocessedContents();
        assertThat("File contents should remain one line", fileContents.size(), is(1));
        assertThat(fileContents.get(0), is("                               "));
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
        
        assertThat("File must remain the same structure", fileContents.size(), is(3));
        assertThat(fileContents.get(0), is("             "));
        assertThat(fileContents.get(1), is("           "));
        assertThat(fileContents.get(2), is("      "));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void onlyRemoveIfItShouldBeRemovedSingleLine(GameTestHelper helper) {
        
        final IScriptFile file = getFile("#onlyif true HelloWorld #endif");
        final List<String> fileContents = file.preprocessedContents();
        assertThat("File content should remain one line", fileContents.size(), is(1));
        assertThat(fileContents.get(0), is("             HelloWorld       "));
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
        assertThat("File must remain the same structure", fileContents.size(), is(3));
        assertThat(fileContents.get(0), is("            "));
        assertThat(fileContents.get(1), is("Hello World"));
        assertThat(fileContents.get(2), is("      "));
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
        
        assertThat("File must remain the same structure", fileContents.size(), is(6));
        assertThat(fileContents.get(0), is("            "));
        assertThat(fileContents.get(1), is("Hello World"));
        assertThat(fileContents.get(2), is("             "));
        assertThat(fileContents.get(3), is("           "));
        assertThat(fileContents.get(4), is("      "));
        assertThat(fileContents.get(5), is("      "));
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
        
        assertThat("File must remain the same structure", fileContents.size(), is(8));
        assertThat("Line 0", fileContents.get(0), is("            "));
        assertThat("Line 1", fileContents.get(1), is("            "));
        assertThat("Line 2", fileContents.get(2), is("var x = 13;"));
        assertThat("Line 3", fileContents.get(3), is("             "));
        assertThat("Line 4", fileContents.get(4), is("           "));
        assertThat("Line 5", fileContents.get(5), is("      "));
        assertThat("Line 6", fileContents.get(6), is("      "));
        assertThat("Line 7", fileContents.get(7), is("      "));
    }
    
    private IScriptFile getFile(String contents) {
        
        return GameTestScriptRunner.getFile("test.zs", contents, List.of(OnlyIfPreprocessor.INSTANCE, EndIfPreprocessor.INSTANCE));
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
        assertThat(sourceFile.isPresent(), is(true));
        try {
            List<String> fileContents = IOUtils.readLines(sourceFile.get().open());
    
            assertThat("File must remain the same structure", fileContents.size(), is(3));
            assertThat(fileContents.get(0), is("                               "));
            assertThat(fileContents.get(1), is("           "));
            assertThat(fileContents.get(2), is("      "));
        }catch(IOException e){
            throw new GameTestAssertException(e.getMessage());
        }
    }
    
}