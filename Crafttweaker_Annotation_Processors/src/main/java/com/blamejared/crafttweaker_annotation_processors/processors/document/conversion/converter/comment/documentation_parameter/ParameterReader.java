package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkTagReplacer;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParameterReader {
    
    private static final Pattern parameterPattern = Pattern.compile("@(\\w+)\\s+([^@]+)");
    
    private final LinkTagReplacer linkTagReplacer;
    private final CodeTagReplacer codeTagReplacer;
    
    public ParameterReader(LinkTagReplacer linkTagReplacer, CodeTagReplacer codeTagReplacer) {
        
        this.linkTagReplacer = linkTagReplacer;
        this.codeTagReplacer = codeTagReplacer;
    }
    
    public ParameterInformationList readParametersFrom(String from, Element element) {
        
        if(from == null) {
            return emptyInformationList();
        }
        
        return readParametersFromNonNull(from, element);
    }
    
    @Nonnull
    private ParameterInformationList emptyInformationList() {
        
        return new ParameterInformationList(new HashMap<>());
    }
    
    @Nonnull
    private ParameterInformationList readParametersFromNonNull(String from, Element element) {
        
        final Map<String, ParameterInfo> parameterInformation = readParameterInformation(from, element);
        return new ParameterInformationList(parameterInformation);
    }
    
    private Map<String, ParameterInfo> readParameterInformation(String from, Element element) {
        
        from = prepareDocComment(from, element);
        final Matcher matcher = parameterPattern.matcher(from);
        return readParameterInformationFromMatcher(matcher);
    }
    
    
    private String prepareDocComment(String from, Element element) {
        
        return this.codeTagReplacer.replaceCodeTags(linkTagReplacer.replaceLinkTagsFrom(from, element));
    }
    
    private Map<String, ParameterInfo> readParameterInformationFromMatcher(Matcher matcher) {
        
        return getMatchResultsFrom(matcher).map(this::readParameterInfoFromMatchResult)
                .collect(asMapWithMergedParameterInformation());
    }
    
    private Collector<ParameterInfo, ?, Map<String, ParameterInfo>> asMapWithMergedParameterInformation() {
        
        final Function<ParameterInfo, String> keyMapper = ParameterInfo::getName;
        final Function<ParameterInfo, ParameterInfo> valueMapper = Function.identity();
        final BinaryOperator<ParameterInfo> mergerFunction = ParameterInfo::merge;
        
        return Collectors.toMap(keyMapper, valueMapper, mergerFunction);
    }
    
    private Stream<MatchResult> getMatchResultsFrom(Matcher matcher) {
        
        final Stream.Builder<MatchResult> builder = Stream.builder();
        while(matcher.find()) {
            builder.add(matcher.toMatchResult());
        }
        return builder.build();
    }
    
    private ParameterInfo readParameterInfoFromMatchResult(MatchResult toMatchResult) {
        
        final String name = getParameterNameFromMatchResult(toMatchResult);
        final String text = getParameterTextFromMatchResult(toMatchResult);
        
        return new ParameterInfo(name, text);
    }
    
    private String getParameterNameFromMatchResult(MatchResult toMatchResult) {
        
        return toMatchResult.group(1).trim();
    }
    
    private String getParameterTextFromMatchResult(MatchResult toMatchResult) {
        
        return toMatchResult.group(2).trim();
    }
    
}
