package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderConverter {
    
    private final TypeConverter typeConverter;
    private final ParameterConverter parameterConverter;
    private final GenericParameterConverter genericParameterConverter;
    private final Types typeUtils;
    
    public HeaderConverter(TypeConverter typeConverter, ParameterConverter parameterConverter, GenericParameterConverter genericParameterConverter, Types typeUtils) {
        this.typeConverter = typeConverter;
        this.parameterConverter = parameterConverter;
        this.genericParameterConverter = genericParameterConverter;
        this.typeUtils = typeUtils;
    }
    
    public MemberHeader convertHeaderFor(List<? extends VariableElement> parameters, List<? extends TypeParameterElement> typeParameters, TypeMirror returnType) {
        final AbstractTypeInfo returnTypeInfo = convertReturnType(returnType);
        final List<DocumentedParameter> documentedParameters = convertParameters(parameters, typeParameters);
        final List<DocumentedGenericParameter> genericParameters = convertGenericParameters(typeParameters);
        
        return new MemberHeader(returnTypeInfo, documentedParameters, genericParameters);
    }
    
    private AbstractTypeInfo convertReturnType(TypeMirror returnType) {
        return typeConverter.convertType(returnType);
    }
    
    private List<DocumentedParameter> convertParameters(List<? extends VariableElement> parameters, List<? extends TypeParameterElement> typeParameters) {
        parameters = new ArrayList<>(parameters);
        removeClassParametersIfPresent(parameters, typeParameters.size());
        
        return parameters.stream()
                .map(parameterConverter::convertParameter)
                .collect(Collectors.toList());
    }
    
    private void removeClassParametersIfPresent(List<? extends VariableElement> parameters, int maximumNumberOfParameters) {
        for(int i = 0; i < maximumNumberOfParameters; i++) {
            final VariableElement variableElement = parameters.get(0);
            final Element element = typeUtils.asElement(variableElement.asType());
            if(isNoClassParameter(element)) {
                return;
            } else {
                parameters.remove(0);
            }
        }
    }
    
    private boolean isNoClassParameter(Element element) {
        return !(element instanceof TypeElement) || !isClassType((TypeElement) element);
    }
    
    private boolean isClassType(TypeElement element) {
        return element.getQualifiedName().toString().equals(Class.class.getCanonicalName());
    }
    
    private List<DocumentedGenericParameter> convertGenericParameters(List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
                .map(genericParameterConverter::convertGenericParameter)
                .collect(Collectors.toList());
    }
    
    
}
