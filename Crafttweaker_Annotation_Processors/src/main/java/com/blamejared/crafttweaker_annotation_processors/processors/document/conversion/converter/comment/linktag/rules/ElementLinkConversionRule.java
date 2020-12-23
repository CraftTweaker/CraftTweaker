package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkConverter;

import javax.lang.model.element.Element;

public class ElementLinkConversionRule implements LinkConversionRule {
    
    private final LinkConverter linkConverter;
    
    public ElementLinkConversionRule(LinkConverter linkConverter) {
        this.linkConverter = linkConverter;
    }
    
    @Override
    public boolean canConvert(String link) {
        return link.contains("#");
    }
    
    @Override
    public String convertToClickableMarkdown(String link, Element element) {
        final String prefix = getPrefix(link, element);
        final String suffix = getSuffix(link);
        
        return merge(prefix, suffix);
    }
    
    private String getPrefix(String link, Element element) {
        final int indexOfSeparator = getIndexOfSeparator(link);
        final String substring = link.substring(0, indexOfSeparator);
        
        return linkConverter.convertLinkToClickableMarkdown(substring, element);
    }
    
    private String getSuffix(String link) {
        final int indexOfSeparator = getIndexOfSeparator(link);
        return link.substring(indexOfSeparator + 1);
    }
    
    private int getIndexOfSeparator(String link) {
        return link.indexOf('#');
    }
    
    private String merge(String prefix, String suffix) {
        return String.format("%s#%s", prefix, suffix);
    }
}
