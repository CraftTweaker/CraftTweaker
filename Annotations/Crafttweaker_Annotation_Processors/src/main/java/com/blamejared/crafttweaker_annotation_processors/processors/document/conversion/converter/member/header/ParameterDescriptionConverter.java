package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInformationList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

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
        return new DocumentationComment(description, null, null, ExampleData.empty(), MetaData.empty());
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
