package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag;

import javax.lang.model.element.Element;

public interface LinkConversionRule {
    boolean canConvert(String link);
    
    String convertToClickableMarkdown(String link, Element element);
}
