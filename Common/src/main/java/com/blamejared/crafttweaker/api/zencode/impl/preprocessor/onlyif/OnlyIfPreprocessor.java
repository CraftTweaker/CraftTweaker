package com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterFalse;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoaded;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoader;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterModNotLoaded;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterSide;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter.OnlyIfParameterTrue;
import org.openzen.zencode.shared.CodePosition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Preprocessor
public final class OnlyIfPreprocessor implements IPreprocessor {
    
    public static final String NAME = "onlyif";
    private final Map<String, OnlyIfParameter> knownParameters = new HashMap<>();
    private OnlyIfMatch currentMatch;
    
    public OnlyIfPreprocessor() {
        //Replace this with something from the CrT registry at some point?
        //That would allow other mods to add their own parameters as well :thinking:
        addParameter(new OnlyIfParameterTrue());
        addParameter(new OnlyIfParameterFalse());
        addParameter(new OnlyIfParameterModLoaded());
        addParameter(new OnlyIfParameterModNotLoaded());
        addParameter(new OnlyIfParameterSide());
        addParameter(new OnlyIfParameterModLoader());
    }
    
    public void addParameter(OnlyIfParameter parameter) {
        
        knownParameters.put(parameter.getName().toLowerCase(), parameter);
    }
    
    @Override
    public String getName() {
        
        return NAME;
    }
    
    @Override
    public String getMatchEnder() {
        
        return "endif";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        final List<OnlyIfMatch> matches = new ArrayList<>();
        readMatches(file, preprocessorMatches, matches);
        removeIt(file, matches);
        
        return true;
    }
    
    private void removeIt(FileAccessSingle file, List<OnlyIfMatch> matches) {
        
        for(OnlyIfMatch match : matches) {
            match.remove(file);
        }
    }
    
    private void readMatches(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches, List<OnlyIfMatch> matches) {
        
        this.currentMatch = null;
        List<PreprocessorMatch> allMatches = file.getMatches().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(match -> match.getLine() != -1)
                .filter(match -> match.getPreprocessor()
                        .getName()
                        .equalsIgnoreCase(this.getName()) || match.getPreprocessor()
                        .getName()
                        .equalsIgnoreCase(this.getMatchEnder()))
                .sorted(Comparator.comparingInt(PreprocessorMatch::getLine))
                .toList();
        for(PreprocessorMatch preprocessorMatch : allMatches) {
            getOnlyIfMatch(file, matches, preprocessorMatch);
        }
        
        if(currentMatch != null) {
            final CodePosition start = currentMatch.getStart();
            final String name = currentMatch.getName();
            CraftTweakerAPI.LOGGER.warn("{} onlyif '{}' starting at line {}:{} was not closed properly", file, name, start.fromLine, start.fromLineOffset);
        }
    }
    
    private void getOnlyIfMatch(@Nonnull FileAccessSingle file, List<OnlyIfMatch> matches, PreprocessorMatch preprocessorMatch) {
        
        final int line = preprocessorMatch.getLine();
        final String fileName = file.getFileName();
        
        //The rest of the line is considered possible arguments
        final String[] content = preprocessorMatch.getContent().split(" ");
        //If none were given that means only '#onlyif' was called, ignore for now.
        if(content.length < 1) {
            CraftTweakerAPI.LOGGER.warn("{}:{} Using 'onlyif' requires a parameter, like start, end, modloaded, etc", fileName, line);
            return;
        }
        
        //The first argument is the name
        final String parameterName = content[0];
        
        if(preprocessorMatch.getPreprocessor().getName().equalsIgnoreCase(getMatchEnder())) {
            if(currentMatch != null) {
                currentMatch.setEnd(getPosition(file, line, new String[] {""}, 1, true));
                matches.add(currentMatch);
                currentMatch = currentMatch.getParent();
            } else {
                CraftTweakerAPI.LOGGER.warn("{}:{} Called 'onlyif end' without prior start", fileName, line);
            }
            return;
        }
        
        if(!knownParameters.containsKey(parameterName.toLowerCase())) {
            CraftTweakerAPI.LOGGER.warn("{}:{} Unknown 'onlyif' parameter: '{}'", fileName, line, parameterName);
            return;
        }
        
        //The other arguments are arguments for the OnlyIfParameter
        final String[] additionalArguments = Arrays.copyOfRange(content, 1, content.length);
        final OnlyIfParameter parameter = this.knownParameters.get(parameterName.toLowerCase());
        final OnlyIfParameterHit hit = parameter.isHit(additionalArguments);
        
        //Invalid arguments
        if(!hit.validArguments) {
            final String array = Arrays.toString(additionalArguments);
            CraftTweakerAPI.LOGGER.warn("{}:{} Invalid 'onlyif' arguments for parameter '{}': {}'", fileName, line, parameterName, array);
            return;
        }
        
        final CodePosition startPosition = getPosition(file, line, content, hit.numberOfConsumedArguments + 1, false);
        currentMatch = new OnlyIfMatch(startPosition, parameterName, currentMatch, hit);
        
        checkAdditionalOnlyIfsOnSameLine(content, file, line, matches);
    }
    
    private void checkAdditionalOnlyIfsOnSameLine(String[] content, FileAccessSingle file, int line, List<OnlyIfMatch> matches) {
        
        final List<String> strings = Arrays.asList(content);
        int indexOfStart = strings.indexOf("#" + getName());
        int indexOfEnd = strings.indexOf("#" + getMatchEnder());
        if(indexOfStart > 0 || indexOfEnd > 0) {
            
            boolean foundStart = (Math.min(indexOfStart, indexOfEnd) == indexOfStart) != (indexOfStart == -1);
            String newContent;
            
            if(foundStart) {
                newContent = String.join(" ", strings.subList(indexOfStart + 1, content.length));
                getOnlyIfMatch(file, matches, new PreprocessorMatch(this, line, newContent));
            } else {
                // Substring 1 to get rid of the # of #endif
                newContent = String.join(" ", strings.subList(indexOfEnd, content.length)).substring(1);
                getOnlyIfMatch(file, matches, new PreprocessorMatch(new EndIfPreprocessor(), line, newContent));
            }

            
        }
    }
    
    @Override
    public int getPriority() {
        
        return 20;
    }
    
    private CodePosition getPosition(FileAccessSingle file, int line, String[] preprocessorContent, int contentPosition, boolean isEnd) {
        //The length of '#onlyif ' since we want to remove that as well ^^
        final int prefixLength = isEnd ? getMatchEnder().length() + 1 : NAME.length() + 2;
        
        
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
