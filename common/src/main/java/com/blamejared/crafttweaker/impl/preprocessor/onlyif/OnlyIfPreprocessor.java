package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterFalse;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoaded;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModLoader;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterModNotLoaded;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterSide;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter.OnlyIfParameterTrue;
import org.openzen.zencode.shared.CodePosition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@ZenRegister
@Preprocessor
public final class OnlyIfPreprocessor implements IPreprocessor {
    
    public static final OnlyIfPreprocessor INSTANCE = new OnlyIfPreprocessor();
    public static final String NAME = "onlyif";
    
    private static final String SPACE = Pattern.quote(" ");
    
    private final Map<String, OnlyIfParameter> knownParameters = new HashMap<>();
    private OnlyIfMatch currentMatch;
    
    private OnlyIfPreprocessor() {
        //Replace this with something from the CrT registry at some point?
        //That would allow other mods to add their own parameters as well :thinking:
        this.addParameter(new OnlyIfParameterTrue());
        this.addParameter(new OnlyIfParameterFalse());
        this.addParameter(new OnlyIfParameterModLoaded());
        this.addParameter(new OnlyIfParameterModNotLoaded());
        this.addParameter(new OnlyIfParameterSide());
        this.addParameter(new OnlyIfParameterModLoader());
    }
    
    public void addParameter(final OnlyIfParameter parameter) {
        
        this.knownParameters.put(parameter.name().toLowerCase(), parameter);
    }
    
    @Override
    public String name() {
        
        return NAME;
    }
    
    @Override
    public String preprocessorEndMarker() {
        
        return "endif";
    }
    
    @Nullable
    @Override
    public String defaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        final List<OnlyIfMatch> onlyIfMatches = new ArrayList<>();
        this.readMatches(file, preprocessedContents, matches, onlyIfMatches);
        this.removeData(onlyIfMatches, preprocessedContents);
        
        return true;
    }
    
    @Override
    public int priority() {
        
        return 20;
    }
    
    private void readMatches(final IScriptFile file, final List<String> contents, final List<Match> preprocessorMatches, final List<OnlyIfMatch> matches) {
        
        this.currentMatch = null;
        
        Stream.concat(preprocessorMatches.stream(), file.matchesFor(EndIfPreprocessor.INSTANCE).stream())
                .filter(it -> it.line() != -1)
                .sorted(Comparator.comparingInt(Match::line))
                .forEach(it -> this.getOnlyIfMatch(file, contents, matches, it));
        
        if(this.currentMatch != null) {
            final CodePosition start = this.currentMatch.start();
            final String name = this.currentMatch.name();
            PREPROCESSOR_LOGGER.warn("{} onlyif '{}' starting at line {}:{} was not closed properly", file, name, start.fromLine, start.fromLineOffset);
        }
    }
    
    private void getOnlyIfMatch(final IScriptFile file, final List<String> contents, final List<OnlyIfMatch> matches, final Match preprocessorMatch) {
        
        final int line = preprocessorMatch.line();
        final String fileName = file.name();
        
        //The rest of the line is considered possible arguments
        final String[] content = preprocessorMatch.content().split(SPACE);
        //If none were given that means only '#onlyif' was called, ignore for now.
        if(content.length < 1) {
            PREPROCESSOR_LOGGER.warn("{}:{} Using 'onlyif' requires a parameter, like start, end, modloaded, etc", fileName, line);
            return;
        }
        
        //The first argument is the name
        final String parameterName = content[0];
        
        if(preprocessorMatch.preprocessor().name().equalsIgnoreCase(this.preprocessorEndMarker())) {
            
            if(this.currentMatch != null) {
                
                this.currentMatch.end(this.getPosition(file, contents, line, new String[] {""}, 1, true));
                matches.add(this.currentMatch);
                this.currentMatch = this.currentMatch.parent();
            } else {
                
                PREPROCESSOR_LOGGER.warn("{}:{} Called 'onlyif end' without prior start", fileName, line);
            }
            
            return;
        }
        
        if(!this.knownParameters.containsKey(parameterName.toLowerCase())) {
            PREPROCESSOR_LOGGER.warn("{}:{} Unknown 'onlyif' parameter: '{}'", fileName, line, parameterName);
            return;
        }
        
        //The other arguments are arguments for the OnlyIfParameter
        final String[] additionalArguments = Arrays.copyOfRange(content, 1, content.length);
        final OnlyIfParameter parameter = this.knownParameters.get(parameterName.toLowerCase());
        final OnlyIfParameterHit hit = parameter.isHit(additionalArguments);
        
        //Invalid arguments
        if(!hit.validArguments()) {
            final String array = Arrays.toString(additionalArguments);
            PREPROCESSOR_LOGGER.warn("{}:{} Invalid 'onlyif' arguments for parameter '{}': {}'", fileName, line, parameterName, array);
            return;
        }
        
        final CodePosition startPosition = this.getPosition(file, contents, line, content, hit.numberOfConsumedArguments() + 1, false);
        this.currentMatch = new OnlyIfMatch(startPosition, parameterName, this.currentMatch, hit);
        
        this.checkAdditionalOnlyIfsOnSameLine(contents, content, file, line, matches);
    }
    
    private void checkAdditionalOnlyIfsOnSameLine(final List<String> contents, final String[] content, final IScriptFile file, final int line, final List<OnlyIfMatch> matches) {
        
        final List<String> strings = Arrays.asList(content);
        final int indexOfStart = strings.indexOf("#" + this.name());
        final int indexOfEnd = strings.indexOf("#" + this.preprocessorEndMarker());
        
        if(indexOfStart > 0 || indexOfEnd > 0) {
            
            final boolean foundStart = (Math.min(indexOfStart, indexOfEnd) == indexOfStart) != (indexOfStart == -1);
            final String newContent;
            
            if(foundStart) {
                
                newContent = String.join(" ", strings.subList(indexOfStart + 1, content.length));
                this.getOnlyIfMatch(file, contents, matches, new Match(this, line, newContent));
            } else {
                
                // Substring 1 to get rid of the # of #endif
                newContent = String.join(" ", strings.subList(indexOfEnd, content.length)).substring(1);
                this.getOnlyIfMatch(file, contents, matches, new Match(EndIfPreprocessor.INSTANCE, line, newContent));
            }
        }
    }
    
    private CodePosition getPosition(
            final IScriptFile file,
            final List<String> contents,
            final int line,
            final String[] preprocessorContent,
            final int contentPosition,
            final boolean isEnd
    ) {
        
        //The length of '#onlyif ' since we want to remove that as well ^^
        final int prefixLength = isEnd ? this.preprocessorEndMarker().length() + 1 : NAME.length() + 2;
        
        final int startColumn = contents.get(line - 1).lastIndexOf(String.join(" ", preprocessorContent));
        
        int endColumn = startColumn + contentPosition - 1;
        for(int i = 0; i < contentPosition; i++) {
            endColumn += preprocessorContent[i].length();
        }
        
        return new CodePosition(new FakeSourceFile(file.name()), line, startColumn - prefixLength, line, endColumn);
    }
    
    private void removeData(final List<OnlyIfMatch> matches, final List<String> preprocessedFileContents) {
        
        matches.forEach(it -> it.remove(preprocessedFileContents));
    }
    
}
