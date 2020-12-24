package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.util.Optional;

public class ReturnTypeInfoReader {
    
    private final ParameterReader parameterReader;
    private final Elements elementUtils;
    
    public ReturnTypeInfoReader(ParameterReader parameterReader, Elements elementUtils) {
        this.parameterReader = parameterReader;
        this.elementUtils = elementUtils;
    }
    
    public Optional<String> readForMethod(ExecutableElement method) {
        final String docComment = elementUtils.getDocComment(method);
        final ParameterInformationList parameterInformationList = parameterReader.readParametersFrom(docComment, method);
        return Optional.ofNullable(getReturnTypeInfoFrom(parameterInformationList));
    }
    
    private String getReturnTypeInfoFrom(ParameterInformationList parameterInformationList) {
        if(!parameterInformationList.hasParameterInfoWithName("return")) {
            return null;
        }
        
        return parameterInformationList.getParameterInfoWithName("return").getAnyOccurrence();
    }
}
