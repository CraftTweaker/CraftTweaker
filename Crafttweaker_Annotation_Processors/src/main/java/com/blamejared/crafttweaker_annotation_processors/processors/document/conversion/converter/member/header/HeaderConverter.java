package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderConverter {
    
    private final TypeConverter typeConverter;
    private final ParameterConverter parameterConverter;
    private final GenericParameterConverter genericParameterConverter;
    
    public HeaderConverter(TypeConverter typeConverter, ParameterConverter parameterConverter, GenericParameterConverter genericParameterConverter) {
        this.typeConverter = typeConverter;
        this.parameterConverter = parameterConverter;
        this.genericParameterConverter = genericParameterConverter;
    }
    
    public MemberHeader convertHeaderFor(List<? extends VariableElement> parameters, List<? extends TypeParameterElement> typeParameters, TypeMirror returnType) {
        final AbstractTypeInfo returnTypeInfo = convertReturnType(returnType);
        final List<DocumentedParameter> documentedParameters = convertParameters(parameters);
        final List<DocumentedGenericParameter> genericParameters = convertGenericParameters(typeParameters);
        
        return new MemberHeader(returnTypeInfo, documentedParameters, genericParameters);
    }
    
    private AbstractTypeInfo convertReturnType(TypeMirror returnType) {
        return typeConverter.convertType(returnType);
    }
    
    private List<DocumentedParameter> convertParameters(List<? extends VariableElement> parameters) {
        return parameters.stream()
                .map(parameterConverter::convertParameter)
                .collect(Collectors.toList());
    }
    
    private List<DocumentedGenericParameter> convertGenericParameters(List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(genericParameterConverter::convertGenericParameter)
                .collect(Collectors.toList());
    }
    
    
}
