package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.zencode.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.parameters.*;
import org.openzen.zencode.shared.*;

import javax.annotation.*;
import java.util.*;

@Preprocessor
public final class SnippingPreprocessor implements IPreprocessor {
    
    public static final String NAME = "snip";
    private final Map<String, SnippingParameter> knownParameters = new HashMap<>();
    private SnippingMatch currentMatch;
    
    public SnippingPreprocessor() {
        //Replace this with something from the CrT registry at some point?
        //That would allow other mods to add their own parameters as well :thinking:
        addSnippingParameter(new SnippingParameterStart());
        addSnippingParameter(new SnippingParameterModLoaded());
        addSnippingParameter(new SnippingParameterModNotLoaded());
    }
    
    public void addSnippingParameter(SnippingParameter parameter) {
        knownParameters.put(parameter.getName().toLowerCase(), parameter);
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        final List<SnippingMatch> matches = new ArrayList<>();
        readSnippingMatches(file, preprocessorMatches, matches);
        snipIt(file, matches);
        
        return true;
    }
    
    private void snipIt(FileAccessSingle file, List<SnippingMatch> matches) {
        for(SnippingMatch match : matches) {
            match.snip(file);
        }
        
    }
    
    private void readSnippingMatches(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches, List<SnippingMatch> matches) {
        this.currentMatch = null;
        for(PreprocessorMatch preprocessorMatch : preprocessorMatches) {
            getSnippingMatch(file, matches, preprocessorMatch);
        }
        
        if(currentMatch != null) {
            final CodePosition start = currentMatch.getStart();
            final String name = currentMatch.getName();
            CraftTweakerAPI.logWarning("%s Snip '%s' starting at line %d:%d was not closed properly", file, name, start.fromLine, start.fromLineOffset);
        }
    }
    
    private void getSnippingMatch(@Nonnull FileAccessSingle file, List<SnippingMatch> matches, PreprocessorMatch preprocessorMatch) {
        final int line = preprocessorMatch.getLine();
        final String fileName = file.getFileName();
        
        //The rest of the line is considered possible arguments
        final String[] content = preprocessorMatch.getContent().split(" ");
        //If none were given that means only '#snip' was called, ignore for now.
        if(content.length < 1) {
            CraftTweakerAPI.logWarning("%s:%d Using 'snip' requires a parameter, like start, end, modloaded, etc", fileName, line);
            return;
        }
        
        //The first argument is the name
        final String snippingParameterName = content[0];
        
        //End is not a parameter because it's special
        if(snippingParameterName.toLowerCase().equals("end")) {
            if(currentMatch != null) {
                currentMatch.setEnd(getPosition(file, line, content, 1));
                matches.add(currentMatch);
                currentMatch = currentMatch.getParent();
            } else {
                CraftTweakerAPI.logWarning("%s:%d Called 'snip end' without prior start", fileName, line);
            }
            return;
        }
        
        if(!knownParameters.containsKey(snippingParameterName.toLowerCase())) {
            CraftTweakerAPI.logWarning("%s:%d Unknown 'snip' parameter: '%s'", fileName, line, snippingParameterName);
            return;
        }
        
        //The other arguments are arguments for the SnippingParameter
        final String[] additionalArguments = Arrays.copyOfRange(content, 1, content.length);
        final SnippingParameter snippingParameter = this.knownParameters.get(snippingParameterName.toLowerCase());
        final SnippingParameterHit hit = snippingParameter.isHit(additionalArguments);
        
        //Invalid arguments
        if(!hit.validArguments) {
            final String array = Arrays.toString(additionalArguments);
            CraftTweakerAPI.logWarning("%s:%d Invalid 'snip' arguments for parameter '%s': %s'", fileName, line, snippingParameterName, array);
            return;
        }
        
        final CodePosition startPosition = getPosition(file, line, content, hit.numberOfConsumedArguments + 1);
        currentMatch = new SnippingMatch(startPosition, snippingParameterName, currentMatch, hit);
        
        checkAdditionalSnipsOnSameLine(content, file, line, matches);
    }
    
    private void checkAdditionalSnipsOnSameLine(String[] content, FileAccessSingle file, int line, List<SnippingMatch> matches) {
        final List<String> strings = Arrays.asList(content);
        final int indexOf = strings.indexOf("#" + getName());
        if(indexOf > 0) {
            String newContent = String.join(" ", strings.subList(indexOf + 1, content.length));
            getSnippingMatch(file, matches, new PreprocessorMatch(this, line, newContent));
        }
    }
    
    @Override
    public int getPriority() {
        return 20;
    }
    
    private CodePosition getPosition(FileAccessSingle file, int line, String[] preprocessorContent, int contentPosition) {
        //The length of '#snip ' since we want to remove that as well ^^
        final int prefixLength = NAME.length() + 2;
        
        final int startColumn = file.getFileContents()
                .get(line - 1)
                .lastIndexOf(String.join(" ", preprocessorContent));
        
        int endColumn = startColumn + contentPosition - 1;
        for(int i = 0; i < contentPosition; i++) {
            endColumn += preprocessorContent[i].length();
        }
        
        return new CodePosition(file.getSourceFile(), line, startColumn - prefixLength, line, endColumn);
    }
}
