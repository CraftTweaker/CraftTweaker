package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.LinkConversionRule;

import javax.lang.model.element.Element;

public class ThisTypeConversionRule implements LinkConversionRule {
    
    @Override
    public boolean canConvert(String link) {
        return link.isEmpty();
    }
    
    @Override
    public String convertToClickableMarkdown(String link, Element element) {
        return "[this](.)";
    }
}
