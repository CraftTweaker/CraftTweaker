package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag;

import javax.lang.model.element.Element;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkTagReplacer {
    
    private static final Pattern linkPattern = Pattern.compile("\\{@link ([\\w.]+)}");
    
    private final LinkConverter linkConverter;
    
    public LinkTagReplacer(LinkConverter linkConverter) {
        this.linkConverter = linkConverter;
    }
    
    public String replaceLinkTagsFrom(String docComment, Element element) {
        final Matcher matcher = linkPattern.matcher(docComment);
        return replaceLinksWithMatcher(matcher, element);
    }
    
    private String replaceLinksWithMatcher(Matcher matcher, Element element) {
        final StringBuffer result = new StringBuffer();
        
        while(matcher.find()) {
            final MatchResult matchResult = matcher.toMatchResult();
            final String replacement = getReplacementForMatchResult(matchResult, element);
            matcher.appendReplacement(result, replacement);
        }
        
        matcher.appendTail(result);
        return result.toString();
    }
    
    private String getReplacementForMatchResult(MatchResult result, Element element) {
        final String linkTagContent = getLinkTagContent(result);
        return getReplacementFor(linkTagContent, element);
    }
    
    private String getLinkTagContent(MatchResult result) {
        return result.group(1);
    }
    
    private String getReplacementFor(String linkTagContent, Element element) {
        return linkConverter.convertLinkToClickableMarkdown(linkTagContent, element);
    }
    
    
}
