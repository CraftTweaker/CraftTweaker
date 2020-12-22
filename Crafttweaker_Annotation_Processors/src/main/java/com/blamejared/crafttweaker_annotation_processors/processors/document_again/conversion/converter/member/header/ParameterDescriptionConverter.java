package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.*;

import javax.annotation.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

public class ParameterDescriptionConverter {
    
    private final ParameterReader parameterReader;
    private final Elements elementUtils;
    
    
    public ParameterDescriptionConverter(ParameterReader parameterReader, Elements elementUtils) {
        this.parameterReader = parameterReader;
        this.elementUtils = elementUtils;
    }
    
    public DocumentationComment convertDescriptionOf(Element parameter) {
        final Element method = parameter.getEnclosingElement();
        final String methodDocComment = elementUtils.getDocComment(method);
        final ParameterInformationList parameterInformationList = parameterReader.readParametersFrom(methodDocComment, method);
        
        return readDescriptionOf(parameter, parameterInformationList);
    }
    
    private DocumentationComment readDescriptionOf(Element parameter, ParameterInformationList parameterInformationList) {
        if(!parameterInformationList.hasParameterInfoWithName("param")) {
            return DocumentationComment.empty();
        }
        
        final ParameterInfo param = parameterInformationList.getParameterInfoWithName("param");
        return getCommentForName(parameter, param);
    }
    
    private DocumentationComment getCommentForName(Element element, ParameterInfo param) {
        final String description = getDescriptionForParameterInfo(element, param);
        return new DocumentationComment(description, ExampleData.empty());
    }
    
    @Nullable
    private String getDescriptionForParameterInfo(Element element, ParameterInfo param) {
        final String name = getName(element);
        
        for(String occurrence : param.getOccurrences()) {
            if(occurrence.startsWith(name)) {
                return occurrence.substring(name.length()).trim();
            }
        }
        return null;
    }
    
    private String getName(Element element) {
        final String simpleName = element.getSimpleName().toString();
        if(isTypeParameter(element)) {
            return "<" + simpleName + ">";
        } else {
            return simpleName;
        }
    }
    
    private boolean isTypeParameter(Element element) {
        return element.getKind() == ElementKind.TYPE_PARAMETER;
    }
}
