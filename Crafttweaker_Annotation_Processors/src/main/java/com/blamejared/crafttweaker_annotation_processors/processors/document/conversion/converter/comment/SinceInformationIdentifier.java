package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInformationList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterReader;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;

public class SinceInformationIdentifier {
    private static final String SINCE_PARAMETER = "since";
    
    private final ParameterReader reader;
    
    public SinceInformationIdentifier(final ParameterReader reader) {
        this.reader = reader;
    }
    
    @Nullable
    public String findInCommentString(@Nullable final String docComment, final Element element) {
        if(docComment == null) {
            return null;
        }
        
        return findInNonnullCommentString(docComment, element);
    }
    
    @Nullable
    private String findInNonnullCommentString(final String docComment, final Element element) {
        final ParameterInformationList list = this.reader.readParametersFrom(docComment, element);
        
        return list.hasParameterInfoWithName(SINCE_PARAMETER)?
                this.stringifySinceInfo(list.getParameterInfoWithName(SINCE_PARAMETER)) : null;
    }
    
    private String stringifySinceInfo(final ParameterInfo info) {
        return String.join("", info.getOccurrences());
    }
}
